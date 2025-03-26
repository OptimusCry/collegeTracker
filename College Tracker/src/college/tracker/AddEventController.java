package college.tracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AddEventController {

    @FXML private TextField textFieldForTitle;
    @FXML private DatePicker dateField;
    @FXML private ComboBox<String> timeField;
    @FXML private Button saveButton, cancelButton;

    private FXMLController mainController;

    public void setMainController(FXMLController controller) {
        this.mainController = controller;
    }

    @FXML
    public void initialize() {
         if (timeField != null) {
             for (int i = 0; i < 48; i++) {
                 int hour = i / 2;
                 int minute = (i % 2) * 30;
                 String time = "";

             if (hour < 10) {
                time += "0" + hour;
             } else {
                time += hour;
             }

             time += ":";

             if (minute == 0) {
                time += "00";
             } else {
                time += minute;
             }
             
             timeField.getItems().add(time);
         }
         timeField.getSelectionModel().select(0); 
         }

         if (saveButton != null) {
             saveButton.setOnAction(e -> {
             saveEvent(); 
         });
         }
     }

    @FXML
    private void saveEvent() {
        String title = textFieldForTitle.getText().trim();
        LocalDate date = dateField.getValue();
        String time = timeField.getValue();

        if (!title.isEmpty() && date != null && time != null) {
            mainController.addEvent(date, title + " @ " + time);
        }
        closeWindow();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}