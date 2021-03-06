package controller;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Appointment;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Notification;

public class EditAppointmentDialogueController {
	private DatabaseConnection database = new DatabaseConnection();
	@FXML
	private TextField hourField;
	@FXML
	private TextField minuteField;
	@FXML
	private ComboBox<String> amPMCombo;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField notificationFrequency;
	@FXML
	private TextField notificationStart;
	@FXML
	private TextField roomField;
	@FXML
	private TextField addressField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField zipField;
	@FXML
	private ComboBox<String> statusCombo;
	private Stage dialogStage;
	private Appointment appointment;

	public void setDialogStage(Stage dialogStage, Appointment appointment){
		this.appointment = appointment;
		fillFields();
		this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image("/view/case.png"));
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
		appointment.setTitle(titleField.getText());
		appointment.setRoom(roomField.getText());
		appointment.setAddress(addressField.getText());
		appointment.setCity(cityField.getText());
		appointment.setState(stateField.getText());
		appointment.setZip(zipField.getText());
		appointment.setDate(makeLocalDateTime());
		appointment.setStatus(statusCombo.getValue());
		DatabaseController.editAppointmentInDB(database, appointment);
		DatabaseController.deleteNotifications(appointment.getAppointment_id(),0);
		addAppointmentNotifications(appointment, makeLocalDateTime());

		dialogStage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	private LocalDateTime makeLocalDateTime(){
		int hour = Integer.parseInt(hourField.getText());
		int minute = Integer.parseInt(minuteField.getText());
		String hourString;
		String minuteString;

		LocalTime time;
		if(amPMCombo.getValue().equals("AM") && hour == 12){
			hour = 0;
		}
		if(amPMCombo.getValue().equals("PM")){
			if(hour != 12){
				hour += 12;
			}
		}
		if(hour < 10){
			hourString = "0" + Integer.toString(hour);
		} else {
			hourString = Integer.toString(hour);
		}
		if(minute < 10){
			minuteString = "0" + Integer.toString(minute);
		}else{
			minuteString = Integer.toString(minute);
		}
		time = LocalTime.parse(hourString + ":" + minuteString);
		return LocalDateTime.of(dateField.getValue(), time);
	}

	private void fillFields(){
		titleField.setText(appointment.getTitle());
		roomField.setText(appointment.getRoom());
		addressField.setText(appointment.getAddress());
		cityField.setText(appointment.getCity());
		stateField.setText(appointment.getState());
		zipField.setText(appointment.getZip());
		dateField.setValue(appointment.getDate().toLocalDate());
		String date = appointment.getDate().toString();
		Integer hour = Integer.parseInt(date.substring(11, 13));
		String minute = date.substring(14, 16);

		minuteField.setText(minute);
		if(hour < 12){
			amPMCombo.setValue("AM");
			if(hour == 0){
				hour = 12;
			}
		} else {
			amPMCombo.setValue("PM");
			if(hour > 12){
				hour -= 12;
			}
		}
		hourField.setText(hour.toString());
	}

	public void initialize() {
		statusCombo.getItems().addAll("Complete", "Incomplete");
		statusCombo.setValue("Incomplete");
		amPMCombo.getItems().addAll("AM", "PM");
	}

	private void addAppointmentNotifications(Appointment appointment, LocalDateTime date) throws SQLException{
		long start = Long.parseLong(notificationStart.getText());
		long frequency = Long.parseLong(notificationFrequency.getText());
		LocalDateTime startDate = date.minusDays(start);
		long daysUntil = startDate.until(date, ChronoUnit.DAYS);
		for(long i = 0; i < daysUntil; i+=frequency){
			Notification n = new Notification(startDate.plusDays(i));
			n.setAppointment(appointment);
			n.setMessage(daysUntil - i + " Days Until " + date);
			DatabaseController.addNotificationToDB(n);
		}
		Notification today = new Notification();
		today.setAppointment(appointment);
		today.setMessage(appointment.getTitle() + " is due today!");
		today.setSendDate(appointment.getDate());
		DatabaseController.addNotificationToDB(today);
	}

}