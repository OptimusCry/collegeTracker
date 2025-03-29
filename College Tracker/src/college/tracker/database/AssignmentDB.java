
package college.tracker.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author kayla
 */
public class AssignmentDB {
    
    
    public boolean addAssignment(AssignmentInfo assignment) throws SQLException {
        String query = "INSERT INTO assignments (name, due_date, status, class_id) values (?, ?, ?, ?)";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            
            ps.setString(1, assignment.getName());
            ps.setDate(2, java.sql.Date.valueOf(assignment.getDueDate()));
            ps.setString(3, assignment.getStatus());
            ps.setInt(4, assignment.getClassId());
            
            int rowsInserted = ps.executeUpdate(); // this returns amount of rows inserted
            
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
