package controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Deadline;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        dialogStage.getIcons().add(new Image("/view/case.png"));
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

	@FXML
	public void handleCalcDeadline(ActionEvent event){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DeadlineCalculator.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Deadline Calculator");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			DeadlineCalculatorController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			LocalDate newDate = controller.getDate();
			if (newDate != null){
				dateField.setValue(newDate);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
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