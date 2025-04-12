/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker;

import college.tracker.database.EventDB;
import college.tracker.database.RecurringDB;
import college.tracker.info.EventInfo;
import college.tracker.info.RecurringInfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 *
 * @author kayla
 */
public class EventGui {
    
    private final FXMLController controller;
    
    private final Label errorNameLabel;
    private final Label errorStartDateLabel;
    private final Label errorStartTimeLabel;
    private final Label errorEndTimeLabel;
    private final Label errorFrequencyLabel;
    
    private final CheckBox mondayBox;
    private final CheckBox tuesdayBox;
    private final CheckBox wednesdayBox;
    private final CheckBox thursdayBox;
    private final CheckBox fridayBox;
    private final CheckBox saturdayBox;
    private final CheckBox sundayBox;
    
    private List<String> selectedDays = new ArrayList<>();
    private String selectedFrequency = null;
    
    public EventGui(FXMLController controller) {
        this.controller = controller;
        
        // Error labels and the styling
        errorNameLabel = new Label();
        errorStartDateLabel = new Label();
        errorStartTimeLabel = new Label();
        errorEndTimeLabel = new Label();
        errorFrequencyLabel = new Label();
        
        errorNameLabel.setStyle("-fx-text-fill: red;");
        errorStartDateLabel.setStyle("-fx-text-fill: red;");
        errorStartTimeLabel.setStyle("-fx-text-fill: red;");
        errorEndTimeLabel.setStyle("-fx-text-fill: red;");
        errorFrequencyLabel.setStyle("-fx-text-fill: red;");
        
        mondayBox = new CheckBox("Monday");
        tuesdayBox = new CheckBox("Tuesday");
        wednesdayBox = new CheckBox("Wednesday");
        thursdayBox = new CheckBox("Thursday");
        fridayBox = new CheckBox("Friday");
        saturdayBox = new CheckBox("Saturday");
        sundayBox = new CheckBox("Sunday");
        
    }
    
    private final ObservableList<EventInfo> eventList = FXCollections.observableArrayList();
    
    public void handleAddEvent() {
        
        resetErrorLabels();
        
        TextField eventNameInput = new TextField();
        TextField eventDescriptionInput = new TextField();
        DatePicker eventStartDay = new DatePicker();
        TextField eventStartInput = new TextField();
        DatePicker eventEndDay = new DatePicker();
        TextField eventEndInput = new TextField();
        TextField eventLocationInput = new TextField();
        
        RadioButton yesBtn = new RadioButton("Yes");
        RadioButton noBtn = new RadioButton("No");
        ToggleGroup recurringGroup = new ToggleGroup();
        yesBtn.setToggleGroup(recurringGroup);
        noBtn.setToggleGroup(recurringGroup);
        noBtn.setSelected(true);
        
        ComboBox<String> frequencyBox = new ComboBox<>();
        frequencyBox.getItems().addAll("Daily", "Weekly", "Bi-Weekly", "Monthly");
        frequencyBox.setDisable(true);
        
        Dialog<ButtonType> eventDialog = new Dialog<>();
        eventDialog.setTitle("Add Event");
        
        ButtonType nextButton = new ButtonType("Next", ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType backButtonType = new ButtonType("Back", ButtonBar.ButtonData.BACK_PREVIOUS);
        
        eventDialog.getDialogPane().getButtonTypes().addAll(nextButton, cancelButton);
        
        // Prompts
        eventNameInput.setPromptText("Enter the event name");
        eventDescriptionInput.setPromptText("Enter a description of the event");
        eventStartDay.setPromptText("Enter the start date of the event");
        eventStartInput.setPromptText("Enter the start time (24 hour format, ex: HH:mm)");
        eventEndDay.setPromptText("Enter the end date of the event");
        eventEndInput.setPromptText("Enter the end time (24 hour format, ex: HH:mm)");
        eventLocationInput.setPromptText("Enter the event location");
        // End of prompts
        
        VBox dialogVBox = new VBox(10);
        intitialDialogBox(dialogVBox, eventNameInput, eventDescriptionInput, eventStartDay, eventStartInput, eventEndDay, eventEndInput,
                         eventLocationInput, yesBtn, noBtn);
        
        eventDialog.getDialogPane().setContent(dialogVBox);
        
        yesBtn.setOnAction(e -> {
            Platform.runLater(() -> {
                frequencyBox.setDisable(false);
                frequencyBox.requestFocus();  // Explicitly request focus for the combo box
                System.out.println("Frequency box enabled");
            });
            
        });
        
        noBtn.setOnAction(e -> {
            Platform.runLater(() -> {
                frequencyBox.setDisable(true);
                System.out.println("Frequency box disabled");
            });
        });
        
        Button nextBtn = (Button) eventDialog.getDialogPane().lookupButton(nextButton);
        nextBtn.setOnAction(e -> {
             if (yesBtn.isSelected()) {
                boolean dialogSuccess = openFrequencyDialog(frequencyBox);
                
                if (dialogSuccess) {
                    eventDialog.getDialogPane().getButtonTypes().setAll(backButtonType, saveButton, cancelButton);
                }
            } else {
                frequencyBox.setValue(null);
                frequencyBox.setDisable(true);
                mondayBox.setSelected(false); 
                tuesdayBox.setSelected(false);
                wednesdayBox.setSelected(false);
                thursdayBox.setSelected(false);
                fridayBox.setSelected(false);
                saturdayBox.setSelected(false);
                sundayBox.setSelected(false);
                
                saveEvent(
                    eventNameInput.getText(), 
                    eventDescriptionInput.getText(), 
                    eventStartDay.getValue(), 
                    eventStartInput.getText(), 
                    eventEndDay.getValue(), 
                    eventEndInput.getText(), 
                    eventLocationInput.getText(), 
                    0, 
                    null
                );

            }

        });
        
        Button cancelBtn = (Button) eventDialog.getDialogPane().lookupButton(cancelButton);
        cancelBtn.setOnAction(e -> {
             eventDialog.close();
         });
        
        // Show the dialog and wait for the user input
        Optional<ButtonType> result = eventDialog.showAndWait();
             
        if (result.isPresent() && result.get() == saveButton) {
            String name = eventNameInput.getText();
            String description = eventDescriptionInput.getText();
            LocalDate startDate = eventStartDay.getValue();
            String startTime = eventStartInput.getText();
            LocalDate endDate = eventEndDay.getValue();
            String endTime = eventEndInput.getText();
            String location = eventLocationInput.getText();
            int isRecurring = yesBtn.isSelected() ? 1 : 0;
            String frequency = (isRecurring == 1) ? frequencyBox.getValue() : null;
            
            saveEvent(name, description, startDate, startTime, endDate, endTime, location, isRecurring, frequency);
            
            if (isRecurring == 1) {
                frequency = frequencyBox.getValue();
            }
            
            // Validating
            boolean isValid = validateForm(name, startDate, startTime, endTime, isRecurring, frequency);          
            if (!isValid) {
                return;
            }
           
            
        }
    }
    
    private void saveEvent(String name, String description, LocalDate startDate, String startTime, LocalDate endDate, String endTime, String location, int isRecurring, String frequency) {
        
        boolean isValid = validateForm(name, startDate, startTime, endTime, isRecurring, frequency);
        if (!isValid) {
            return;
        }
        
        LocalDateTime startDateTime = parseDateTime(startDate, startTime, errorStartTimeLabel);
            if (startDateTime == null) return;
            
            LocalDateTime endDateTime = parseDateTime(endDate, endTime, errorEndTimeLabel);
            if (endDateTime == null) return;
            
            EventInfo eventInfo = new EventInfo(name, description, startDateTime, endDateTime, location, isRecurring, "scheduled");
            System.out.println("Adding event: " + name);
            boolean success = EventDB.addEvent(eventInfo);
            System.out.println("Event added: " + success);
            
            if (success) {
                int eventId = EventDB.getNewestEventId();
                
                EventInfo fullEventInfo = new EventInfo(eventId, name, description, startDateTime, endDateTime, location, isRecurring, "scheduled");
                eventList.add(fullEventInfo);
                
                if (isRecurring == 1) {
                    handleRecurrence(eventId, frequency, mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox, saturdayBox, sundayBox, startDate, endDate, name, description, location, isRecurring);
                }
                
            } else {
                System.out.println("Error saving event");
            }
    } 
    
    private boolean openFrequencyDialog(ComboBox<String> frequencyBox) {
        Dialog<ButtonType> frequencyDialog = new Dialog<>();
        frequencyDialog.setTitle("Frequency Details");
        
        selectedDays.clear();
        frequencyBox.getItems().clear();
        
        frequencyBox.getItems().addAll("Daily", "Weekly", "Monthly");
        frequencyBox.setPromptText("Select Frequency");
                
        VBox daysBox = new VBox(5,
            mondayBox, tuesdayBox, wednesdayBox, thursdayBox,
            fridayBox, saturdayBox, sundayBox
        );
       

        VBox dialogVBox = new VBox(10);
        dialogVBox.getChildren().addAll(
            new Label("Selected Frequency:"), frequencyBox,
            new Label("Days of the Week:"), daysBox,
            errorFrequencyLabel
        );
        
        frequencyDialog.getDialogPane().setContent(dialogVBox);
        
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        frequencyDialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);
       
        Optional<ButtonType> result = frequencyDialog.showAndWait();

            if (result.isPresent() && result.get() == saveButton) {
                String localSelectedVariable = frequencyBox.getValue();
                selectedDays.clear();

                if (mondayBox.isSelected()) selectedDays.add("Monday");
                if (tuesdayBox.isSelected()) selectedDays.add("Tuesday");
                if (wednesdayBox.isSelected()) selectedDays.add("Wednesday");
                if (thursdayBox.isSelected()) selectedDays.add("Thursday");
                if (fridayBox.isSelected()) selectedDays.add("Friday");
                if (saturdayBox.isSelected()) selectedDays.add("Saturday");
                if (sundayBox.isSelected()) selectedDays.add("Sunday");

                if (localSelectedVariable == null || localSelectedVariable.isEmpty()) {
                    errorFrequencyLabel.setText("Please select a frequency.");
                    return false;
                } else if (selectedDays.isEmpty()) {
                    errorFrequencyLabel.setText("Please select at least one day.");
                    return false;
                } else {
                    errorFrequencyLabel.setText("");
                    return true;
                }
            }
        
        return false;

    }
    
    private void handleRecurrence(int eventId, String frequency, CheckBox mondayBox, CheckBox tuesdayBox, CheckBox wednesdayBox, CheckBox thursdayBox,
                                  CheckBox fridayBox, CheckBox saturdayBox, CheckBox sundayBox, LocalDate startDate, LocalDate endDate, String name, String Description, String location, int isRecurring) {
        
        if (isRecurring == 1 && selectedDays.isEmpty()) {
            errorFrequencyLabel.setText("At least one day must be chosen if you selected yes to recurring event");
            return;
        }
            
        if (isRecurring == 1) {
            switch (frequency) {
                case "Daily" -> handleDailyRecurrence(eventId, startDate, endDate);
                case "Weekly" -> handleWeeklyRecurrence(eventId, startDate, selectedDays, endDate);
                case "Bi-Weekly" -> handleBiWeeklyRecurrence(eventId, startDate, selectedDays, endDate);
                case "Monthly" -> handleMonthlyRecurrence(eventId, startDate, endDate);
                default -> {
                }      
            }

        }
    }
    
    private void handleDailyRecurrence(int eventId, LocalDate startDate, LocalDate endDate) {
        LocalDate nextOccurence = startDate;
        
        while (nextOccurence != null && !nextOccurence.isAfter(endDate)) {
            
            String dayOfWeek = nextOccurence.getDayOfWeek().name();
                RecurringInfo recurringInfo = new RecurringInfo(0, "Daily", dayOfWeek, nextOccurence, endDate, eventId);
                boolean added = RecurringDB.addRecurring(recurringInfo);
                
                if (added) {
                    System.out.println("Added Daily recurrence for " + dayOfWeek + " on " + nextOccurence);
                } else {
                    System.err.println("Failed to add weekly recurrence for " + dayOfWeek + " on " + nextOccurence);
                }
                nextOccurence = nextOccurence.plusWeeks(1); 
            }
    }
    
    private void handleWeeklyRecurrence(int eventId, LocalDate startDate, List<String> selectedDays, LocalDate endDate) {
        for (String day : selectedDays) {
            LocalDate nextOccurence = getNextOccurenceForDay(startDate, day);
            
            
            while (nextOccurence != null && nextOccurence.isBefore(LocalDate.now())) {
                nextOccurence = nextOccurence.plusWeeks(1);
            }
            
            while (nextOccurence != null && !nextOccurence.isAfter(endDate)) {
                
                RecurringInfo recurringInfo = new RecurringInfo(0, "Weekly", day, nextOccurence, endDate, eventId);
                boolean added = RecurringDB.addRecurring(recurringInfo);
                
                if (added) {
                    System.out.println("Added weekly recurrence for " + day + " on " + nextOccurence);
                } else {
                    System.err.println("Failed to add weekly recurrence for " + day);
                }
                
                nextOccurence = nextOccurence.plusWeeks(1); 
            }
        }
    }
    
    private void handleBiWeeklyRecurrence(int eventId, LocalDate startDate, List<String> selectedDays, LocalDate endDate) {
        for (String day : selectedDays) {
            LocalDate nextOccurence = getNextOccurenceForDay(startDate, day).plusWeeks(2);
            
            while (nextOccurence != null && !nextOccurence.isAfter(endDate)) {
                
                RecurringInfo recurringInfo = new RecurringInfo(0, "Bi-Weekly", day, nextOccurence, endDate, eventId);
                boolean added = RecurringDB.addRecurring(recurringInfo);
                
                if (added) {
                    System.out.println("Added Bi-weekly recurrence for " + day + " on " + nextOccurence);
                } else {
                    System.err.println("Failed to add Bi-weekly recurrence for " + day);
                }
                
                nextOccurence = nextOccurence.plusWeeks(1); 
            }
            
        }
    }
    
    private void handleMonthlyRecurrence(int eventId, LocalDate startDate, LocalDate endDate) {
        LocalDate nextOccurence = startDate.plusMonths(1);
        
        while (nextOccurence != null && !nextOccurence.isAfter(endDate)) {
            
            String dayOfWeek = nextOccurence.getDayOfWeek().name();
            
                RecurringInfo recurringInfo = new RecurringInfo(0, "Monthly", dayOfWeek, nextOccurence, endDate, eventId);
                boolean added = RecurringDB.addRecurring(recurringInfo);
                
                if (added) {
                    System.out.println("Added Monthly recurrence for " + dayOfWeek + " on " + nextOccurence);
                } else {
                    System.err.println("Failed to add Monthly recurrence for " + dayOfWeek + " on " + nextOccurence);
                }
                nextOccurence = nextOccurence.plusWeeks(1); 
            }
    }
    
    
    private LocalDate getNextOccurenceForDay(LocalDate startDate, String dayOfWeek) {
        
        if (startDate == null) {
            return null;
        }
        
        LocalDate nextOccurence = startDate;
        int targetDayOfWeek = getDayOfWeekNumber(dayOfWeek);
        
        while (nextOccurence.getDayOfWeek().getValue() != targetDayOfWeek) {
            nextOccurence = nextOccurence.plusDays(1);
        }
        
        return nextOccurence;
    }
    
    private int getDayOfWeekNumber(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "Monday" -> 1;
            case "Tuesday" -> 2;
            case "Wednesday" -> 3;
            case "Thursday" -> 4;
            case "Friday" -> 5;
            case "Saturday" -> 6;
            case "Sunday" -> 7;
            default -> -1;
        };
    }
    
    private void resetErrorLabels() {
        errorNameLabel.setText("");
        errorStartDateLabel.setText("");
        errorStartTimeLabel.setText("");
        errorEndTimeLabel.setText("");
        errorFrequencyLabel.setText("");
    }
    
    private boolean validateForm(String name, LocalDate startDate, String startTime, String endTime, int isRecurring, String frequency) {
        
        boolean isValid = true;
            
            if (name.isEmpty()) {
                errorNameLabel.setText("Event name is required");
                isValid = false;
            }
            
            if (startDate == null) {
                errorStartDateLabel.setText("Event start date is required");
                isValid = false;
            }
            
            if (startTime.isEmpty()) {
                errorStartTimeLabel.setText("Event start time is required");
                isValid = false;
            }
            
            if (endTime.isEmpty()) {
                errorEndTimeLabel.setText("Event start time is required");
                isValid = false;
            }
            
            if (isRecurring == 1 && (frequency == null || frequency.isEmpty())) {
                errorFrequencyLabel.setText("Frequency is required when the event is recurring");
                isValid = false;
            }
            
        return isValid;
    }
    
    private void intitialDialogBox(VBox dialogVBox, TextField eventNameInput, TextField eventDescriptionInput, 
                                          DatePicker eventStartDay, TextField eventStartInput, 
                                          DatePicker eventEndDay, TextField eventEndInput, 
                                          TextField eventLocationInput, RadioButton yesBtn, RadioButton noBtn) {
        
        dialogVBox.getChildren().addAll(
                new Label("Event Name:"), eventNameInput, errorNameLabel,
                new Label("Event Description:"), eventDescriptionInput,
                new Label("Event Start Date:"), eventStartDay, errorStartDateLabel,
                new Label("Event Start Time:"), eventStartInput, errorStartTimeLabel,
                new Label("Event End Date:"), eventEndDay,
                new Label("Event End Time:"), eventEndInput,
                new Label("Event Location:"), eventLocationInput,
                new Label("Is Recurring:"), yesBtn, noBtn
        );
    }
    
    private LocalDateTime parseDateTime(LocalDate date, String time, Label errorLabel) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        try {
            LocalTime localTime = LocalTime.parse(time, timeFormatter);
            return LocalDateTime.of(date, localTime);
        } catch (DateTimeParseException e) {
            errorLabel.setText("Invalid time format (use HH:mm)");
            return null;
        }
        
    }
    
    
}
