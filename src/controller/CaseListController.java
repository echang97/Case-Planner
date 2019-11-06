package controller;

import controller.*;
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
	private TableView<Appointment> appointmentsTable;

	private ObservableList<Case> ongoingCases = FXCollections.observableArrayList();
	private ObservableList<Case> archivedCases = FXCollections.observableArrayList();
	private ObservableList<Case> deletedCases = FXCollections.observableArrayList();

	private ObservableList<Case> getDataFromACaseAndAddToObservableList(String query){
		ObservableList<Case> personData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM aCase WHERE status = ...;"
			System.out.println(resultSet);
			while(resultSet.next()){

				personData.add(new Case(
						resultSet.getInt("case_id"),
						resultSet.getString("title"),
						resultSet.getString("status"),
						resultSet.getString("dateAdded"),
						resultSet.getString("dateResolved"),
						resultSet.getString("dateRemoved")
				));
			}
			connection.close();
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return personData;
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
			ongoingCases.add(controller.getCase());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewCaseDetails(ActionEvent event) {
		// TODO Implement with Database
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewCaseDetails.fxml"));
		Case selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();

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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void archiveCase(ActionEvent event) throws SQLException {
		// TODO implement w Database
		Case selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateResolved(LocalDateTime.now());
			DatabaseController.archiveCaseInDB(database, selectedCase);
			archivedCases.add(selectedCase);
			ongoingCases.remove(selectedCase);
		}else{
			// TODO Make this a pop-up message
			System.out.println("Must select a case from Ongoing");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void removeCase(ActionEvent event) throws SQLException {
		// TODO implement w Database
		Case selectedCase = ongoingCaseTable.getSelectionModel().getSelectedItem();
		if (selectedCase != null){
			selectedCase.setDateRemoved(LocalDateTime.now());
			DatabaseController.removeCaseInDB(database, selectedCase);
			deletedCases.add(selectedCase);
			ongoingCases.remove(selectedCase);
		}else{
			// TODO Make this a pop-up message
			System.out.println("Must select a case from Ongoing");
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewAllDeadlines(ActionEvent event) { //////////////////////////
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewAllDeadlines.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("View Deadlines");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void viewAllAppointments(ActionEvent event) throws SQLException { /////////////////////
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ViewAllAppointments.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("View Appointments");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			dialogStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Implement w Database
		ongoingCaseColumn.setCellValueFactory(new PropertyValueFactory<Case,String>("title"));
		dateAddedColumn.setCellValueFactory(new PropertyValueFactory<Case,LocalDateTime>("dateAdded"));
		ongoingCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'ongoing'");
		ongoingCaseTable.getItems().addAll(ongoingCases);

		archivedCaseColumn.setCellValueFactory(new PropertyValueFactory<Case,String>("title"));
		dateArchivedColumn.setCellValueFactory(new PropertyValueFactory<Case,LocalDateTime>("dateResolved"));
		archivedCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'resolved'");
		archivedCaseTable.getItems().addAll(archivedCases);

		deletedCaseColumn.setCellValueFactory(new PropertyValueFactory<Case,String>("title"));
		dateRemovedColumn.setCellValueFactory(new PropertyValueFactory<Case,LocalDateTime>("dateRemoved"));
		deletedCases = getDataFromACaseAndAddToObservableList("SELECT * FROM aCase WHERE status = 'removed'");
		deletedCaseTable.getItems().addAll(deletedCases);
	}

}