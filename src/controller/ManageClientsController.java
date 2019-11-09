package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Client;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class ManageClientsController {
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private TableView<Client> clientTable;
	@FXML
	private TableColumn<Client, String> clientNameColumn;
	@FXML
	private TableColumn<Client, String> clientPhoneColumn;
	@FXML
	private TableColumn<Client, String> clientEmailColumn;

	private Stage dialogStage;
	
	private ObservableList<Client> clientList = FXCollections.observableArrayList();


	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void handleEditClient(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditClientInfoDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Client");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditClientInfoDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleDeleteClient(ActionEvent event) throws SQLException {
		Client client = clientTable.getSelectionModel().getSelectedItem();
		DatabaseController.delClientInfoInDB(database, client);
		refreshClientList();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
	
	private ObservableList<Client> getClientAndAddToObservableList(String query){
		//ObservableList<Client> clientData = FXCollections.observableArrayList();
		try {
			
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			System.out.println(resultSet);
			while(resultSet.next()){
				clientList.add(new Client(
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
		return clientList;
	}
	
	public void refreshClientList(){
		clientTable.getItems().clear();
		clientList.clear();
		clientNameColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("name"));
		clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("phone"));
		clientEmailColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
		clientList = getClientAndAddToObservableList("SELECT * FROM client");
		clientTable.getItems().addAll(clientList);
	}
	
	public void initialize(){
		clientNameColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("name"));
		clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("phone"));
		clientEmailColumn.setCellValueFactory(new PropertyValueFactory<Client,String>("email"));
		clientList = getClientAndAddToObservableList("SELECT * FROM client");
		clientTable.getItems().addAll(clientList);
	}
	
}
