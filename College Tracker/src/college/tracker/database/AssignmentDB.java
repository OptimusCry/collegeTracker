
package college.tracker.database;

import college.tracker.info.AssignmentInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kayla
 */
public class AssignmentDB {
    
    
    public boolean addAssignment(AssignmentInfo assignment) {
        String query = "INSERT INTO assignments (name, due_date, status, class_id) values (?, ?, ?, ?)";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            ps.setString(1, assignment.getName());
            ps.setString(2, assignment.getDueDate().toString());
            ps.setString(3, assignment.getStatus());
            ps.setInt(4, assignment.getClassId());
            
            int rowsInserted = ps.executeUpdate(); // this returns amount of rows inserted
            
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteAssignment(AssignmentInfo assignment) {
        String query = "DELETE FROM assignments WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, assignment.getId());
            
            int rowsDeleted = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static  List<AssignmentInfo> getAssignments() {
        
        // Making array for the assignments, this function gets the classes and their info
        List<AssignmentInfo> assignments = new ArrayList<>();
        
        // Making a query to retrieve assignments from db
        String query = "SELECT id, name, due_date, status, class_id FROM assignments";
        
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
                
                String dueDateString = rs.getString("due_date");
                LocalDate dueDate = (dueDateString != null) ? LocalDate.parse(dueDateString): null;
                
                String status = rs.getString("status");
                int classId = rs.getInt("class_id");
                
                // Creating new ClassInfo object and adding it to classes
                AssignmentInfo assignment = new AssignmentInfo(id, name, dueDate, status, classId); // make new classInfo object
                assignments.add(assignment); // add it to classes         
            }
            
           
        } catch (SQLException e) {
            System.err.println("Error retrieving assignments: " + e.getMessage());
            return new ArrayList<>();
        }
                
        return assignments;
    }
    
}
