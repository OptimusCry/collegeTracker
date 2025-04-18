
package college.tracker;

import college.tracker.database.AssignmentDB;
import college.tracker.database.ClassDB;
import college.tracker.database.EventDB;
import college.tracker.database.HomePageDB;
import college.tracker.database.RecurringDB;
import college.tracker.database.ThemeDB;
import college.tracker.database.ToDoDB;
import college.tracker.info.AssignmentInfo;
import college.tracker.info.ClassInfo;
import college.tracker.info.ColorCell;
import college.tracker.info.EventInfo;
import college.tracker.info.HomePageInfo;
import college.tracker.info.RecurringInfo;
import college.tracker.info.ThemeInfo;
import college.tracker.info.ToDoInfo;
import java.net.URL;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


public class FXMLController implements Initializable {
    
    private Stage primaryStage;
    
    private Scene scene;
    
    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    
    // Start of code for HomePage
    @FXML
    private TextField todaysDate;
    
    @FXML
    private TableColumn<HomePageInfo, String> classNameTable;
    
    @FXML
    private TableColumn<HomePageInfo, LocalDate> classStartDateTable;
    
    @FXML
    private TableColumn<HomePageInfo, LocalDate> classEndDateTable;
    
    @FXML
    private TableColumn<HomePageInfo, Color> classColorTable;
    
    @FXML 
    private TableColumn<HomePageInfo, Void> classDelete;
    
    @FXML
    private final ObservableList<HomePageInfo> homePageList = FXCollections.observableArrayList();
    
    @FXML
    private TableView<HomePageInfo> homePageTable;
    
    // End of code for Homepage
    
    // CALENDAR
    
    @FXML private Label dayLabel_1, dayLabel_2, dayLabel_3, dayLabel_4, dayLabel_5, dayLabel_6, dayLabel_7;
    @FXML private Label dayLabel_8, dayLabel_9, dayLabel_10, dayLabel_11, dayLabel_12, dayLabel_13, dayLabel_14;
    @FXML private Label dayLabel_15, dayLabel_16, dayLabel_17, dayLabel_18, dayLabel_19, dayLabel_20, dayLabel_21;
    @FXML private Label dayLabel_22, dayLabel_23, dayLabel_24, dayLabel_25, dayLabel_26, dayLabel_27, dayLabel_28;
    @FXML private Label dayLabel_29, dayLabel_30, dayLabel_31, dayLabel_32, dayLabel_33, dayLabel_34, dayLabel_35;
    @FXML private Label dayLabel_36, dayLabel_37, dayLabel_38, dayLabel_39, dayLabel_40, dayLabel_41, dayLabel_42;
    
    @FXML private Label eventLabel_1, eventLabel_2, eventLabel_3, eventLabel_4, eventLabel_5, eventLabel_6, eventLabel_7;
    @FXML private Label eventLabel_8, eventLabel_9, eventLabel_10, eventLabel_11, eventLabel_12, eventLabel_13, eventLabel_14;
    @FXML private Label eventLabel_15, eventLabel_16, eventLabel_17, eventLabel_18, eventLabel_19, eventLabel_20, eventLabel_21;
    @FXML private Label eventLabel_22, eventLabel_23, eventLabel_24, eventLabel_25, eventLabel_26, eventLabel_27, eventLabel_28;
    @FXML private Label eventLabel_29, eventLabel_30, eventLabel_31, eventLabel_32, eventLabel_33, eventLabel_34, eventLabel_35;
    @FXML private Label eventLabel_36, eventLabel_37, eventLabel_38, eventLabel_39, eventLabel_40, eventLabel_41, eventLabel_42;
   
    @FXML private Button btnJanuary, btnFebruary, btnMarch, btnApril, btnMay, btnJune;
    @FXML private Button btnJuly, btnAugust, btnSeptember, btnOctober, btnNovember, btnDecember;
    
    private final Map<LocalDate, List<String>> events = new HashMap<>(); 

   // start of code for the study timer
    
    @FXML
    private TextField timer_input;
    
    @FXML
    private TextField timer_output;
    

    private myTimer studyTimer;
    
    // initializes pop up guis for classes and assignments
    
    private ClassGui myClassGui;
    
    private AssignmentGui myAssignmentGui;

    @FXML
    private Button addClassButton;
    
    // start of to do
    @FXML
    private TableColumn<ToDoInfo, String> toDoClass;
    @FXML
    private TableColumn<ToDoInfo, String> toDoAssignmentName;
    @FXML
    private TableColumn<ToDoInfo, LocalDate> toDoDueDate;
    @FXML
    private TableColumn<ToDoInfo, Boolean> toDoCompleted;
    @FXML
    private TableColumn<ToDoInfo, Void> toDoDelete;

    @FXML
    private final ObservableList<ToDoInfo> toDoList = FXCollections.observableArrayList();
    
    @FXML
    private TableView<ToDoInfo> toDoTable;
    
    @FXML
    private Button addEventOrAssignmentBtn;
   
    private EventOrAssignmentGui eventOrAssignmentGui;
    
    @FXML
    private ComboBox<ThemeInfo> themeComboBox;

    private YearMonth currentMonth;
  
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // homepage
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        String formattedDate = today.format(formatter);
        
        todaysDate.setText("Todays date is " + formattedDate);
       
        classNameTable.setCellValueFactory(cellData -> cellData.getValue().getName());
        classStartDateTable.setCellValueFactory(cellData -> cellData.getValue().getStartDate());
        classEndDateTable.setCellValueFactory(cellData -> cellData.getValue().getEndDate());
        
        // Color is special
        classColorTable.setCellValueFactory(cellData -> cellData.getValue().getColor());
        classColorTable.setCellFactory(cellData -> new ColorCell());
        
        
        homePageList.addAll(HomePageDB.getAllHomePage());
        homePageTable.setItems(homePageList);
        
        myClassGui = new ClassGui(this);
        
        addClassButton.setOnAction(event -> onAddClassButtonClick(event));
        studyTimer = new myTimer(() -> updateTimer());
        
        // calendar
        currentMonth = YearMonth.now();
        updateCalendar();
        highlightSelectedMonth();
        
        // -------------
        
        // To Do
        
        toDoList.addAll(ToDoDB.getAllToDo());
        toDoTable.setItems(toDoList);
        
        
        // Binding the variables to the right columns
        toDoClass.setCellValueFactory(cellData -> cellData.getValue().getClassName());
        toDoAssignmentName.setCellValueFactory(cellData -> cellData.getValue().getAssignmentName());
        toDoDueDate.setCellValueFactory(cellData -> cellData.getValue().getDueDate());
        
        myAssignmentGui = new AssignmentGui(this);
         
        // have to set up special column for the completed and delete column because 
        // scenebuilder doesn't support it
        toDoCompleted.setCellFactory(column -> {
            return new TableCell<ToDoInfo, Boolean>() {
                private final CheckBox checkBox = new CheckBox(); // making a toggleable checkbox
            
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);

                    // if the checkBox is empty, removing it by setting the graphic to null
                    if (empty) {
                        setGraphic(null);
                        
                    // else binding the checkbox and if status is completed, checking it
                    
                    } else {
                        ToDoInfo toDo = getTableRow().getItem();
                        
                        if (toDo != null) { // Check if 'toDo' is not null
                            
                        // Bind the checkbox to the "completed" status of ToDoInfo
                        checkBox.setSelected("completed".equals(toDo.getStatus().get())); // need the get() here because we are using wrapper classes and it's incompatible otherwise (spent an hour on figuring out why it wasn't working)
                        
                        // Handle checkbox toggle
                        checkBox.setOnAction(e -> {
                            
                            // Update the status when checkbox is clicked
                            String newStatus = checkBox.isSelected() ? "completed" : "pending";
                            toDo.setStatus(newStatus);
                            boolean success = ToDoDB.updateStatus(toDo.getId().get(), newStatus);
                            System.out.println("Updating status for " + toDo.getAssignmentName() + " to " + newStatus + ": " + success);
                        });
                    }

                        setGraphic(checkBox); // setting the checkbox in the cell
                    }
                }
            };
        });
        
        toDoDelete.setCellFactory(column -> {
            return new TableCell<ToDoInfo, Void>() {
                private final Button deleteButton = new Button("Delete");
                
                {
                    deleteButton.setOnAction(event -> {
                        ToDoInfo toDo = getTableRow().getItem();
                        
                        // if the object isn't null, remove from db, if successful remove from the ui
                        if (toDo != null) {
                            if ("Pending".equals(toDo.getStatus().get())){
                                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmationAlert.setTitle("Delete Assignment");
                                confirmationAlert.setHeaderText("You are about to delete an incomplete assignment.");
                                confirmationAlert.setContentText("Are you sure you want to delete this assignment without marking it as complete?");
                                
                                ButtonType yesButton = new ButtonType("Yes");
                                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                                confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
                                
                                Optional<ButtonType> result = confirmationAlert.showAndWait();
                                
                                if (result.isPresent() && result.get() == yesButton) {
                                    boolean success = ToDoDB.deleteAssignment(toDo);
                                    if (success) {
                                        toDoList.remove(toDo); // Remove from the UI
                                    } else {
                                        System.out.println("Wasn't able to delete the assignment from the database");
                                    }
                                }
                            } else {
     
                                boolean success = ToDoDB.deleteAssignment(toDo);
                                if (success) {
                                    toDoList.remove(toDo);
                                } else {
                                    System.out.println("Wasn't able to delete the assignment from the database");
                                }
                            }
                        }
                        
                        updateCalendar();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                } 
            };
        });
        
        eventOrAssignmentGui = new EventOrAssignmentGui(this);
        
        FXMLController self = this;
        
        classDelete.setCellFactory(column -> {
            return new TableCell<HomePageInfo, Void>() {
                private final Button deleteButtonHomepage = new Button("Delete");
                
                {
                    deleteButtonHomepage.setOnAction(event -> {
                        HomePageInfo classHome = getTableRow().getItem();

                        if (classHome != null) {
                            
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Confirmation");
                            alert.setHeaderText("Are you sure you want to delete this class?");
                            alert.setContentText("This will delete all assignments related to this class");
                            
                            Optional<ButtonType> result = alert.showAndWait();
                            
                            if (result.isPresent() && result.get() == ButtonType.OK) {
                            
                                try {
                                    boolean success = HomePageDB.deleteClass(classHome);
                                    
                                    if (success) {
                                        homePageList.remove(classHome); 
                                        self.updateTableView();
                                        updateCalendar();

                                    } else {
                                        System.out.println("Wasn't able to delete the class from the database.");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace(); 
                                }
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButtonHomepage);
                    }
                } 
            };
        });
      
        themeComboBox.getItems().addAll(ThemeDB.getAllThemes());

        
        List<ThemeInfo> themes = ThemeDB.getAllThemes();
        themeComboBox.getItems().setAll(themes);
            
        ThemeInfo selectedTheme = ThemeDB.getSelectedTheme();
        if (selectedTheme != null) {
            themeComboBox.getSelectionModel().select(selectedTheme);
        }

        themeComboBox.setOnAction(e -> {
            ThemeInfo selected = themeComboBox.getSelectionModel().getSelectedItem();

            if (selected != null) {
                boolean success = ThemeDB.updateSelectedTheme(selected.getName());

                if (success) {
                    System.out.println("Theme applied " + selected);

                    changeThemeStyle(selected.getName(), primaryStage);
                } else {
                    System.out.println("Failed to apply theme");
                }
            }
        });

    }
       
    
    // Start of code for the homepage 
    
    
    
    
    
    
    
    // End of code for the homepage
    
    // handling code for the class and assignment guis
    
    public void onAddClassButtonClick(ActionEvent event) {
        myClassGui.handleAddClass();
    }
    
    // Start of code for the To Do List
    
    @FXML
    public void onAddAssignmentButtonClick(ActionEvent event) {
        myAssignmentGui.handleAddAssignment();
    }
    
    // end of code for the class and assignment guis
    
    
    
   // start of code for the study timer
    
    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            setTime();
        }
    }
    

    @FXML
    public void handleTimeButton(ActionEvent event) {
        // getting the button that was clicked by the user
        Button sourceButton = (Button) event.getSource();
        String time = sourceButton.getText();
        
        // converting the string to an int 
        int minutes = Integer.parseInt(time);
        Duration duration = Duration.ofMinutes(minutes);
        
        // calling the formatting function
        String formattedTime = formatDuration(duration);
        
        studyTimer.stopTimer();
        
        // displays the time in the timer_ouput
        timer_output.setText(formattedTime);
        
        studyTimer.startTimer((int) duration.getSeconds());
    }
    

    @FXML

    public void handleStartButton(ActionEvent event) {
        int remainingTime = studyTimer.getRemainingTime();
        studyTimer.startTimer(remainingTime);
    }
    

    @FXML

    public void handleStopButton(ActionEvent event) {
        studyTimer.stopTimer();
        setRemainingTime();
    }
    

    @FXML

    public void handleResetButton(ActionEvent event) {
        // stop the timer
        studyTimer.stopTimer();
        
        // Reset the text input and output
        timer_output.setText("00:00");
        timer_input.clear();
    }
    
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() - hours * 60;
        var seconds = duration.getSeconds() - minutes * 60 - hours * 3600;
        
        if (hours > 0 ) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0 && hours == 0) {
            return String.format("%d:%02d", minutes, seconds);
        } else {
            return String.format("%d", seconds);
        }
        
    }
    
    // this is for when users input their own time
    private void setTime() {
        String desiredTime = timer_input.getText();
        
        // checking the entered minutes and seconds since we want 00:00 format
        
        if (desiredTime.contains(":")) {
            String[] timeParts = desiredTime.split(":");
            
            // this checks the HH:MM:SS format
            if (timeParts.length == 3) {
                try {
                    
                    // getting the parts of the inputted time and assigning them to their respective variables
                    int seconds = Integer.parseInt(timeParts[2]);
                    int minutes = Integer.parseInt(timeParts[1]);
                    int hours = Integer.parseInt(timeParts[0]);
                    
                    int totalSeconds = hours * 3600 + minutes * 60 + seconds;
                    studyTimer.startTimer(totalSeconds);
                } catch (NumberFormatException e ) {
                    timer_output.setText("Invalid time format, use HH:MM:SS");
                } 
                
            // This checks the MM:SS format    
            } else if (timeParts.length == 2) {
                
                try {
                    int seconds = Integer.parseInt(timeParts[1]);
                    int minutes = Integer.parseInt(timeParts[0]);
                    int totalSeconds = minutes * 60 + seconds;
                    studyTimer.startTimer(totalSeconds);
                } catch (NumberFormatException e ) {
                    timer_output.setText("Invalid time format, use MM:SS");
                } 
            } 
        
        // This just checks for seconds 
        } else {
            
            try {
                int totalSeconds = Integer.parseInt(desiredTime);
                studyTimer.startTimer(totalSeconds);
                
            } catch (NumberFormatException e ) {
                timer_output.setText("Invalid input. Enter HH:MM:SS, MM:SS, or the amount in seconds ");
            }
        }
        
        // clear the user input and unfocus it 
        timer_input.clear();
        timer_output.requestFocus();
                
    }
    
    public void updateTimer() {
        Platform.runLater(() -> {
            int remainingTime = studyTimer.getRemainingTime();
            Duration duration = Duration.ofSeconds(remainingTime);
            timer_output.setText(formatDuration(duration));
        });
    }
    
    private void setRemainingTime() {
        int remainingTime = studyTimer.getRemainingTime();
        Duration duration = Duration.ofSeconds(remainingTime);
        timer_output.setText(formatDuration(duration));
    }
    
    // end of code for study timer
    
    
    // ------------------------------------------------------
    
    // start of coded for calendar
    
    @FXML
    private void changeMonth(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String monthText = clickedButton.getText();
        
        switch (monthText) {
            case "JAN" -> currentMonth = YearMonth.of(2025, 1);
            case "FEB" -> currentMonth = YearMonth.of(2025, 2);
            case "MAR" -> currentMonth = YearMonth.of(2025, 3);
            case "APR" -> currentMonth = YearMonth.of(2025, 4);
            case "MAY" -> currentMonth = YearMonth.of(2025, 5);
            case "JUN" -> currentMonth = YearMonth.of(2025, 6);
            case "JUL" -> currentMonth = YearMonth.of(2025, 7);
            case "AUG" -> currentMonth = YearMonth.of(2025, 8);
            case "SEP" -> currentMonth = YearMonth.of(2025, 9);
            case "OCT" -> currentMonth = YearMonth.of(2025, 10);
            case "NOV" -> currentMonth = YearMonth.of(2025, 11);
            case "DEC" -> currentMonth = YearMonth.of(2025, 12);
        }
        updateCalendar();
    }

    public void updateCalendar() {
        events.clear();
        populateCalendar();
        
        int daysInMonth = currentMonth.lengthOfMonth();
        DayOfWeek firstDayOfWeek = currentMonth.atDay(1).getDayOfWeek();
        int startDay = firstDayOfWeek.getValue() %7;
        
      
        Label[] dayLabels = {dayLabel_1, dayLabel_2, dayLabel_3, dayLabel_4, dayLabel_5, dayLabel_6, dayLabel_7,
                             dayLabel_8, dayLabel_9, dayLabel_10, dayLabel_11, dayLabel_12, dayLabel_13, dayLabel_14,
                             dayLabel_15, dayLabel_16, dayLabel_17, dayLabel_18, dayLabel_19, dayLabel_20, dayLabel_21,
                             dayLabel_22, dayLabel_23, dayLabel_24, dayLabel_25, dayLabel_26, dayLabel_27, dayLabel_28,
                             dayLabel_29, dayLabel_30, dayLabel_31, dayLabel_32, dayLabel_33, dayLabel_34, dayLabel_35,
                             dayLabel_36, dayLabel_37, dayLabel_38, dayLabel_39, dayLabel_40, dayLabel_41, dayLabel_42};

        Label[] eventLabels = {eventLabel_1, eventLabel_2, eventLabel_3, eventLabel_4, eventLabel_5, eventLabel_6, eventLabel_7,
                               eventLabel_8, eventLabel_9, eventLabel_10, eventLabel_11, eventLabel_12, eventLabel_13, eventLabel_14,
                               eventLabel_15, eventLabel_16, eventLabel_17, eventLabel_18, eventLabel_19, eventLabel_20, eventLabel_21,
                               eventLabel_22, eventLabel_23, eventLabel_24, eventLabel_25, eventLabel_26, eventLabel_27, eventLabel_28,
                               eventLabel_29, eventLabel_30, eventLabel_31, eventLabel_32, eventLabel_33, eventLabel_34, eventLabel_35,
                               eventLabel_36, eventLabel_37, eventLabel_38, eventLabel_39, eventLabel_40, eventLabel_41, eventLabel_42};

        for (int i = 0; i < dayLabels.length; i++) {
            dayLabels[i].setText("");
            eventLabels[i].setText("");
            eventLabels[i].setStyle("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            int index = startDay + i - 1;
            if (index < dayLabels.length) {
                dayLabels[index].setText(String.valueOf(i));
                LocalDate date = currentMonth.atDay(i);

                List<String> dayEvents = events.get(date);

                if (dayEvents != null && !dayEvents.isEmpty()) {
                    StringBuilder eventText = new StringBuilder();

                    int displayLimit = 2;
                    int countToShow = Math.min(displayLimit, dayEvents.size());

                    for (int j = 0; j < countToShow; j++) {
                        eventText.append("• ").append(dayEvents.get(j)).append("\n");
                    }

                    if (dayEvents.size() > displayLimit) {
                        eventText.append("+").append(dayEvents.size() - displayLimit).append(" more");
                    }

                    eventLabels[index].setText(eventText.toString().trim());
                    eventLabels[index].setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

                    // Tooltip with full list of events
                    Tooltip tooltip = new Tooltip(String.join("\n", dayEvents));
                    tooltip.setStyle("-fx-font-size: 20px;");
                    Tooltip.install(eventLabels[index], tooltip);
                }
            }
        }
        
        highlightSelectedMonth();
    }
    
    private void populateCalendar() {
        List<EventInfo> allEvents = EventDB.getAllEvent();
        List<AssignmentInfo> allAssignments = AssignmentDB.getAssignments(); 
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        // Handle Events (like you already have)
        for (EventInfo event : allEvents) {
            LocalDate eventDate = event.getStartTime().get().toLocalDate();

            if (event.getIsRecurring().get() == 0) {
                if (!eventDate.isBefore(startOfMonth) && !eventDate.isAfter(endOfMonth)) {
                    addEventToMap(eventDate, event.getName().get());
                }
            } else {
                try {
                    List<RecurringInfo> recurs = RecurringDB.getRecurringEvents(event.getId().get());

                    for (RecurringInfo recur : recurs) {
                        LocalDate recurStart = recur.getStartDate().get();

                        if (!recurStart.isBefore(startOfMonth) && !recurStart.isAfter(endOfMonth)) {
                            addEventToMap(recurStart, event.getName().get());
                        }
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // 🔥 Now handle Assignments
        for (AssignmentInfo assignment : allAssignments) {
            LocalDate dueDate = assignment.getDueDate();

            if (!dueDate.isBefore(startOfMonth) && !dueDate.isAfter(endOfMonth)) {
                addEventToMap(dueDate, "[Due] " + assignment.getName()); 
            }
        }
    }
    
     private void addEventToMap(LocalDate date, String eventName) {
        events.computeIfAbsent(date, k -> new ArrayList<>()).add(eventName);
    }
     
     private void highlightSelectedMonth() {
 
        btnJanuary.setStyle("");
        btnFebruary.setStyle("");
        btnMarch.setStyle("");
        btnApril.setStyle("");
        btnMay.setStyle("");
        btnJune.setStyle("");
        btnJuly.setStyle("");
        btnAugust.setStyle("");
        btnSeptember.setStyle("");
        btnOctober.setStyle("");
        btnNovember.setStyle("");
        btnDecember.setStyle("");

        // Add highlight to the selected button
        switch (currentMonth.getMonthValue()) {
            case 1 -> btnJanuary.setStyle("-fx-background-color: lightgreen;");
            case 2 -> btnFebruary.setStyle("-fx-background-color: lightgreen;");
            case 3 -> btnMarch.setStyle("-fx-background-color: lightgreen;");
            case 4 -> btnApril.setStyle("-fx-background-color: lightgreen;");
            case 5 -> btnMay.setStyle("-fx-background-color: lightgreen;");
            case 6 -> btnJune.setStyle("-fx-background-color: lightgreen;");
            case 7 -> btnJuly.setStyle("-fx-background-color: lightgreen;");
            case 8 -> btnAugust.setStyle("-fx-background-color: lightgreen;");
            case 9 -> btnSeptember.setStyle("-fx-background-color: lightgreen;");
            case 10 -> btnOctober.setStyle("-fx-background-color: lightgreen;");
            case 11 -> btnNovember.setStyle("-fx-background-color: lightgreen;");
            case 12 -> btnDecember.setStyle("-fx-background-color: lightgreen;");
        }
    }
       
    public void updateHomePageTable() {
        homePageList.clear();
        homePageList.addAll(HomePageDB.getAllHomePage());
        homePageTable.refresh();
    }
    
    public void updateTableView() {
        toDoList.clear();
        toDoList.addAll(ToDoDB.getAllToDo());
        toDoTable.refresh(); 
    }
    
    @FXML
    public void addEventOrAssignmentBtnClicked(ActionEvent event) {
        eventOrAssignmentGui.showAddItemDialog();
        
    }
    
    @FXML
    public void removeEventOrAssignmentBtnClicked(ActionEvent event) {
        eventOrAssignmentGui.showDeleteItemDialog();
    }

    public void changeThemeStyle(String themeName, Stage primaryStage) {
        if (primaryStage == null) {
        System.err.println("Primary stage is null!");
        return;
    }

    Scene scene = primaryStage.getScene();
    if (scene == null) {
        System.err.println("Scene is null!");
        return;
    }

    // Clear any existing stylesheets
    scene.getStylesheets().clear();

    // Construct the path to the CSS file for the selected theme
    String css = "/css/" + themeName + ".css";

    try {
        // Try loading the CSS file
        URL resource = getClass().getResource(css);
        if (resource != null) {
            scene.getStylesheets().add(resource.toExternalForm());
            System.out.println("Theme applied: " + themeName);
        } else {
            System.err.println("CSS file not found: " + css);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Debug: Print applied stylesheets to check if it's working
    System.out.println("Applied stylesheets: " + scene.getStylesheets());
    }

@FXML private TextArea sundayAssignmentArea, mondayAssignmentArea, tuesdayAssignmentArea,
                       wednesdayAssignmentArea, thursdayAssignmentArea, fridayAssignmentArea, saturdayAssignmentArea;

public void displaySampleData() {

    // Assignments
    sundayAssignmentArea.setText("English 2");
    mondayAssignmentArea.setText("Spanish Assignment 1");
    tuesdayAssignmentArea.setText("Art Assignment 1");
    wednesdayAssignmentArea.setText("Assignment 10");
    thursdayAssignmentArea.setText("Watercolor Painting");
    fridayAssignmentArea.setText("Assignment 1");
    saturdayAssignmentArea.setText(""); // none
}

}

    

   


