/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

/**
 *
 * @author kingj
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassTimetable {
    
    public static void saveMeetingTime(ClassMeeting meeting) {
        String sql = "INSERT INTO ClassMeetingTimes (class_name, start_time, end_time) VALUES (?,?,?,?)";
        
        try (Connection conn = connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, meeting.getClassName());
            pstmt.setString(2, meeting.getClassDay());
            pstmt.setString(3, meeting.getStartTime());
            pstmt.setString(4, meeting. getEndTime());
            
            pstmt.executeUpdate();
            System.out.println("Class meeting time saved successfully.");
        }  catch (SQLException e) {
            System.out.println("Error saving class meeting time: " + e.getMessage());
        }
    }
}


