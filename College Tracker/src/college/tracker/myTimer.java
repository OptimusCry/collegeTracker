
package college.tracker;


import java.util.concurrent.*;
import javafx.application.Platform;


/**
 *
 * @author kayla
 */
public class myTimer {
    private final ScheduledExecutorService executorService;
    private ScheduledFuture<?> scheduledTask;
    private int timeRemaining;
    private boolean isRunning = false;
    
    private final Runnable updateTimer;
   
    public myTimer(Runnable updateTimer) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        this.updateTimer = updateTimer;
    }
    
    public void startTimer(int timeSeconds) {
        if (isRunning) return;
        
        isRunning = true;
        timeRemaining = timeSeconds;
        
        scheduledTask = executorService.scheduleAtFixedRate(() -> {
            if (timeRemaining <= 0) {
                stopTimer();
            } else {
                timeRemaining--;
                
                if (updateTimer != null) {
                    Platform.runLater(updateTimer);
                }
            }
                    
        }, 0, 1, TimeUnit.SECONDS);
    }
          
    public void stopTimer() {
        
        // if task is running cancel it
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
        }
        
        // set timer running to false
        isRunning = false;
    }
    
    public void resetTimer() {
        
        if (timeRemaining > 0 ) {
            stopTimer();
            timeRemaining = 0;
        } 
        
    }
    
    public int getRemainingTime() {
        return timeRemaining;
    }
    
   // public String formatTime() {
       
    // }
    
    public void shutdown() {
        executorService.shutdown();
    }
}
 