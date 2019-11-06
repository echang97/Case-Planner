package controller;

import controller.*;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

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
	private TableColumn<Deadline, String> appointmentTitleColumn;
	@FXML
	private TableColumn<Deadline, LocalDateTime> appointmentDateColumn;
	@FXML
	private TableColumn<Deadline, LocalDateTime> appointmentLocColumn;
	@FXML
	private TableColumn<Deadline, String> appointmentColumn;
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

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}


	public void setDetails() {
		// TODO Auto-generated method stub
		caseTitleLabel.setText(c.getTitle());
		caseStatusLabel.setText(c.getStatus());
		caseDateAddLabel.setText(c.getDateAdded().toString());

		deadlineTable.setItems(c.getDeadlines());
		deadlineTitleColumn.setCellValueFactory(new PropertyValueFactory<Deadline,String>("title"));
		deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<Deadline,LocalDateTime>("date"));

		if(c.getClient() != null){
			clientNameLabel.setText(c.getClient().getName());
			clientPhoneLabel.setText(c.getClient().getPhone());
			clientEmailLabel.setText(c.getClient().getEmail());
		}
	}
}
