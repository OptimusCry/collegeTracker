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

/**
 *
 * @author kayla
 */
public class RecurringInfo {
    
    private final IntegerProperty id;
    private final StringProperty frequency;
    private final StringProperty dayOfWeek;
    private final ObjectProperty<LocalDate> startDate;
    private final ObjectProperty<LocalDate> endDate;
    private final IntegerProperty eventId;
    
    public RecurringInfo(String frequency, String dayOfWeek, LocalDate startDate, LocalDate endDate, int eventId) {
        this.id = new SimpleIntegerProperty(0);
        this.frequency = new SimpleStringProperty(frequency);
        this.dayOfWeek = new SimpleStringProperty(dayOfWeek);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.eventId = new SimpleIntegerProperty(eventId);
    }

    public RecurringInfo(int id, String frequency, String dayOfWeek, LocalDate startDate, LocalDate endDate, int eventId) {
        this.id = new SimpleIntegerProperty(id);
        this.frequency = new SimpleStringProperty(frequency);
        this.dayOfWeek = new SimpleStringProperty(dayOfWeek);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.eventId = new SimpleIntegerProperty(eventId);
        
    }

    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getFrequency() {
        return frequency;
    }

    public StringProperty getDayOfWeek() {
        return dayOfWeek;
    }

    public ObjectProperty<LocalDate> getStartDate() {
        return startDate;
    }

    public ObjectProperty<LocalDate> getEndDate() {
        return endDate;
    }

    public IntegerProperty getEventId() {
        return eventId;
    }
    
    
    
    
}