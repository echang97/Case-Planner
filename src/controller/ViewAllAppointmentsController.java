package controller;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Case;

public class ViewAllAppointmentsController implements Initializable {
    private Connection connection;
    private Statement statement;
	
	@FXML
	private TableView<Appointment> appointmentTable;
	@FXML
	private TableColumn<Case, String> caseColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentColumn;
	@FXML
	private TableColumn<String, String> locationColumn;
	@FXML
	private TableColumn<LocalDateTime, String> dateColumn;
		
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

    private Case getDataFromCaseToReturn(int case_id) throws SQLException {
        connection = DatabaseConnection.getConnection();
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

    private ObservableList<Appointment> getDataFromAnAppointmentAndAddToObservableList(){
        ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointment");//"SELECT * FROM deadline;"
            System.out.println("SELECT * FROM appointment");
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
                if(c.getStatus().equals("ongoing") && appointment_status.equals("Incomplete")){
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
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return appointmentData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        caseColumn.setCellValueFactory(new PropertyValueFactory<>("case"));
        appointmentColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        ObservableList<Appointment> appointments = getDataFromAnAppointmentAndAddToObservableList();
        appointmentTable.getItems().addAll(appointments);

    }
}
