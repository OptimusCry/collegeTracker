/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.info;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kayla
 */
public class EventInfo {
    
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final ObjectProperty<LocalDateTime> startTime;
    private final ObjectProperty<LocalDateTime> endTime;
    private final StringProperty location;
    private final IntegerProperty isRecurring;
    private final StringProperty status;
    
    // constructor for adding
    public EventInfo(String name, String description, LocalDateTime startTime, LocalDateTime endTime, String location, int isReccurring, String status) {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.location = new SimpleStringProperty(location);
        this.isRecurring = new SimpleIntegerProperty(0);
        this.status = new SimpleStringProperty(status);
    }

    // constructor for retrieving/deleting
    public EventInfo(int id, String name, String description, LocalDateTime startTime, LocalDateTime endTime, String location, int isReccurring, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.location = new SimpleStringProperty(location);
        this.isRecurring = new SimpleIntegerProperty(0);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getDescription() {
        return description;
    }

    public ObjectProperty<LocalDateTime> getStartTime() {
        return startTime;
    }

    public ObjectProperty<LocalDateTime> getEndTime() {
        return endTime;
    }

    public StringProperty getLocation() {
        return location;
    }

    public IntegerProperty getIsRecurring() {
        return isRecurring;
    }

    public StringProperty getStatus() {
        return status;
    }
    
    
}
