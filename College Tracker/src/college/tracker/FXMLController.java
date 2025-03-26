package college.tracker;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private TextField timer_input;
    
    @FXML
    private TextField timer_output;
    
    private myTimer studyTimer;
    
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
    private YearMonth currentMonth;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studyTimer = new myTimer(() -> updateTimer());
        currentMonth = YearMonth.of(2025, 1);
        updateCalendar();
    }
    
   // start of code for the study timer
    
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            setTime();
        }
    }
    
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
    
    public void handleStartButton(ActionEvent event) {
        int remainingTime = studyTimer.getRemainingTime();
        studyTimer.startTimer(remainingTime);
    }
    
    public void handleStopButton(ActionEvent event) {
        studyTimer.stopTimer();
        setRemainingTime();
    }
    
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
    
    @FXML
    private void changeMonth(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String monthText = clickedButton.getText();
        
        switch (monthText) {
            case "JAN": 
                currentMonth = YearMonth.of(2025, 1); 
                break;
            case "FEB": 
                currentMonth = YearMonth.of(2025, 2); 
                break;
            case "MAR": 
                currentMonth = YearMonth.of(2025, 3); 
                break;
            case "APR": 
                currentMonth = YearMonth.of(2025, 4); 
                break;
            case "MAY": 
                currentMonth = YearMonth.of(2025, 5); 
                break;
            case "JUN": 
                currentMonth = YearMonth.of(2025, 6); 
                break;
            case "JUL": 
                currentMonth = YearMonth.of(2025, 7); 
                break;
            case "AUG": 
                currentMonth = YearMonth.of(2025, 8); 
                break;
            case "SEP": 
                currentMonth = YearMonth.of(2025, 9); 
                break;
            case "OCT": 
                currentMonth = YearMonth.of(2025, 10); 
                break;
            case "NOV": 
                currentMonth = YearMonth.of(2025, 11); 
                break;
            case "DEC": 
                currentMonth = YearMonth.of(2025, 12); 
                break;
        }
        updateCalendar();
    }

    private void updateCalendar() {
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

                if (events.containsKey(date)) {          
                    String eventText = String.join("\n", events.get(date));
                    eventLabels[index].setText(eventText);
                    eventLabels[index].setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        }
    }
    
    @FXML
    public void openAddEvent() {
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEvent.fxml"));
             Parent root = loader.load();

             AddEventController controller = loader.getController();
             controller.setMainController(this);

             Stage stage = new Stage();
             stage.setTitle("Add Event");
             stage.setScene(new Scene(root));
             stage.show();
         } catch (IOException e) {
         }
     }

    public void addEvent(LocalDate date, String title) {
        events.putIfAbsent(date, new ArrayList<>());
        events.get(date).add(title);
        updateCalendar();
    }

    
   
}