package controller;

import model.Case;
import model.Deadline;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import model.Notification;

public class AddDeadlineDialogController{
	private DatabaseConnection database = new DatabaseConnection();
	@FXML
	private TextField titleField;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField notificationFrequency;
	@FXML
	private TextField notificationStart;

	private Stage dialogStage;
	private Deadline deadline;
	private Case c;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException{
		String title = titleField.getText();
		LocalDateTime date = dateField.getValue().atStartOfDay();
		deadline = new Deadline(title, date);
		deadline.setCase(c);
		DatabaseController.addDeadlineToDB(database, deadline);
		addDeadlineNotifications(date);
		dialogStage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCalcDeadline(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeadlineCalculator.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Deadline Calcuator");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			DeadlineCalculatorController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			LocalDateTime newDate = controller.getDate();
			if (newDate != null){
				dateField.setValue(newDate.toLocalDate());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
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
	}

	public Deadline getDeadline(){
		return deadline;
	}

	public void setCase(Case c){
		this.c = c;
	}

}
