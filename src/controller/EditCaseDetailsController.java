package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class EditCaseDetailsController {
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private TextField caseTitleField;
	@FXML
	private TableView <Client> clientTable;
	@FXML
	private TableColumn <Client, String> clientNameColumn;
	@FXML
	private TableColumn <Client, String> clientPhoneColumn;
	@FXML
	private TableColumn <Client, String> clientEmailColumn;
	@FXML
	private TableView <Deadline> deadlineTable;
	@FXML
	private TableColumn <Deadline, String> deadlineTitleColumn;
	@FXML
	private TableColumn <Deadline, String> deadlineDateColumn;
	@FXML
	private TableColumn<Deadline, String> deadlineStatusColumn;
	@FXML
	private TableView <Appointment> appointmentTable;
	@FXML
	private TableColumn <Appointment, String> appointmentTitleColumn;
	@FXML
	private TableColumn <Appointment, String> appointmentLocationColumn;
	@FXML
	private TableColumn <Appointment, String> appointmentDateColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentStatusColumn;
	private Case c;
	private Stage dialogStage;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void addDeadline(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddDeadlineDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add Deadline");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			AddDeadlineDialogController controller = loader.getController();
			controller.setCase(c);
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteDeadline(ActionEvent event) throws SQLException {
		Deadline deadline = deadlineTable.getSelectionModel().getSelectedItem();
		DatabaseConnection database = new DatabaseConnection();
		DatabaseController.delDeadlineInDB(database, deadline);
		refreshLists();
	}
	// Event Listener on Button.onAction
	@FXML
	public void editDeadline(ActionEvent event) {
		Deadline deadline = deadlineTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditDeadlineDialogue.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Deadline");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditDeadlineDialogueController controller = loader.getController();
			controller.setDialogStage(dialogStage, deadline);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		refreshLists();
	}
	// Event Listener on Button.onAction
	@FXML
	public void editAppointment(ActionEvent event) {
		Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditAppointmentDialogue.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Appointment");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditAppointmentDialogueController controller = loader.getController();
			controller.setDialogStage(dialogStage, appointment);

			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		refreshLists();
	}
	// Event Listener on Button.onAction
	@FXML
	public void addAppointment(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddAppointmentDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add Appointment");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			AddAppointmentDialogController controller = loader.getController();
			controller.setCase(c);
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteAppointment(ActionEvent event) throws SQLException {
		Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
		DatabaseConnection database = new DatabaseConnection();
		DatabaseController.delAppointmentInDB(database, appointment);
		refreshLists();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException{
		c.setTitle(caseTitleField.getText());
		try {
			System.out.println(clientTable.getSelectionModel().getSelectedItem());
			c.setClient(clientTable.getSelectionModel().getSelectedItem());
			c.setClient_id(c.getClient().getClient_id());
		}
		catch (Exception e){
			c.setClient(null);
			c.setClient_id(-1);
		}
		System.out.println (c.getClient_id());
		DatabaseController.editCaseInDB(database, c);

		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	public void setCase(Case c){
		this.c = c;
	}

	private ObservableList<Client> getDataFromAClientAndAddToObservableList(String query) {
		ObservableList<Client> clientData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM deadline;"
			System.out.println(query);
			while (resultSet.next()) {
				int client_id = resultSet.getInt(1);
				String client_name = resultSet.getString("name");
				String client_phone = resultSet.getString("phone");
				String client_email = resultSet.getString("email");

				clientData.add(new Client(
						client_id,
						client_name,
						client_phone,
						client_email
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return clientData;
	}

	private ObservableList<Deadline> getDataFromADeadlineAndAddToObservableList(String query){
		ObservableList<Deadline> deadlineData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM deadline;"
			System.out.println(query);
			while(resultSet.next()){
				int deadline_id = resultSet.getInt(1);
				String deadline_title = resultSet.getString("title");
				String deadline_date = resultSet.getString(4);
				String deadline_status = resultSet.getString(5);
				deadlineData.add(new Deadline(
						deadline_id,
						c,
						deadline_title,
						deadline_date,
						deadline_status
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return deadlineData;
	}

	private ObservableList<Appointment> getDataFromAnAppointmentAndAddToObservableList(String query){
		ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM deadline;"
			System.out.println(query);
			while(resultSet.next()){
				int appointment_id = resultSet.getInt(1);
				String appointment_title = resultSet.getString("title");
				String appointment_room = resultSet.getString("room");
				String appointment_address = resultSet.getString("address");
				String appointment_city = resultSet.getString("city");
				String appointment_state = resultSet.getString("state");
				String appointment_zip = resultSet.getString("zip");
				String appointment_date = resultSet.getString("date");
				String appointment_status = resultSet.getString("status");

				appointmentData.add(new Appointment(
						appointment_id,
						c,
						appointment_title,
						appointment_room,
						appointment_address,
						appointment_city,
						appointment_state,
						appointment_zip,
						appointment_date,
						appointment_status
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return appointmentData;
	}

	public void refreshLists(){
		clientTable.getItems().clear();
		clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		clientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		ObservableList<Client> clients = getDataFromAClientAndAddToObservableList("SELECT * FROM client");
		clientTable.getItems().addAll(clients);

		deadlineTable.getItems().clear();
		deadlineTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
		deadlineStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		ObservableList<Deadline> deadlines = getDataFromADeadlineAndAddToObservableList("SELECT * FROM deadline WHERE case_id = " + c.getCase_id());
		deadlineTable.getItems().addAll(deadlines);

		appointmentTable.getItems().clear();
		appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		appointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
		appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		ObservableList<Appointment> appointments = getDataFromAnAppointmentAndAddToObservableList("SELECT * FROM appointment WHERE case_id = " + c.getCase_id());
		appointmentTable.getItems().addAll(appointments);
	}

	public void setDetails(){
		refreshLists();
		caseTitleField.setText(c.getTitle());
		clientTable.getSelectionModel().select(c.getClient());
		System.out.println(clientTable.getSelectionModel().getSelectedItem());
	}

	public Case getCase(){
		return c;
	}

	public void clearSelectedClient(){
		clientTable.getSelectionModel().clearSelection();
	}

}
