package college.tracker.database;

import college.tracker.info.EventInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kayla
 */
public class EventDB {
    
    public static List<EventInfo> getAllEvent() {
        List<EventInfo> eventList = new ArrayList<>();
        
        String query = "SELECT id, name, description, start_time, end_time, location, is_recurring, status FROM events";
        
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
                String description = rs.getString("description");
                
                // start and end time
                String startTimeString = rs.getString("start_time");
                LocalDateTime startTime = (startTimeString != null) ? LocalDateTime.parse(startTimeString) : null;
                
                String endTimeString = rs.getString("end_time");
                LocalDateTime endTime = (endTimeString != null) ? LocalDateTime.parse(endTimeString) : null;
                
                String location = rs.getString("location");
                int isRecurring = rs.getInt("is_recurring");
                String status = rs.getString("status");
               
                EventInfo eventInfo = new EventInfo(id, name, description, startTime, endTime, location, isRecurring, status);
                eventList.add(eventInfo);
                
            }
            
           
        } catch (SQLException e) {
            System.err.println("Error retrieving to event items: " + e.getMessage());
            
        }
                
        return eventList;
        
    }
    
    public static boolean deleteEvent(EventInfo eventInfo) {
        String query = "DELETE FROM events where id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, eventInfo.getId().get());
            
            int rowsDeleted = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean addEvent(EventInfo eventInfo) {
        
        String query = "INSERT INTO events (name, description, start_time, end_time, location, is_recurring, status) values (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, eventInfo.getName().get());
            
            ps.setString(2, eventInfo.getDescription().get() != null ? eventInfo.getDescription().get() : null);
           
            ps.setString(3, eventInfo.getStartTime().get().toString());
            
            ps.setString(4, eventInfo.getEndTime().get() != null ? eventInfo.getEndTime().get().toString() : null);
            
            ps.setString(5, eventInfo.getLocation().get() != null ? eventInfo.getLocation().get() : null);
            
            ps.setInt(6, eventInfo.getIsRecurring().get());
            ps.setString(7, "scheduled");
            
            int rowsAdded = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsAdded > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static int getNewestEventId() {
        String query = "SELECT id from events ORDER BY id DESC LIMIT 1";
        
         try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                return rs.getInt("id");
            }
             
        } catch (SQLException e) {
            System.err.println("Error retrieving eventId: " + e.getMessage());
        }
         
        return -1;
    }
    
}