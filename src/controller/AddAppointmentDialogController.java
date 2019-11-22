package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Appointment;
import model.Case;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Notification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class AddAppointmentDialogController implements Initializable{
	@FXML
	private TextField appointmentTitleField;
	@FXML
	private TextField hourField;
	@FXML
	private TextField minuteField;
	@FXML
	private ComboBox<String> amPMCombo;
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
	private DatePicker dateField;
	@FXML
	private TextField notificationFrequencyField;
	@FXML
	private TextField notificationStartField;
	private Case c;
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException{
		DatabaseConnection database = new DatabaseConnection();
		LocalDateTime date = makeLocalDateTime();
		Appointment appointment = new Appointment();
		appointment.setTitle(appointmentTitleField.getText());
		appointment.setDate(date);
		appointment.setRoom(roomField.getText());
		appointment.setAddress(addressField.getText());
		appointment.setCity(cityField.getText());
		appointment.setState(stateField.getText());
		appointment.setZip(zipField.getText());
		appointment.setCase(c);
		DatabaseController.addAppointmentToDB(database, appointment);
		addAppointmentNotifications(appointment, date);
		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		amPMCombo.getItems().addAll("AM", "PM");
		amPMCombo.setValue("AM");
	}

	private LocalDateTime makeLocalDateTime(){
		int hour = Integer.parseInt(hourField.getText());
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
			time = LocalTime.parse("0" + Integer.toString(hour) + ":" + minuteField.getText());
		}
		else{
			time = LocalTime.parse(Integer.toString(hour) + ":" + minuteField.getText());
		}
		return LocalDateTime.of(dateField.getValue(), time);
	}

	private void addAppointmentNotifications(Appointment appointment, LocalDateTime date) throws SQLException{
		long start = Long.parseLong(notificationStartField.getText());
		long frequency = Long.parseLong(notificationFrequencyField.getText());
		LocalDateTime startDate = date.minusDays(start);
		long daysUntil = startDate.until(date, ChronoUnit.DAYS);
		for(long i = 0; i < daysUntil; i+=frequency){
			Notification n = new Notification(startDate.plusDays(i));
			n.setAppointment(appointment);
			n.setMessage(daysUntil - i + " Days Until " + date);
			DatabaseController.addNotificationToDB(n);
		}
	}

	public void setCase(Case c){
		this.c = c;
	}
}