package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Appointment;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private TextField notifcationStartField;
	private Stage dialogStage;
	private Appointment appointment;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException{
		DatabaseConnection database = new DatabaseConnection();
		appointment = new Appointment();
		appointment.setTitle(appointmentTitleField.getText());
		appointment.setDate(makeLocalDateTime());
		appointment.setRoom(roomField.getText());
		appointment.setAddress(addressField.getText());
		appointment.setCity(cityField.getText());
		appointment.setState(stateField.getText());
		appointment.setZip(zipField.getText());

		DatabaseController.addAppointmentToDB(database, appointment);

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
		LocalTime time = null;
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
}