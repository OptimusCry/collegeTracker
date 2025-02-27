
package college.tracker.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kayla
 */
public class connect {
    
    public static void connect() {
        
        String url = "jdbc:sqlite:C:/college_tracker.db";
        
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connection Successful");
            
            

            
        } catch (SQLException e) {
            System.err.println("Connection failed " + e.getMessage());
        }
        
    }
    
}
