/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.info;

import java.time.LocalDate;
import javafx.beans.property.*;

/**
 *
 * @author kayla
 */
public class ToDoInfo {
    
    private final IntegerProperty id;
    private final StringProperty className;         // the class table just has it named as name
    private final StringProperty assignmentName;    // the assignment table just has it named as name
    private final ObjectProperty<LocalDate> dueDate; 
    private final StringProperty status;
    private final SimpleBooleanProperty delete;
    private SimpleIntegerProperty classId;

    public ToDoInfo(int id, String className, String assignmentName, LocalDate dueDate, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.className = new SimpleStringProperty(className);
        this.assignmentName = new SimpleStringProperty(assignmentName);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.status = new SimpleStringProperty(status);
        this.delete = new SimpleBooleanProperty(false);
    }

    public ToDoInfo(String assignmentName, LocalDate dueDate, String status, int classId) {
        this.id = new SimpleIntegerProperty(0);
        this.className = new SimpleStringProperty("");
        this.assignmentName = new SimpleStringProperty(assignmentName);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.status = new SimpleStringProperty(status);
        this.delete = new SimpleBooleanProperty(false);
        this.classId = new SimpleIntegerProperty(classId);
    }



    public IntegerProperty getId() {
        return id;
    }

    public StringProperty getClassName() {
        return className;
    }

    public StringProperty getAssignmentName() {
        return assignmentName;
    }

    public ObjectProperty<LocalDate> getDueDate() {
        return dueDate;
    }

    public StringProperty getStatus() {
        return status;
    }

    public BooleanProperty getDelete() {
        return delete;
    }
    
    public int getClassId() {
        return classId.get();
    }
    
    public void setStatus(String newStatus) {
        this.status.set(newStatus);
    }
    
    public void setClassId(int classId) {
        this.classId.set(classId);
    }
      
}
