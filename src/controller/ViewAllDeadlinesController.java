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
	private TableColumn<Deadline, LocalDateTime> dateColumn;
	
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	public void initialize(URL location, ResourceBundle bundle){
	    deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
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
                Case c = getDataFromCaseToReturn(resultSet.getInt("case_id"));
                if(c.getStatus().equals("ongoing")) {
                    deadlineData.add(new Deadline(
                            deadline_id,
                            c,
                            deadline_title,
                            deadline_date
                    ));
                }
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return deadlineData;
    }
}
