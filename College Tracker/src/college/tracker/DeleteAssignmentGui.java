/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker;

import college.tracker.database.EventDB;
import college.tracker.database.ToDoDB;
import college.tracker.info.EventInfo;
import college.tracker.info.ToDoInfo;
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
public class DeleteAssignmentGui {
    
    private final FXMLController controller;
    
    public DeleteAssignmentGui(FXMLController controller) {
        this.controller = controller;
    }
    
    public void handleDeleteAssignment() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete Assignment");
        dialog.initModality(Modality.APPLICATION_MODAL);

        ComboBox<ToDoInfo> assignmentComboBox = new ComboBox<>();
        assignmentComboBox.setPromptText("Select an assignment to delete");

        List<ToDoInfo> allAssignments = ToDoDB.getAllToDo();
        assignmentComboBox.getItems().addAll(allAssignments);

        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButton, cancelButton);
        
        VBox Vbox = new VBox(10, new Label("Select Event:"), assignmentComboBox);
        dialog.getDialogPane().setContent(Vbox);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == deleteButton) {
            ToDoInfo selectedAssignment = assignmentComboBox.getValue();
            if (selectedAssignment != null) {
                
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Are you sure you want to delete this assignment?");
                confirmationAlert.setContentText("This action cannot be undone.");
                
                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);

                
                Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();
                
                 if (confirmationResult.isPresent() && confirmationResult.get() == yesButton) {
                    boolean deleted = ToDoDB.deleteAssignment(selectedAssignment);
                    
                    if (deleted) {
                        controller.updateCalendar();
                        controller.updateTableView();
                    }
                }
            }
        }
    }
}
