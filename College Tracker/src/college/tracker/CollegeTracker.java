
package college.tracker;

import college.tracker.database.AssignmentDB;
import college.tracker.database.AssignmentInfo;
import college.tracker.database.ClassDB;
import college.tracker.database.ClassInfo;
import college.tracker.database.ThemeDB;
import static college.tracker.database.connect.connect;
import college.tracker.info.ThemeInfo;
import java.lang.ModuleLayer.Controller;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
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
        
        if (controller == null) {
            System.err.println("FXMLController is null! Make sure the FXML file is correctly linked to the controller.");
            return;
        }
        
        Scene scene = new Scene(root, 1000, 800);
        
        primaryStage.setTitle("College Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        ThemeInfo selectedTheme = ThemeDB.getSelectedTheme();
        
        if (selectedTheme != null) {
        System.out.println("Setting selected theme to: " + selectedTheme.getName());
        controller.changeThemeStyle(selectedTheme.getName(), primaryStage); // Apply the theme change through the controller
    } else {
        // Default theme is applied if none is selected in the DB
        scene.getStylesheets().add(getClass().getResource("/css/Default.css").toExternalForm());
        System.out.println("Setting selected theme to: Default");
    }
        
        List<ClassInfo> classes = ClassDB.getClasses();
        List<AssignmentInfo> assignments = AssignmentDB.getAssignments();
        
        // Check if we are getting any classes back
        if (classes.isEmpty()) {
            System.out.println("No classes found in the database.");
        } else {
            System.out.println("Classes fetched from the database:");
            for (ClassInfo classInfo : classes) {
                System.out.println("Class Name: " + classInfo.getName());
            }
        }
        
        if (assignments.isEmpty()) {
            System.out.println("No Assignments found in the database.");
        } else {
            System.out.println("Assignments fetched from the database:");
            for (AssignmentInfo Assignment : assignments) {
                System.out.println("Assignment Name: " + Assignment.getName());
            }
        }
    
    }
       
    
    public static void main(String[] args) {
        launch(args);
        
        
    }
    
    
    
}
