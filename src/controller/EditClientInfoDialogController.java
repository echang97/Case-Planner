package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;

public class EditClientInfoDialogController {
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private TextField phoneField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField nameField;

	private Stage dialogStage;
	
	private Client client;

	
	public void setDialogStage(Stage dialogStage, Client client) {
		this.client = client;
		fillFields();
		this.dialogStage = dialogStage;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
		client.setName(nameField.getText());
		client.setEmail(emailField.getText());
		client.setPhone(phoneField.getText());
		DatabaseController.editClientInfoInDB(database, client);
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
	
	public void fillFields(){
		nameField.setText(client.getName());
		phoneField.setText(client.getPhone());
		emailField.setText(client.getEmail());
	}
	
}
