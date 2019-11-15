package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Deadline;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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
}
