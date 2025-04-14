package college.tracker;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class FrequencyGui {
    private final CheckBox mondayBox = new CheckBox("Monday");
    private final CheckBox tuesdayBox = new CheckBox("Tuesday");
    private final CheckBox wednesdayBox = new CheckBox("Wednesday");
    private final CheckBox thursdayBox = new CheckBox("Thursday");
    private final CheckBox fridayBox = new CheckBox("Friday");
    private final CheckBox saturdayBox = new CheckBox("Saturday");
    private final CheckBox sundayBox = new CheckBox("Sunday");
    private final ComboBox<String> frequencyBox = new ComboBox<>();
    private final List<String> selectedDays = new ArrayList<>();

    public FrequencyGui() {
        frequencyBox.getItems().addAll("Daily", "Weekly", "Bi-weekly");

        // Set up a listener to handle changes in frequency selection
        frequencyBox.setOnAction(e -> {
            String selectedFrequency = frequencyBox.getValue();
            handleFrequencySelection(selectedFrequency); // Disable or enable days based on frequency
        });
    }

    public boolean showFrequencyGui() {
        Dialog<ButtonType> frequencyDialog = new Dialog<>();
        frequencyDialog.setTitle("Frequency Details");
        frequencyDialog.initModality(Modality.APPLICATION_MODAL); // Ensure it's modal

        VBox dialogPane = new VBox(10);
        dialogPane.getChildren().addAll(
                new Label("Select Frequency:"),
                frequencyBox,
                new Label("Days of the Week:"),
                mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox
        );

        frequencyDialog.getDialogPane().setContent(dialogPane);
        frequencyDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) frequencyDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        
        frequencyBox.setOnAction(e -> validateForm(okButton));
        
        for (CheckBox checkBox : List.of(mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox)) {
            checkBox.setOnAction(e -> validateForm(okButton));
        }

        // Show the dialog and wait for the result
        Optional<ButtonType> result = frequencyDialog.showAndWait();
       
        if (result.isPresent() && result.get() == ButtonType.OK) {
            
            boolean isValid = true;
            
            if (inValid(frequencyBox)) {
                isValid = false;
                showWarningMessage("Please select a frequency");
            }
            
            // Collect selected days if frequency is not "Daily"
            if (!"Daily".equals(frequencyBox.getValue())) {
                if (mondayBox.isSelected()) selectedDays.add("Monday");
                if (tuesdayBox.isSelected()) selectedDays.add("Tuesday");
                if (wednesdayBox.isSelected()) selectedDays.add("Wednesday");
                if (thursdayBox.isSelected()) selectedDays.add("Thursday");
                if (fridayBox.isSelected()) selectedDays.add("Friday");
                if (saturdayBox.isSelected()) selectedDays.add("Saturday");
                if (sundayBox.isSelected()) selectedDays.add("Sunday");
                
                if (selectedDays.isEmpty()) {
                    showErrorStyle(mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox);
                    showWarningMessage("You must selected at least one day");
                    isValid = false;
                }
            }

            if (isValid) {
                resetStyles(mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox);
                resetStyles(frequencyBox); 
                return true; 
            } else {
                return false;
            }
        }
        
        return false; 
    }

    public void handleFrequencySelection(String selectedFrequency) {
        if ("Daily".equals(selectedFrequency)) {
            disableDayCheckboxes();
        } else {
            enableDayCheckboxes();
        }
    }

    public void disableDayCheckboxes() {
        mondayBox.setDisable(true);
        tuesdayBox.setDisable(true);
        wednesdayBox.setDisable(true);
        thursdayBox.setDisable(true);
        fridayBox.setDisable(true);
        saturdayBox.setDisable(true);
        sundayBox.setDisable(true);
    }

    public void enableDayCheckboxes() {
        mondayBox.setDisable(false);
        tuesdayBox.setDisable(false);
        wednesdayBox.setDisable(false);
        thursdayBox.setDisable(false);
        fridayBox.setDisable(false);
        saturdayBox.setDisable(false);
        sundayBox.setDisable(false);
    }

    public List<String> getSelectedDays() {
        return selectedDays;
    }

    public String getSelectedFrequency() {
        return frequencyBox.getValue();
    }
    
    private boolean inValid(ComboBox<String> comboBox) {
        if (comboBox.getValue() == null) {
            comboBox.setStyle("-fx-border-color: red;");
            return true;
        }
        comboBox.setStyle("");
        return false;
    }

    private void showErrorStyle(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setStyle("-fx-border-color: red");
        }
    }

    private void resetStyles(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setStyle(""); 
        }
    }

    private void resetStyles(ComboBox<String> comboBox) {
        comboBox.setStyle(""); 
    }
    
    public void showWarningMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void validateForm(Button okButton) {
        boolean hasFrequency = frequencyBox.getValue() != null;

        if (!hasFrequency) {
            okButton.setDisable(true);
            return;
        }

        if ("Daily".equals(frequencyBox.getValue())) {
            okButton.setDisable(false);
            return;
        }

        boolean atLeastOneDaySelected = mondayBox.isSelected() || tuesdayBox.isSelected() ||
                                        wednesdayBox.isSelected() || thursdayBox.isSelected() ||
                                        fridayBox.isSelected() || saturdayBox.isSelected() ||
                                        sundayBox.isSelected();

        okButton.setDisable(!atLeastOneDaySelected);      
    }
}