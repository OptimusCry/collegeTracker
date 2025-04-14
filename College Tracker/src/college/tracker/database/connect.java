
package college.tracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kayla
 */
public class connect {
    
    public static Connection connect() {
        
        String url = "jdbc:sqlite:src/college/tracker/database/collegeTracker.db";
        
        try {
            Connection connection = DriverManager.getConnection(url);
            
            // Enable foreign key support in SQLite
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }
            
            return connection;
            
            
          
        } catch (SQLException e) {
            System.err.println("Connection failed " + e.getMessage());
            return null;
        }
        
    }
    
}
