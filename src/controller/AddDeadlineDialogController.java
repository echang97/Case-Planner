package controller;

import controller.*;
import model.Deadline;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;


public class AddDeadlineDialogController{
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private DatePicker dateField;
	@FXML
	private TextField titleField;
	@FXML
	private TextField notificationFrequency;
	@FXML
	private TextField notificationStart;

	private Stage dialogStage;
	private Deadline deadline;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) {
		String title = titleField.getText();
		LocalDateTime date = dateField.getValue().atStartOfDay();
		deadline = new Deadline(title, date);
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

	public Deadline getDeadline(){
		return deadline;
	}

}
