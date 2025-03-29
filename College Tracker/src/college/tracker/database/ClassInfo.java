
package college.tracker.database;

import java.time.LocalDate;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class ClassInfo {
    private int id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Color color;
    private String status;

    public ClassInfo(String name, LocalDate startDate, LocalDate endDate, Color color, String status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.status = status;
    }

    public ClassInfo(int id, String name, LocalDate startDate, LocalDate endDate, Color color, String status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String toHexString() {
        return String.format("#%02x%02x%02x", // Looked up this pattern for hex code
                            (int)(color.getRed() * 255),
                            (int)(color.getGreen() * 255),
                            (int)(color.getBlue() * 255));
    }
    
    
    // Allows me to write the names to the combobox in the to do list without messing with the cellfactory
    @Override
    public String toString() {
        return name;
    }
    
}
