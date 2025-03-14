
package college.tracker;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author kayla
 */
public class FXMLController implements Initializable {

    @FXML
    private TextField timer_input;
    
    @FXML
    private TextField timer_output;
    

    
    private myTimer studyTimer;
    
    private ClassGui myClassGui;

    @FXML
    private Button addClassButton;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myClassGui = new ClassGui();
        addClassButton.setOnAction(event -> onAddClassButtonClick(event));
        studyTimer = new myTimer(() -> updateTimer());
        
       
    }
    
    public void onAddClassButtonClick(ActionEvent event) {
        myClassGui.handleAddClass();
    }
    
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
   
}
