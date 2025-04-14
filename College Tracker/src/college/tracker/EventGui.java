package college.tracker;

import college.tracker.database.EventDB;
import college.tracker.database.RecurringDB;
import college.tracker.info.EventInfo;
import college.tracker.info.RecurringInfo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author kayla
 */
public class EventGui {
    
    private final FXMLController controller;
    
    private FrequencyGui frequencyGui;
    
    private final Label errorNameLabel;
    private final Label errorStartDateLabel;
    private final Label errorStartTimeLabel;
    private final Label errorEndTimeLabel;
    private final Label errorFrequencyLabel;
    
    private final ObservableList<EventInfo> eventList = FXCollections.observableArrayList();
    
    private EventInfo savedEventInfo;
    private String selectedFrequency;
    private List<String> selectedDays = new ArrayList<>();
    
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
        
        
    }
    
   
    public void handleAddEvent() {
        
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
         
        Dialog<ButtonType> eventDialog = new Dialog<>();
        eventDialog.setTitle("Add Event");
        
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        
        eventDialog.getDialogPane().getButtonTypes().addAll(cancelButton);
        eventDialog.initModality(Modality.NONE);
        
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
            System.out.println("Recurring event selected. Frequency dialog will show after 'Next' is clicked.");
            
        });
        
        noBtn.setOnAction(e -> {
            selectedFrequency = null;
            selectedDays.clear();
            System.out.println("Non-recurring event selected.");
        });
        
        Button nextBtn = new Button("Next");
        nextBtn.setOnAction(e -> {

            boolean isRecurring = yesBtn.isSelected();

            String name = eventNameInput.getText();
            String description = eventDescriptionInput.getText();
            LocalDate startDate = eventStartDay.getValue();
            String startTime = eventStartInput.getText();
            LocalDate endDate = eventEndDay.getValue();
            String endTime = eventEndInput.getText();
            String location = eventLocationInput.getText();
            
            boolean isValid = validateForm(name, startDate, startTime, endTime, endDate,
                                       eventNameInput, eventStartDay, eventStartInput,
                                       eventEndDay, eventEndInput);
            if (!isValid) {
                nextBtn.setDisable(false);
                return;
            }

            LocalDateTime startDateTime = parseDateTime(startDate, startTime);
            LocalDateTime endDateTime = parseDateTime(endDate, endTime);

            if (startDateTime == null || endDateTime == null) {
                nextBtn.setDisable(false);
                return;
            }
          
            EventInfo savedEventInfo = new EventInfo(name, description, startDateTime, endDateTime, location, isRecurring ? 1 : 0, "scheduled");

            if (isRecurring) {
              
                FrequencyGui frequencyGui = new FrequencyGui();
                boolean result = frequencyGui.showFrequencyGui();

                if (result) {
                    selectedFrequency = frequencyGui.getSelectedFrequency();
                    frequencyGui.handleFrequencySelection(selectedFrequency);
                    selectedDays = frequencyGui.getSelectedDays();
                    System.out.println("Selected Frequency: " + selectedFrequency);
                    System.out.println("Selected Days: " + selectedDays);
                    
                    if (selectedFrequency == null || selectedFrequency.isEmpty()) {
                        frequencyGui.showWarningMessage("Please selected a frequency for the recurring event");
                        return;
                    }
                                        
                    saveEvent(savedEventInfo, selectedFrequency, selectedDays);
                    eventDialog.close();
                } else {
                    System.out.println("Frequency dialog cancelled");
                }
            } else {
                saveEvent(savedEventInfo, null, null);
                eventDialog.close();
            }
        });

    dialogVBox.getChildren().add(nextBtn);

    eventDialog.getDialogPane().setContent(dialogVBox);
        
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
            
            LocalDateTime startDateTime = parseDateTime(startDate, startTime);
            LocalDateTime endDateTime = parseDateTime(endDate, endTime);
            
            if (startDateTime == null || endDateTime == null) {
                return;
            }
            
            // Validating
            boolean isValid = validateForm(name, startDate, startTime, endTime, endDate,
                                       eventNameInput, eventStartDay, eventStartInput,
                                       eventEndDay, eventEndInput);
            
            if (!isValid) {
                return;
            }
            
            EventInfo eventInfo = new EventInfo(name, description, startDateTime, endDateTime, location, isRecurring, "scheduled");
            saveEvent(eventInfo, selectedFrequency, selectedDays);

        }
    }
    
    private void saveEvent(EventInfo eventInfo, String selectedFrequency, List<String> selectedDays) {
        String name = eventInfo.getName().get();
        String description = eventInfo.getDescription().get();
        LocalDateTime startDateTime = eventInfo.getStartTime().get();
        LocalDateTime endDateTime = eventInfo.getEndTime().get();
        String location = eventInfo.getLocation().get();
        int isRecurring = eventInfo.getIsRecurring().get(); 
        String status = eventInfo.getStatus().get();

        System.out.println("Adding event: " + name);
        
        System.out.println("isRecurring value before saving: " + isRecurring);

        boolean success = EventDB.addEvent(eventInfo);

        if (!success) {
            System.out.println("Failed to save the event");
        } else {
            System.out.println("The event was saved: " + name);
        }

        int eventId = EventDB.getNewestEventId();
        EventInfo fullEventInfo = new EventInfo(eventId, name, description, startDateTime, endDateTime, location, isRecurring, status);
        eventList.add(fullEventInfo);

        if (isRecurring == 1 && selectedFrequency != null) {
            handleRecurrence(eventId, selectedFrequency, startDateTime.toLocalDate(), endDateTime.toLocalDate(), selectedDays);
            
        }
    }
        
    private void handleRecurrence(int eventId, String frequency, LocalDate startDate, LocalDate endDate, List<String> selectedDays) {
        
        System.out.println("in handleRecurrence ");
        System.out.println("Frequency: " + frequency);
        
        if (frequency == null) return;

        LocalDate currentDate = startDate;

        System.out.println("Now prior to the switch statement");
        switch (frequency) {
            case "Daily" -> {

                while (!currentDate.isAfter(endDate)) {
                    String day = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                    createRecurringEvent(currentDate, frequency, day, endDate, eventId);
                    currentDate = currentDate.plusDays(1);
                }
            }

            case "Weekly" -> {
                System.out.println("Inside the weekly switch case");
                if (selectedDays == null || selectedDays.isEmpty()) return;

                for (String day : selectedDays) {
                    LocalDate eventDate = getNextWeekdayFrom(startDate, day);

                    while (!eventDate.isAfter(endDate)) {
                        createRecurringEvent(eventDate, frequency, day, endDate, eventId);
                        eventDate = eventDate.plusWeeks(1);
                    }
                }
            }

            case "Bi-weekly" -> {
                System.out.println("Inside the bi-weekly switch case");
                if (selectedDays == null || selectedDays.isEmpty()) return;

                for (String day : selectedDays) {
                    LocalDate eventDate = getNextWeekdayFrom(startDate, day);

                    while (!eventDate.isAfter(endDate)) {
                        createRecurringEvent(eventDate, frequency, day, endDate, eventId);
                        eventDate = eventDate.plusWeeks(2); 
                    }
                }
            }

            // Want to implement monthly here
        }
        
    }

    private void createRecurringEvent(LocalDate date, String frequency, String dayOfWeek, LocalDate endDate, int eventId) {
        
        RecurringInfo recurringInfo = new RecurringInfo(0, frequency, dayOfWeek, date, endDate, eventId);
        boolean recurrenceSuccess = RecurringDB.addRecurring(recurringInfo);
        
        if (recurrenceSuccess) {
            System.out.println("Recurring event added: " + recurrenceSuccess);
        } else {
            System.out.println("Recurring event failed");
        }
    }
   
    private boolean validateForm(String name, LocalDate startDate, String startTimeStr, String endTimeStr, 
                             LocalDate endDate, TextField eventNameInput, DatePicker eventStartDay, 
                             TextField eventStartInput, DatePicker eventEndDay, TextField eventEndInput) {
        boolean isValid = true;

        // Validate event name
        if (name == null || name.trim().isEmpty()) {
            eventNameInput.setStyle("-fx-border-color: red;");
            errorNameLabel.setText("Event name cannot be empty.");
            isValid = false;
        } else {
            clearStyle(eventNameInput);
            errorNameLabel.setText("");
        }

        // Validate start date
        if (startDate == null) {
            eventStartDay.setStyle("-fx-border-color: red;");
            errorStartDateLabel.setText("Start date is required.");
            isValid = false;
        } else {
            clearStyle(eventStartDay);
            errorStartDateLabel.setText("");
        }

        // Validate end date
        if (endDate == null) {
            eventEndDay.setStyle("-fx-border-color: red;");
            errorStartDateLabel.setText("End date is required.");
            isValid = false;
        } else {
            clearStyle(eventEndDay);
            errorEndTimeLabel.setText("");
            // Ensure end date is after start date
            if (startDate != null && endDate.isBefore(startDate)) {
                eventEndDay.setStyle("-fx-border-color: red;");
                errorEndTimeLabel.setText("End date must be after start date.");
                isValid = false;
            }
        }

        // Validate start time and end time
        LocalDateTime startDateTime = parseDateTime(startDate, startTimeStr);
        LocalDateTime endDateTime = parseDateTime(endDate, endTimeStr);

        if (startDateTime == null) {
            eventStartInput.setStyle("-fx-border-color: red;");
            errorStartTimeLabel.setText("Invalid start time.");
            isValid = false;
        } else {
            clearStyle(eventStartInput);
            errorStartTimeLabel.setText("");
        }

        if (endDateTime == null) {
            eventEndInput.setStyle("-fx-border-color: red;");
            errorEndTimeLabel.setText("Invalid end time.");
            isValid = false;
        } else {
            clearStyle(eventEndInput);
            errorEndTimeLabel.setText("");
        }

        return isValid;
    }

    private void clearStyle(TextField inputField) {
        inputField.setStyle("");
    }

    private void clearStyle(DatePicker datePicker) {
        datePicker.setStyle("");
    }
    
    private void intitialDialogBox(VBox dialogVBox, TextField eventNameInput, TextField eventDescriptionInput, 
                                          DatePicker eventStartDay, TextField eventStartInput, 
                                          DatePicker eventEndDay, TextField eventEndInput, 
                                          TextField eventLocationInput, RadioButton yesBtn, RadioButton noBtn) {
        
        dialogVBox.getChildren().addAll(
                new Label("Event Name:"), eventNameInput,
                new Label("Event Description:"), eventDescriptionInput,
                new Label("Event Start Date:"), eventStartDay,
                new Label("Event Start Time:"), eventStartInput,
                new Label("Event End Date:"), eventEndDay,
                new Label("Event End Time:"), eventEndInput,
                new Label("Event Location:"), eventLocationInput,
                new Label("Is Recurring:"), yesBtn, noBtn
        );
    }
    
    private LocalDateTime parseDateTime(LocalDate date, String time) {
        
        time = time.replaceAll("\\s+", "");
        
        if (time.length() == 4) {
            time = "0" + time;
        }
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        try {
            LocalTime localTime = LocalTime.parse(time, timeFormatter);
            return LocalDateTime.of(date, localTime);
        } catch (DateTimeParseException e) {
           
            return null;
        }
        
    }
        
    private LocalDate getNextWeekdayFrom(LocalDate startDate, String targetDay) {
        DayOfWeek target = DayOfWeek.valueOf(targetDay.toUpperCase());
        int daysToAdd = (target.getValue() - startDate.getDayOfWeek().getValue() + 7) % 7;

        if (daysToAdd == 0) {
            daysToAdd = 0; // same day
        }

        return startDate.plusDays(daysToAdd);
    }
          
}