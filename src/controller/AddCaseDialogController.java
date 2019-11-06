package controller;

import controller.*;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class AddCaseDialogController{
	private DatabaseConnection database = new DatabaseConnection();
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	@FXML
	private TextField caseTitleField;
	@FXML
	private Label clientNameLabel;
	@FXML
	private Label clientPhoneLabel;
	@FXML
	private Label clientEmailLabel;
	@FXML
	private TableView<Deadline> deadlineTable;
	@FXML
	private TableColumn<Deadline, String> deadlineTitleColumn;
	@FXML
	private TableColumn<Deadline, LocalDateTime> deadlineDateColumn;
	@FXML
	private TableView<Appointment> appointmentTable;
	@FXML
	private TableColumn<Appointment, String> appointmentTitleColumn;
	@FXML
	private TableColumn<Appointment, String> appointmentLocColumn;
	@FXML
	private TableColumn<Appointment, LocalDateTime> appointmentDateColumn;

	private Stage dialogStage;
	private Case c = new Case();
	private Client client;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	// Event Listener on Button.onAction
	@FXML
	public void addAppointment(ActionEvent event) {
		c.addAppointment(null);
		System.out.println("add appointment");
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
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			c.addDeadline(controller.getDeadline());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void addClientInfo(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddClientInfoDialog.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Add Client Information");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			AddClientInfoDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			client = controller.getClient();
			c.setClient(client);
			clientNameLabel.setText(client.getName());
			clientPhoneLabel.setText(client.getPhone());
			clientEmailLabel.setText(client.getEmail());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) throws SQLException {
		c.setTitle(caseTitleField.getText());
		c.setDateAdded(LocalDateTime.now());
		c.setStatus("ongoing");

		DatabaseController.addCaseToDB(database, c);
		dialogStage.close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	public Case getCase(){
		return c;
	}

}