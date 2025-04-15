
package college.tracker;

import college.tracker.database.AssignmentDB;
import college.tracker.info.AssignmentInfo;
import college.tracker.database.ClassDB;
import college.tracker.info.ClassInfo;
import college.tracker.database.ThemeDB;
import static college.tracker.database.connect.connect;
import college.tracker.info.ThemeInfo;
import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        Parent root = loader.load();
        
        FXMLController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
                
        Scene scene = new Scene(root, 1000, 800);
        
        primaryStage.setTitle("College Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        ThemeInfo selectedTheme = ThemeDB.getSelectedTheme();
        
        if (selectedTheme != null) {
            controller.changeThemeStyle(selectedTheme.getName(), primaryStage); 
        } else {
            // Default theme is applied if none is selected in the DB
            scene.getStylesheets().add(getClass().getResource("/css/Default.css").toExternalForm());
        }    
    }
       
    
    public static void main(String[] args) {
        launch(args);
    }
   
}
