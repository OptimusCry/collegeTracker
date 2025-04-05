/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.info;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class ColorCell extends TableCell<HomePageInfo, Color> {
    
    @Override
    protected void updateItem(Color color, boolean empty) {
        super.updateItem(color, empty);
        
        // if there is no color item, setting the style and text to nothing
        if (empty || color == null) {
            setStyle("");
            setText("");
        } else {
            String colorHex = colorToHex(color);
            setStyle("-fx-background-color: #" + colorToHex(color) + ";");
            setText("");
        }
    }
    
    // converting the color to hex string to display in the cell style
    private String colorToHex(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("%02x%02x%02x", r, g, b);
    }
}
