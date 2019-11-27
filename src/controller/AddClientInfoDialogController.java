package controller;

import javafx.fxml.FXML;

import java.sql.*;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Client;
import javafx.event.ActionEvent;

public class AddClientInfoDialogController {
	private DatabaseConnection database = new DatabaseConnection();
	@FXML
	private TextField phoneField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField nameField;

	private Stage dialogStage;
	private Client client = new Client();

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image("/view/case.png"));
		nameField.requestFocus();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
		client.setName(nameField.getText());
		client.setPhone(phoneField.getText());
		client.setEmail(emailField.getText());
		DatabaseController.addClientInfoToDB(database, client);
		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	public Client getClient(){
		return client;
	}
}
