package view;

import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Case;
import model.Deadline;

public class ViewAllDeadlinesController {

	@FXML
	private TableView<Deadline> deadlineTable;
	@FXML
	private TableColumn<Case, String> caseColumn;
	@FXML
	private TableColumn<Deadline, String> deadlineColumn;
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
