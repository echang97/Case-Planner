package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deadline;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Notification;

public class EditDeadlineDialogueController {
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
	private ComboBox<String> statusCombo;
	private Stage dialogStage;
	private Deadline deadline;

	public void setDialogStage(Stage dialogStage, Deadline deadline){
		this.deadline = deadline;
		fillFields();
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
		deadline.setTitle(titleField.getText());
		deadline.setDate(dateField.getValue().atStartOfDay());
		deadline.setStatus(statusCombo.getValue());
		DatabaseController.editDeadlineInDB(database, deadline);
		DatabaseController.deleteNotifications(0, deadline.getDeadline_id());
		addDeadlineNotifications(dateField.getValue().atStartOfDay());

		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
	
	public void fillFields(){
		titleField.setText(deadline.getTitle());
		dateField.setValue(deadline.getDate().toLocalDate());
	}
	
	public void initialize() {
		statusCombo.getItems().addAll("Complete", "Incomplete");
		statusCombo.setValue("Incomplete");
	}

	private void addDeadlineNotifications(LocalDateTime date) throws SQLException{
		long start = Long.parseLong(notificationStart.getText());
		long frequency = Long.parseLong(notificationFrequency.getText());
		LocalDateTime startDate = date.minusDays(start);
		long daysUntil = startDate.until(date, ChronoUnit.DAYS);
		for(long i = 0; i < daysUntil; i+=frequency){
			Notification n = new Notification(startDate.plusDays(i));
			n.setDeadline(deadline);
			n.setMessage(daysUntil - i + " Days Until " + date);
			DatabaseController.addNotificationToDB(n);
		}
		Notification today = new Notification();
		today.setDeadline(deadline);
		today.setMessage(deadline.getTitle() + " is due today!");
		today.setSendDate(deadline.getDate());
		DatabaseController.addNotificationToDB(today);
	}
}