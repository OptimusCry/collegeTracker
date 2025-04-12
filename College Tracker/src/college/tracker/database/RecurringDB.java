/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

import college.tracker.info.RecurringInfo;
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
public class RecurringDB {
    
    public static List<RecurringInfo> getRecurringEvents(int eventId) throws SQLException {
        List<RecurringInfo> recurringList = new ArrayList<>();
        
        String query = "SELECT id, frequency, day_of_week, start_date, end_date, event_id FROM recurrence_days WHERE event_id = ?";
        
         try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String frequency = rs.getString("frequency");
                String dayOfWeek = rs.getString("day_of_week");
                
                String startDateString = rs.getString("start_date");
                LocalDate startDate = (startDateString != null) ? LocalDate.parse(startDateString) : null;
                 
                String endDateString = rs.getString("end_date");
                LocalDate endDate = (endDateString != null) ? LocalDate.parse(endDateString) : null;
                
                int eventIdFromDb = rs.getInt("event_id");
                
                RecurringInfo recurringInfo = new RecurringInfo(id, frequency, dayOfWeek, startDate, endDate, eventIdFromDb);
                recurringList.add(recurringInfo);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving recurrence data: " + e.getMessage());
        }
         
         return recurringList;
        
    }
    
    public static boolean deleteRecurring(int id) throws SQLException {
        String query = "DELETE FROM recurrence_days WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
           PreparedStatement ps = connection.prepareStatement(query)) {
            
           ps.setInt(1, id);
           int rowsDeleted = ps.executeUpdate();
           
           return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
          
    }
    
    public static boolean addRecurring(RecurringInfo recurringInfo) {
        String query = "INSERT INTO recurrence_days (frequency, day_of_week, start_date, end_date, event_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = connect.connect(); 
           PreparedStatement ps = connection.prepareStatement(query)) {
            
           ps.setString(1, recurringInfo.getFrequency().get());
           ps.setString(2, recurringInfo.getDayOfWeek().get());
           ps.setObject(3, recurringInfo.getStartDate().get());
           ps.setObject(4, recurringInfo.getEndDate().get());
           ps.setInt(5, recurringInfo.getEventId().get());
           
           int rowsAdded = ps.executeUpdate();
           
           return rowsAdded > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}