/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.info;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 *
 * @author kayla
 */
public class HomePageInfo {
    
    private final IntegerProperty id;
    private final StringProperty name;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final ObjectProperty<Color> color;
    private final StringProperty status;
    
    
    // constructor for adding
    public HomePageInfo(String name, LocalDate startDate, LocalDate endDate, Color color, String status) {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.color = new SimpleObjectProperty<>(color);
        this.status = new SimpleStringProperty(status);
    }

    // constructor for retrieving/deleting
    public HomePageInfo(int id, String name, LocalDate startDate, LocalDate endDate, Color color, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.color = new SimpleObjectProperty<>(color);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public ObjectProperty<LocalDate> getStartDate() {
        return startDate;
    }

    public ObjectProperty<LocalDate> getEndDate() {
        return endDate;
    }

    public ObjectProperty<Color> getColor() {
        return color;
    }

    public StringProperty getStatus() {
        return status;
    }
   
    
}
