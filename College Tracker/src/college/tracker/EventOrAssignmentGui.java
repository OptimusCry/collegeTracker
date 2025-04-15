/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author kayla
 */
public class EventOrAssignmentGui {
    
    private final FXMLController controller;

    public EventOrAssignmentGui(FXMLController controller) {
        this.controller = controller;
    }
    
    public void showAddItemDialog() {
        
        VBox dialogVbox = new VBox(10);
        
        ButtonType addEventBtn = new ButtonType("Add Event", ButtonBar.ButtonData.OK_DONE);
        ButtonType addAssignmentBtn = new ButtonType("Add Assignment", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Select Item to Add");
        dialog.getDialogPane().getButtonTypes().addAll(addEventBtn, addAssignmentBtn, cancelBtn);
        
        dialogVbox.getChildren().add(new Label("Choose whether to add an Event or an Assignment:"));
        dialog.getDialogPane().setContent(dialogVbox);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == addEventBtn) {
                openAddEvent();
            } else if (response == addAssignmentBtn) {
                openAddAssignment();
            }
            
        });
    }
    
    public void showDeleteItemDialog() {
        
        VBox dialogVbox = new VBox(10);
        
        ButtonType deleteEventBtn = new ButtonType("Delete Event", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteAssignmentBtn = new ButtonType("Delete Assignment", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Select Item to Delete");
        dialog.getDialogPane().getButtonTypes().addAll(deleteEventBtn, deleteAssignmentBtn, cancelBtn);
        
        dialogVbox.getChildren().add(new Label("Choose whether to delete an Event or an Assignment:"));
        dialog.getDialogPane().setContent(dialogVbox);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == deleteEventBtn) {
                openDeleteEvent();
            } else if (response == deleteAssignmentBtn) {
                openDeleteAssignment();
            }
            
        });
    }
    
    private void openAddEvent() {
        EventGui eventGui = new EventGui(controller);
        eventGui.handleAddEvent();
    }
    
    private void openAddAssignment() {
        AssignmentGui assignmentGui = new AssignmentGui(controller);
        assignmentGui.handleAddAssignment();
    }
    
    private void openDeleteEvent() {
        DeleteEventGui deleteEventGui = new DeleteEventGui(controller);
        deleteEventGui.handleDeleteEvent();
    }
    
    private void openDeleteAssignment() {
        DeleteAssignmentGui deleteAssignmentGui = new DeleteAssignmentGui(controller);
        deleteAssignmentGui.handleDeleteAssignment();
    }
    
}