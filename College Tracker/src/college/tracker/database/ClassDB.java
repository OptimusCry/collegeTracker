
package college.tracker.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class ClassDB {
    
    // function for adding class, it's a boolean because we want to see if it was added or not
    public boolean addClass(ClassInfo classInfo) throws SQLException {
        
        // query
        String query = "INSERT INTO classes (name, start_date, end_date, color, status) VALUES (?, ?, ?, ?, ?)";
        
        // trying for a connection
         try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setString(1, classInfo.getName());
            ps.setString(2, classInfo.getStartDate().toString());
            ps.setString(3, classInfo.getEndDate().toString());
            ps.setString(4, classInfo.toHexString());
            ps.setString(5, classInfo.getStatus());
            
            int rowsInserted = ps.executeUpdate(); // this returns amount of rows inserted
            
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error adding class " + e.getMessage());
            return false;
        }
            
    } // end of adding classes
    
    public boolean deleteClass(ClassInfo classInfo) throws SQLException {
        String query = "DELETE FROM classes WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, classInfo.getId());
            
            int rowsDeleted = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static List<ClassInfo> getClasses() {
        
        // Making array for the classes, this function gets the classes and their info
        List<ClassInfo> classes = new ArrayList<>();
        
        // Making a query to retrieve classinfo from db
        String query = "SELECT id, name, start_date, end_date, color, status FROM classes";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                         
             // if connection fails, print out that it failedd
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            // while the result set has more, retrieve the values
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                // Since SQLlite uses text instead of date objects we need to convert things
                // Setting the text from the db to variables
                String startDateString = rs.getString("start_date");
                String endDateString = rs.getString("end_date");
                
                // actually converting things here and validating
                // if not null, parse the text (don't want to get errors for trying to parse something null)
                LocalDate startDate = (startDateString != null) ? LocalDate.parse(startDateString): null;
                LocalDate endDate = (endDateString != null) ? LocalDate.parse(endDateString) : null;
                
               // Working with the hex string and making it into a color object to display throughtout the gui
                String colorHex = rs.getString("color"); // Getting the hex
                Color color = Color.web(colorHex); // converting the hex to a color object
                
                String status = rs.getString("status");
                
                // Creating new ClassInfo object and adding it to classes
                ClassInfo classInfo = new ClassInfo(id, name, startDate, endDate, color, status); // make new classInfo object
                classes.add(classInfo); // add it to classes         
            }
            
           
        } catch (SQLException e) {
             System.err.println("Error retrieving classes: " + e.getMessage());
            return new ArrayList<>();
        }
                
        return classes;
    }
    
    public void classComboBox(ComboBox<ClassInfo> comboBox) {
        
        List<ClassInfo> classCombo = new ArrayList<>();
        
        // Making a query to retrieve classinfo from db
        String query = "SELECT id, name FROM classes";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                         
             // if connection fails, print out that it failedd
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            // while the result set has more, retrieve the values
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                ClassInfo classInfo = new ClassInfo(id, name, null, null, null, null);
                classCombo.add(classInfo);
            }
            
        } catch (SQLException e) {
           
        }
        
    }
    
}
