/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

import college.tracker.info.ToDoInfo;
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
public class ToDoDB {
    
    public static List<ToDoInfo> getAllToDo() {
        List<ToDoInfo> toDoList = new ArrayList<>();
        
        String query = "SELECT id, name AS assignment_name, due_date, status, class_id FROM assignments";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                                     
            // while the result set has more, retrieve the values
            while (rs.next()) {
                int id = rs.getInt("id");
                String assignmentName = rs.getString("assignment_name");
                
                String dueDateString = rs.getString("due_date");
                LocalDate dueDate = (dueDateString != null) ? LocalDate.parse(dueDateString) : null;
                
                String status = rs.getString("status");
                int classId = rs.getInt("class_id");
                
                String className = getClassName(classId);
                
                ToDoInfo toDoInfo = new ToDoInfo(id, className, assignmentName, dueDate, status);
                toDoList.add(toDoInfo);
                
            }
            
           
        } catch (SQLException e) {
            System.err.println("Error retrieving to do list items: " + e.getMessage());
            
        }
                
        return toDoList;
    
    }
    
    public static String getClassName(int classId) {
        String query = "SELECT name FROM classes WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("name");
          
            }
   
        } catch (SQLException e) {
            
        }
                
        return null;
    }
    
    public static boolean updateStatus(int id, String newStatus) {
        String query = "UPDATE assignments SET status = ? WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, newStatus); 
            ps.setInt(2, id);    

            
            int rowsUpdated = ps.executeUpdate(); // this returns amount of rows inserted
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean deleteAssignment(ToDoInfo toDo) {
        String query = "DELETE FROM assignments WHERE id = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, toDo.getId().get());
            
            int rowsDeleted = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public boolean addAssignment(ToDoInfo toDoInfo) {
        String query = "INSERT INTO assignments (name, due_date, status, class_id) values (?, ?, ?, ?)";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, toDoInfo.getAssignmentName().get());
            ps.setString(2, toDoInfo.getDueDate().get().toString());
            ps.setString(3, toDoInfo.getStatus().get());
            ps.setInt(4, toDoInfo.getClassId());
            
            int rowsDeleted = ps.executeUpdate(); // this returns amount of rows inserted

            return rowsDeleted > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

}
