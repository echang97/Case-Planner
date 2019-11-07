package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Case;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class EditCaseDetailsController {
	@FXML
	private TextField caseTitleField;
	@FXML
	private TableView clientTable;
	@FXML
	private TableColumn clientNameColumn;
	@FXML
	private TableColumn clientPhoneColumn;
	@FXML
	private TableColumn clientEmailColumn;
	@FXML
	private TableView deadlineTable;
	@FXML
	private TableColumn deadlineTitleColumn;
	@FXML
	private TableColumn deadlineDateColumn;
	@FXML
	private TableView appointmentTable;
	@FXML
	private TableColumn appointmentTitleColumn;
	@FXML
	private TableColumn appointmentLocationColumn;
	@FXML
	private TableColumn appointmentDateColumn;
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
			controller.setDialogStage(dialogStage);
			controller.setCase(c);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteDeadline(ActionEvent event) {
		// TODO Autogenerated
		System.out.println("DELETED");
	}
	// Event Listener on Button.onAction
	@FXML
	public void editDeadline(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditDeadlineDialogue.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Deadline");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditDeadlineDialogueController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void editAppointment(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditAppointmentDialogue.fxml"));
		try {
			Parent root = (Parent) loader.load();
			Stage dialogStage = new Stage();
			Scene scene = new Scene(root);
			dialogStage.setScene(scene);
			dialogStage.setTitle("Edit Appointment");
			dialogStage.initModality(Modality.WINDOW_MODAL);

			EditAppointmentDialogueController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void deleteAppointment(ActionEvent event) {
		// TODO Autogenerated
		System.out.println("DELETED");
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) {
		//TODO implement
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

}
