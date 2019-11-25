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
import model.Case;
import model.Deadline;

public class ViewAllDeadlinesController implements Initializable {
    private Connection connection;
    private Statement statement;

	@FXML
	private TableView<Deadline> deadlineTable;
	@FXML
	private TableColumn<Deadline, Case> caseColumn;
	@FXML
	private TableColumn<Deadline, String> deadlineColumn;
	@FXML
	private TableColumn<Deadline, String> dateColumn;
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void initialize(URL location, ResourceBundle bundle){
	    deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
	    caseColumn.setCellValueFactory(new PropertyValueFactory<>("case"));
        ObservableList<Deadline> deadlines = getDataFromADeadlineAndAddToObservableList("SELECT * FROM deadline");
        deadlineTable.getItems().addAll(deadlines);
	}

    private Case getDataFromCaseToReturn(int case_id) throws SQLException {
        connection = DatabaseConnection.getConnection();
        statement = connection.createStatement();
        ResultSet aCase = statement.executeQuery("SELECT * FROM aCase WHERE case_id = " + case_id);
        Case c = new Case(
                aCase.getInt("case_id"),
                aCase.getInt("client_id"),
                aCase.getString("title"),
                aCase.getString("status"),
                aCase.getString("dateAdded"),
                aCase.getString("dateResolved"),
                aCase.getString("dateRemoved")
        );
        return c;
    }

    private ObservableList<Deadline> getDataFromADeadlineAndAddToObservableList(String query){
        ObservableList<Deadline> deadlineData = FXCollections.observableArrayList();
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);//"SELECT * FROM deadline;"
            System.out.println(query);
            while(resultSet.next()){
                int deadline_id = resultSet.getInt(1);
                String deadline_title = resultSet.getString("title");
                String deadline_date = resultSet.getString(4);
                String deadline_status = resultSet.getString(5);
                Case c = getDataFromCaseToReturn(resultSet.getInt("case_id"));
                if(c.getStatus().equals("ongoing") && deadline_status.equals("Incomplete")) {
                    deadlineData.add(new Deadline(
                            deadline_id,
                            c,
                            deadline_title,
                            deadline_date,
							deadline_status
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return deadlineData;
    }

	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}
}
