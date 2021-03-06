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
import javafx.scene.image.Image;
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
	private TableColumn<Deadline, String> deadlineDateColumn;
	@FXML
	private TableColumn<Deadline, String> deadlineStatusColumn;
	@FXML
	private TableView<Appointment> appointmentTable;
	@FXML
	private TableColumn<Appointment, String> appointmentTitleColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentDateColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentLocColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentStatusColumn;
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
		}
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image("/view/case.png"));
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleEdit(ActionEvent event) throws SQLException{
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

			c = controller.getCase();
			setDetails();

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
		caseTitleLabel.setText(c.getTitle());
		caseStatusLabel.setText(c.getStatus());
		caseDateAddLabel.setText(c.getDateAddedString());
		setClient(c);
		if(c.getClient() != null){
			clientNameLabel.setText(c.getClient().getName());
			clientPhoneLabel.setText(c.getClient().getPhone());
			clientEmailLabel.setText(c.getClient().getEmail());
		}else{
			clientNameLabel.setText("No Client");
			clientPhoneLabel.setText("-");
			clientEmailLabel.setText("-");
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

	private void refreshLists(){
		deadlineTable.getItems().clear();
		deadlineTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
		deadlineStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		ObservableList<Deadline> deadlines = getDataFromADeadlineAndAddToObservableList("SELECT * FROM deadline WHERE case_id = " + c.getCase_id());
		deadlineTable.getItems().addAll(deadlines);

		appointmentTable.getItems().clear();
		appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		appointmentLocColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
		appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		ObservableList<Appointment> appointments = getDataFromAnAppointmentAndAddToObservableList("SELECT * FROM appointment WHERE case_id = " + c.getCase_id());
		appointmentTable.getItems().addAll(appointments);
	}
}
