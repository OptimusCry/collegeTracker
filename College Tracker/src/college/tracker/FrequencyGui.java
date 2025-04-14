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

        // Show the dialog and wait for the result
        Optional<ButtonType> result = frequencyDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Collect selected days if frequency is not "Daily"
            if (!"Daily".equals(frequencyBox.getValue())) {
                if (mondayBox.isSelected()) selectedDays.add("Monday");
                if (tuesdayBox.isSelected()) selectedDays.add("Tuesday");
                if (wednesdayBox.isSelected()) selectedDays.add("Wednesday");
                if (thursdayBox.isSelected()) selectedDays.add("Thursday");
                if (fridayBox.isSelected()) selectedDays.add("Friday");
                if (saturdayBox.isSelected()) selectedDays.add("Saturday");
                if (sundayBox.isSelected()) selectedDays.add("Sunday");
            }
            return true; // Indicate success
        }
        return false; // Indicate cancellation
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
}