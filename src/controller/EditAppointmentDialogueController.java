package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class EditAppointmentDialogueController {
	private DatabaseConnection database = new DatabaseConnection();
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
		appointment.setDate(dateField.getValue().atStartOfDay());
		appointment.setStatus(statusCombo.getValue());
		DatabaseController.editAppointmentInDB(database, appointment);

		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
	
	public void fillFields(){
		titleField.setText(appointment.getTitle());
		roomField.setText(appointment.getRoom());
		addressField.setText(appointment.getAddress());
		cityField.setText(appointment.getCity());
		stateField.setText(appointment.getState());
		zipField.setText(appointment.getZip());
		dateField.setValue(appointment.getDate().toLocalDate());
	}

	public void initialize() {
		statusCombo.getItems().addAll("Complete", "Incomplete");
		statusCombo.setValue("Incomplete");
	}
}
