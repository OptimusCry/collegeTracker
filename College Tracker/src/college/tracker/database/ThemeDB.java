/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

import college.tracker.info.ThemeInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kayla
 */
public class ThemeDB {
    
    public static ThemeInfo getSelectedTheme() {
        String query = "SELECT id, name, is_selected FROM themes WHERE is_selected = 1";
        ThemeInfo selectedTheme = null;
        
        try (Connection connection = connect.connect(); 
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
                         
             // if connection fails, print out that it failedd
            if (connection == null) {
                System.out.println("Failed to get a connection, try again");
            }
            
            // while the result set has more, retrieve the values
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int isSelected = rs.getInt("is_selected");
                
                selectedTheme = new ThemeInfo(id, name, isSelected);
                
            }
        } catch (SQLException e) {
            System.err.println("Error getting selected theme: " + e.getMessage());
        }
        
        return selectedTheme;
            
    }
    
    public static boolean updateSelectedTheme(String themeName) {
        String reset = "UPDATE themes SET is_selected = 0 WHERE is_selected = 1";
        
        String update = "UPDATE themes SET is_selected = 1 WHERE name = ?";
        
        try (Connection connection = connect.connect(); 
             PreparedStatement resetPs = connection.prepareStatement(reset);
             PreparedStatement updatePs = connection.prepareStatement(update)) {

            // if connection fails, print out that it failed
            if (connection == null) {
                return false;
            }
            
            resetPs.executeUpdate();
            updatePs.setString(1, themeName);
            
            int rowsUpdated = updatePs.executeUpdate();
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating selected theme: " + e.getMessage());
            return false;
        }
    }
    
    public static List<ThemeInfo> getAllThemes() {
        List<ThemeInfo> themes = new ArrayList<>();
        String query = "SELECT id, name, is_selected FROM themes";
        
        try (Connection connection = connect.connect(); 
         PreparedStatement ps = connection.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int isSelected = rs.getInt("is_selected");
                
                themes.add(new ThemeInfo(id, name, isSelected));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return themes;
    }
    
}