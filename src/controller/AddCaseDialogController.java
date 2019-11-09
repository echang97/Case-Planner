package controller;

import controller.*;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class AddCaseDialogController{
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	@FXML
	private TextField caseTitleField;
	@FXML
	private Button submitButton;
	@FXML
	private TableView<Client> clientTable;
	@FXML
	private TableColumn<Client, String> clientNameColumn;
	@FXML
	private TableColumn<Client, String> clientPhoneColumn;
	@FXML
	private TableColumn<Client, String> clientEmailColumn;

	private Stage dialogStage;
	private Case c = new Case();
	private Client client;
	
	private ObservableList<Client> clientList = FXCollections.observableArrayList();

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	private ObservableList<Client> getClientAndAddToObservableList(String query){
		ObservableList<Client> clientData = FXCollections.observableArrayList();
		try {
			
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			System.out.println(resultSet);
			while(resultSet.next()){
				clientData.add(new Client(
						resultSet.getInt("client_id"),
						resultSet.getString("name"),
						resultSet.getString("phone"),
						resultSet.getString("email")
				));
			}
			connection.close();
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientData;
	}
	
	public void initialize(){
		clientNameColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("name"));
		clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("phone"));
		clientEmailColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
		clientList = getClientAndAddToObservableList("SELECT * FROM client");
		clientTable.getItems().addAll(clientList);
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
	//	boolean isSelected = false; //////////////
		c.setTitle(caseTitleField.getText());
		c.setDateAdded(LocalDateTime.now());
		c.setStatus("ongoing");
		client = clientTable.getSelectionModel().getSelectedItem();
	//	System.out.println("client is selected: " + isSelected);
		if(client != null){
	//		isSelected = true;
	//		System.out.println("client is selected: " + isSelected);
			c.setClient(client);
		}
		System.out.println(c.getClient());
		
		DatabaseController.addCaseToDB(database, c);
		dialogStage.close();
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
	

}
