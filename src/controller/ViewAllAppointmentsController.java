package controller;

import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Appointment;
import model.Case;

public class ViewAllAppointmentsController {
	
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
}
