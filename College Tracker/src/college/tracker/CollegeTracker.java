
package college.tracker;

import college.tracker.database.AssignmentDB;
import college.tracker.database.AssignmentInfo;
import college.tracker.database.ClassDB;
import college.tracker.database.ClassInfo;
import static college.tracker.database.connect.connect;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        Scene scene = new Scene(loader.load(), 1000, 800);
        scene.getStylesheets().add(getClass().getResource("collegetracker.css").toExternalForm());
        
        
        primaryStage.setTitle("College Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
        
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
