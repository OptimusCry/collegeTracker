/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

import college.tracker.info.HomePageInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class HomePageDB {
    
    public static List<HomePageInfo> getAllHomePage() {
        List<HomePageInfo> homePageList = new ArrayList<>();
         
        String query = "SELECT id, name, start_date, end_date, color, status FROM classes WHERE status = ?";
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setString(1, "Active");

            ResultSet rs = ps.executeQuery();
      
            // while the result set has more, retrieve the values
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                String startDateString = rs.getString("start_date");
                LocalDate startDate = (startDateString != null) ? LocalDate.parse(startDateString) : null;
                
                String endDateString = rs.getString("end_date");
                LocalDate endDate = (endDateString != null) ? LocalDate.parse(endDateString) : null;
                
                String colorString = rs.getString("color");
                Color color = (colorString != null) ? Color.web(colorString) : Color.WHITE;
                
                String status = rs.getString("status");
                
                HomePageInfo homePageInfo = new HomePageInfo(id, name, startDate, endDate, color, status);
                homePageList.add(homePageInfo);
           
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving to home page items: " + e.getMessage());
            
        }
                
        return homePageList;
    
    }
   
 
}
