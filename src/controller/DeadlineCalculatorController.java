package controller;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

public class DeadlineCalculatorController{
	@FXML
	private RadioButton beforeRadioButton;
	@FXML
	private ToggleGroup beforeAfter;
	@FXML
	private TextField days;
	@FXML
	private DatePicker arbitraryDateField;
	@FXML
	private DatePicker newDateField;
	private Stage dialogStage;
	private LocalDate outputDate;

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image("/view/case.png"));
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleSubmit(ActionEvent event) {
		outputDate = newDateField.getValue();
		dialogStage.close();
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleClose(ActionEvent event) {
		dialogStage.close();
	}

	public void handleCalculate(ActionEvent event){
		LocalDate dateOfCalculation = arbitraryDateField.getValue();
		if(beforeRadioButton.isSelected()){
			newDateField.setValue(
					dateOfCalculation.minusDays
					(Integer.parseInt(days.getText())));
		}else{
			newDateField.setValue(
					dateOfCalculation.plusDays
					(Integer.parseInt(days.getText())));
		}
	}

	public LocalDate getDate(){
		return outputDate;
	}
}
