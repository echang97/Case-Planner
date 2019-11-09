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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewCaseDetailsController{
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private TableView<Deadline> deadlineTable;
	@FXML
	private TableColumn<Deadline, String> deadlineTitleColumn;
	@FXML
	private TableColumn<Deadline, LocalDateTime> deadlineDateColumn;
	@FXML
	private TableColumn<Deadline, String> deadlineStatusColumn;
	@FXML
	private TableView<Appointment> appointmentTable;
	@FXML
	private TableColumn<Appointment, String> appointmentTitleColumn;
	@FXML
	private TableColumn<Appointment, LocalDateTime> appointmentDateColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentLocColumn;
	@FXML
	private Label caseTitleLabel;
	@FXML
	private Label caseStatusLabel;
	@FXML
	private Label caseDateAddLabel;
	@FXML
	private Label clientNameLabel;
	@FXML
	private Label clientPhoneLabel;
	@FXML
	private Label clientEmailLabel;

	private Stage dialogStage;
	private Case c;

	private ObservableList<Deadline> deadlines = FXCollections.observableArrayList();
	private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

	public void setCase(Case c){
		this.c = c;
	}

	public void setClient(Case c) throws SQLException {
		if (c.getClient_id() > 0){
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM client WHERE client_id = " + c.getClient_id());
			c.setClient(new Client(
					resultSet.getInt("client_id"),
					resultSet.getString("name"),
					resultSet.getString("phone"),
					resultSet.getString("email")
			));
			connection.close();
			statement.close();
			resultSet.close();
		}
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleEdit(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditCaseDetails.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Case Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditCaseDetailsController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setCase(c);
			controller.setDetails();

			dialogStage.showAndWait();


			refreshLists();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}


	public void setDetails() throws SQLException {
		// TODO Auto-generated method stub
		caseTitleLabel.setText(c.getTitle());
		caseStatusLabel.setText(c.getStatus());
		caseDateAddLabel.setText(c.getDateAdded().toString());
		setClient(c);
		if(c.getClient() != null){
			clientNameLabel.setText(c.getClient().getName());
			clientPhoneLabel.setText(c.getClient().getPhone());
			clientEmailLabel.setText(c.getClient().getEmail());
		}

		refreshLists();
	}

	private ObservableList<Deadline> getDataFromADeadlineAndAddToObservableList(String query){
		ObservableList<Deadline> deadlineData = FXCollections.observableArrayList();
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);//"SELECT * FROM deadline;"
			System.out.println(query);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			System.out.println(resultSet);
			int columnsNumber = rsmd.getColumnCount();
			while(resultSet.next()){
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println();
				int deadline_id = resultSet.getInt(1);
				String deadline_title = resultSet.getString("title");
				String deadline_date = resultSet.getString(4);
				System.out.println(deadline_id + " " + deadline_title + " " + deadline_date);
				deadlineData.add(new Deadline(
						deadline_id,
						c,
						deadline_title,
						deadline_date
				));
			}
			connection.close();
			statement.close();
			resultSet.close();
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
			ResultSetMetaData rsmd = resultSet.getMetaData();
			System.out.println(resultSet);
			int columnsNumber = rsmd.getColumnCount();
			while(resultSet.next()){
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println();
				int appointment_id = resultSet.getInt(1);
				String appointment_title = resultSet.getString("title");
				String appointment_room = resultSet.getString("room");
				String appointment_address = resultSet.getString("address");
				String appointment_city = resultSet.getString("city");
				String appointment_state = resultSet.getString("state");
				String appointment_zip = resultSet.getString("zip");
				String appointment_date = resultSet.getString("date");

				System.out.println(appointment_id + " " + appointment_title + " " + appointment_room);
				appointmentData.add(new Appointment(
						appointment_id,
						c,
						appointment_title,
						appointment_room,
						appointment_address,
						appointment_city,
						appointment_state,
						appointment_zip,
						appointment_date
				));
			}
			connection.close();
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return appointmentData;
	}

	public void refreshLists(){
		deadlineTable.getItems().clear();
		deadlineTitleColumn.setCellValueFactory(new PropertyValueFactory<Deadline,String>("title"));
		deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<Deadline,LocalDateTime>("date"));
		deadlines = getDataFromADeadlineAndAddToObservableList("SELECT * FROM deadline WHERE case_id = " + c.getCase_id());
		deadlineTable.getItems().addAll(deadlines);

		appointmentTable.getItems().clear();
		appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment,String>("title"));
		appointmentLocColumn.setCellValueFactory(new PropertyValueFactory<Appointment,String>("room"));
		appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment,LocalDateTime>("date"));
		appointments = getDataFromAnAppointmentAndAddToObservableList("SELECT * FROM appointment WHERE case_id = " + c.getCase_id());
		appointmentTable.getItems().addAll(appointments);
	}
}
