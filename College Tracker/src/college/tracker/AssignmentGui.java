/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker;

import college.tracker.database.AssignmentDB;
import college.tracker.database.AssignmentInfo;
import college.tracker.database.ClassDB;
import college.tracker.database.ClassInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author kayla
 */
public class AssignmentGui {
    
    private final ObservableList<AssignmentInfo> assignmentList = FXCollections.observableArrayList();
    
    public void handleAddAssignment() {
        
        VBox dialogVBox = new VBox(10);
        ComboBox<ClassInfo> assignmentClass = new ComboBox(); // making a drop down for classes
        TextField assignmentNameInput = new TextField(); // textfield for assignment name
        DatePicker assignmentDueDate = new DatePicker(); // a datepicker for due date
        
        assignmentClass.setPromptText("Which class is this for?");
        assignmentNameInput.setPromptText("Enter the assignment name");
        assignmentDueDate.setPromptText("Enter the due date for this assignment");
        
        addingToClassComboBox(assignmentClass);
        
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        Dialog<ButtonType> assignmentNameDialog = new Dialog<>();
        assignmentNameDialog.setTitle("Add Assignment");
        assignmentNameDialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);
        
         dialogVBox.getChildren().addAll(
                new Label("Class:"), assignmentClass,
                new Label("Assignment Name:"), assignmentNameInput,
                new Label("Assignment Due Date:"), assignmentDueDate);
        assignmentNameDialog.getDialogPane().setContent(dialogVBox);
        
        Optional<ButtonType> result = assignmentNameDialog.showAndWait();
    
        if (result.isPresent() && result.get() == saveButton) {
            
               ClassInfo selectedClass = assignmentClass.getValue();
               String assignmentName = assignmentNameInput.getText();  // Get the assignment name entered by the user
               LocalDate dueDate = assignmentDueDate.getValue();
   

               // Validating thatend date are not null, start date also has to be before end date
               if (selectedClass != null && assignmentName != null && assignmentDueDate != null) {
                              
                   AssignmentInfo newAssignment = new AssignmentInfo(assignmentName, dueDate,"Pending", selectedClass.getId());
                   assignmentList.add(newAssignment);  // Add the new class to the ObservableList
                   
                   // adding the information to the database
                   AssignmentDB assignmentDB = new AssignmentDB();
                   
                   // ensuring that if there are errors, it's displayed
                   boolean assignmentAdded = assignmentDB.addAssignment(newAssignment);
                   if (assignmentAdded == true) {
                       System.out.println("Assignment added");
                   } else {
                       System.out.println("Adding assignment has failed");
                   }
               }        
            }
        }   
    
    // function to adding the actual classInfo objects to the dropdown
    private void addingToClassComboBox(ComboBox<ClassInfo> assignmentClass) {
        
        // Getting the list of classes from our db
        List<ClassInfo> classes = ClassDB.getClasses();
        System.out.println("Classes fetched: " + classes.size());
        
        // converting and setting the items from ObservableList to combo box
        ObservableList<ClassInfo> classInfoList = FXCollections.observableArrayList(classes);
        assignmentClass.setItems(classInfoList);
        
        // Prompt for the combobox
        assignmentClass.setPromptText("Select a class");
        

    }
}
