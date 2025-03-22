
package college.tracker;

import static college.tracker.database.connect.connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author kayla
 */
public class CollegeTracker extends Application {

    /**
     * @param primaryStage
     * @throws java.lang.Exception
     */
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        connect();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/college/tracker/FXML.fxml"));
        scene.getStylesheets().add(getClass().getResource("collegetracker.css").toExternalForm());
        Scene scene = new Scene(loader.load(), 1000, 800);
        
        primaryStage.setTitle("College Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
           
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
    
    
    
}
