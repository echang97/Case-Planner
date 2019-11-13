package controller;

import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CaseListController implements Initializable{
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private Tab ongoingTab;
	@FXML
	private TableView<Case> ongoingCaseTable;
	@FXML
	private TableColumn<Case, String> ongoingCaseColumn;
	@FXML
	private TableColumn<Case, LocalDateTime> dateAddedColumn;
	@FXML
	private Tab archivedTab;
	@FXML
	private TableView<Case> archivedCaseTable;
	@FXML
	private TableColumn<Case, String> archivedCaseColumn;
	@FXML
	private TableColumn<Case, LocalDateTime> dateArchivedColumn;
	@FXML
	private Tab deletedTab;
	@FXML
	private TableView<Case> deletedCaseTable;
	@FXML
	private TableColumn<Case, String> deletedCaseColumn;
	@FXML
	private TableColumn<Case, LocalDateTime> dateRemovedColumn;
	@FXML
	private TableView<Deadline> deadlinesTable;
	@FXML
	private TableColumn<Deadline, String> deadlineTitleColumn;
	@FXML
	private TableColumn<Deadline, Case> deadlineCaseColumn;
	@FXML
	private TableView<Appointment> appointmentsTable;
	@FXML
	private TableColumn<Appointment, String> appointmentTitleColumn;
	@FXML
	private TableColumn<Appointment, Case> appointmentCaseColumn;

	private ObservableList<Case> ongoingCases = FXCollections.observableArrayList();
	private ObservableList<Case> archivedCases = FXCollections.observableArrayList();
	private ObservableList<Case> deletedCases = FXCollections.observableArrayList();
	private ObservableList<Deadline> deadlines = FXCollections.observableArrayList();
	private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

	private ObservableList<Case> getDataFromACaseAndAddToObservableList(String query){
		ObservableList<Case> personData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM aCase WHERE status = ...;"
			while(resultSet.next()){

				personData.add(new Case(
						resultSet.getInt("case_id"),
						resultSet.getInt("client_id"),
						resultSet.getString("title"),
						resultSet.getString("status"),
						resultSet.getString("dateAdded"),
						resultSet.getString("dateResolved"),
						resultSet.getString("dateRemoved")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return personData;
	}

	private Case getDataFromCaseToReturn(int case_id) throws SQLException {
		connection = database.getConnection();
		statement = connection.createStatement();
		ResultSet aCase = statement.executeQuery("SELECT * FROM aCase WHERE case_id = " + case_id);
		return new Case(
				aCase.getInt("case_id"),
				aCase.getInt("client_id"),
				aCase.getString("title"),
				aCase.getString("status"),
				aCase.getString("dateAdded"),
				aCase.getString("dateResolved"),
				aCase.getString("dateRemoved")
		);
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
				Case c = getDataFromCaseToReturn(resultSet.getInt("case_id"));
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

				Case c = getDataFromCaseToReturn(resultSet.getInt("case_id"));
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
  
  // Event Listener on Button.onAction
  @FXML
	public void addClient(ActionEvent event){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddClientInfoDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add Client Info");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			AddClientInfoDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
  	}
  

	// Event Listener on Button.onAction
	@FXML
	public void manageClients(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ManageClients.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Manage Clients");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			ManageClientsController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  
	// Event Listener on Button.onAction
	@FXML
	public void addCase(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddCaseDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add Case");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			AddCaseDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewCaseDetails(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewCaseDetails.fxml"));
		Case selectedCase;
		if(archivedTab.isSelected()){
			selectedCase = archivedCaseTable.getSelectionModel().getSelectedItem();
		}else if(deletedTab.isSelected()){
			selectedCase = deletedCaseTable.getSelectionModel().getSelectedItem();
		}else{
			selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();
		}
		
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Case Details");

			ViewCaseDetailsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setCase(selectedCase);
			controller.setDetails();

			dialogStage.showAndWait();
			refreshLists();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void archiveCaseFromOngoing(ActionEvent event) throws SQLException {
		Case selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateResolved(LocalDateTime.now());
			DatabaseController.archiveCaseInDB(database, selectedCase);
			refreshLists();
		}else{
			System.out.println("Must select a case from Ongoing");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void removeCaseFromOngoing(ActionEvent event) throws SQLException {
		Case selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateRemoved(LocalDateTime.now());
			DatabaseController.removeCaseInDB(database, selectedCase);
			refreshLists();
		}else{
			System.out.println("Must select a case from Ongoing");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void removeCaseFromArchived(ActionEvent event) throws SQLException {
		// TODO implement w Database
		Case selectedCase = archivedCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateRemoved(LocalDateTime.now());
			DatabaseController.removeCaseInDB(database, selectedCase);
			refreshLists();
		}else{
			System.out.println("Must select a case from Archived");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void resumeCaseFromArchived(ActionEvent event) throws SQLException {
		Case selectedCase = archivedCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateResolved(LocalDateTime.now());
			DatabaseController.resumeCaseInDB(selectedCase);
			refreshLists();
		}else{
			// TODO Make this a pop-up message
			System.out.println("Must select a case from Archived");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void archiveCaseFromDeleted(ActionEvent event) throws SQLException {
		Case selectedCase = deletedCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateResolved(LocalDateTime.now());
			DatabaseController.archiveCaseInDB(database, selectedCase);
			refreshLists();
		}else{
			System.out.println("Must select a case from Deleted");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void resumeCaseFromDeleted(ActionEvent event) throws SQLException {
		Case selectedCase = deletedCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			DatabaseController.resumeCaseInDB(selectedCase);
			refreshLists();
		}else{
			// TODO Make this a pop-up message
			System.out.println("Must select a case from Deleted");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewAllDeadlines(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewAllDeadlines.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("View Deadlines");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			dialogStage.showAndWait();
			refreshLists();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewAllAppointments(ActionEvent event){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewAllAppointments.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("View Appointments");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			dialogStage.showAndWait();
			refreshLists();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void refreshLists(){
		try {
			DatabaseController.autoDeleteCasesInDB(database);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ongoingCaseTable.getItems().clear();
		ongoingCaseColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		dateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		ongoingCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'ongoing'");
		ongoingCaseTable.getItems().addAll(ongoingCases);

		archivedCaseTable.getItems().clear();
		archivedCaseColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		dateArchivedColumn.setCellValueFactory(new PropertyValueFactory<>("dateResolved"));
		archivedCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'resolved'");
		archivedCaseTable.getItems().addAll(archivedCases);

		deletedCaseTable.getItems().clear();
		deletedCaseColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		dateRemovedColumn.setCellValueFactory(new PropertyValueFactory<>("dateRemoved"));
		deletedCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'removed'");
		deletedCaseTable.getItems().addAll(deletedCases);

		deadlinesTable.getItems().clear();

		deadlineTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		deadlineCaseColumn.setCellValueFactory(new PropertyValueFactory<>("case"));
		deadlines = getDataFromADeadlineAndAddToObservableList("SELECT * FROM deadline ORDER BY datetime(date) LIMIT 6");
		deadlinesTable.getItems().addAll(deadlines);

		appointmentsTable.getItems().clear();
		appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		appointmentCaseColumn.setCellValueFactory(new PropertyValueFactory<>("case"));
		appointments = getDataFromAnAppointmentAndAddToObservableList("SELECT * FROM appointment ORDER BY datetime(date) LIMIT 6");
		appointmentsTable.getItems().addAll(appointments);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		refreshLists();
	}

}
