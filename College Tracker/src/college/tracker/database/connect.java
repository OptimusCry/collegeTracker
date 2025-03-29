
package college.tracker.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kayla
 */
public class connect {
    
    public static Connection connect() {
        
        String url = "jdbc:sqlite:src/college/tracker/database/collegeTracker.db";
        
        try {
            return DriverManager.getConnection(url);
          
        } catch (SQLException e) {
            System.err.println("Connection failed " + e.getMessage());
            return null;
        }
        
    }
    
}
