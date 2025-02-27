
package college.tracker;

import static college.tracker.database.connect.connect;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author kayla
 */
public class CollegeTracker extends Application {

    /**
     * @param stage
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        connect();
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
