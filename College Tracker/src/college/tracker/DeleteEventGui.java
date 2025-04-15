
package college.tracker;

import college.tracker.database.EventDB;
import college.tracker.info.EventInfo;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author kayla
 */
public class DeleteEventGui {
    
    private final FXMLController controller;
    
    public DeleteEventGui(FXMLController controller) {
        this.controller = controller;
    }
    
    public void handleDeleteEvent() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete Event");
        dialog.initModality(Modality.APPLICATION_MODAL);

        ComboBox<EventInfo> eventComboBox = new ComboBox<>();
        eventComboBox.setPromptText("Select an event to delete");

        List<EventInfo> allEvents = EventDB.getAllEvent();
        eventComboBox.getItems().addAll(allEvents);

        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButton, cancelButton);
        
        VBox Vbox = new VBox(10, new Label("Select Event:"), eventComboBox);
        dialog.getDialogPane().setContent(Vbox);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == deleteButton) {
            EventInfo selectedEvent = eventComboBox.getValue();

            if (selectedEvent != null) {
                
                if (isEventRecurring(selectedEvent)) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Recurring Event Deletion");
                    confirmationAlert.setHeaderText("Warning: Recurring Event");
                    confirmationAlert.setContentText("The event is recurring, all instances of the event will be deleted as well");
                    
                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                    confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                    Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
                    
                    if (confirmationResult.isPresent() && confirmationResult.get() == yesButton) {
                        boolean deleted = EventDB.deleteEvent(selectedEvent);
                        
                        if (deleted) {
                            controller.updateCalendar();
                        } 
                    }
                } else {
                    
                    boolean deleted = EventDB.deleteEvent(selectedEvent);
                
                    if (deleted) {
                        controller.updateCalendar();
                    } 
                }
            }
        }
    }
    
    private boolean isEventRecurring(EventInfo eventInfo) {
        return eventInfo.getIsRecurring().get() != 0;
    }
   
}
