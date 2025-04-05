/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker;

import college.tracker.database.ClassDB;
import college.tracker.database.ClassInfo;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class ClassGui {
    
    private final FXMLController controller;
    
    public ClassGui(FXMLController controller) { // needed to refresh the table after the add class button was pressed, but couldn't directly, so I had to looked up how to pass by reference
        this.controller = controller;
    }
    
    private final ObservableList<ClassInfo> classList = FXCollections.observableArrayList();
    
    public void handleAddClass() {
        
        // Create a VBox for the dialog content
        VBox dialogVBox = new VBox(10);
        TextField classNameInput = new TextField();
        DatePicker classStartDate = new DatePicker();
        DatePicker classEndDate = new DatePicker();
  
        ColorPicker colorPicker = new ColorPicker();

        // Setting prompt text
        classNameInput.setPromptText("Enter Class Name");
        classEndDate.setPromptText("Enter the end date for this class");
        classStartDate.setPromptText("Enter the start date for this class");

        // Buttons for saving or canceling the input
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Create and configure the dialog
        Dialog<ButtonType> classNameDialog = new Dialog<>();
        classNameDialog.setTitle("Add Class");
        classNameDialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);

        // Add components to the dialog
        dialogVBox.getChildren().addAll(
                new Label("Class Name:"), classNameInput,
                new Label("Class Start Date:"), classStartDate,
                new Label("Class End Date:"), classEndDate,
                new Label("Select Color:"), colorPicker);
        classNameDialog.getDialogPane().setContent(dialogVBox);

        // Show the dialog and wait for the user input
        Optional<ButtonType> result = classNameDialog.showAndWait();

        // Process the result when user confirms the dialog
        if (result.isPresent() && result.get() == saveButton) {
            String className = classNameInput.getText();  // Get the class name entered by the user
           
            LocalDate startDate = classStartDate.getValue();
            LocalDate endDate = classEndDate.getValue();
            Color color = colorPicker.getValue();         // Get the color selected by the user
            
            // Validating that start date and end date are not null, start date also has to be before end date
            if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
             
                ClassInfo newClass = new ClassInfo(className, startDate, endDate, color, "Active");
                classList.add(newClass);  // Add the new class to the ObservableList
                
                // Converting color to hex string to save to the database
                String colorHex = newClass.toHexString();
                
                // adding the information to the database
                ClassDB classDB = new ClassDB();
                   
                   // ensuring that if there are errors, it's displayed
                   
                   try {
                       boolean classAdded = classDB.addClass(newClass);
                       if (classAdded == true) {
                          
                           controller.updateHomePageTable();
                       } else {
                           System.out.println("Adding class has failed");
                       }
                   } catch (SQLException e) {
                       e.printStackTrace();
                       System.out.println("Error: " + e.getMessage());
                   }              
            }
        }   
    }   
}
