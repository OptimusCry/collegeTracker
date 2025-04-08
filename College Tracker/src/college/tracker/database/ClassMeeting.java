/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package college.tracker.database;

/**
 *
 * @author kingj
 */ 

public class ClassMeeting {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty className;
    private final SimpleStringProperty classDay;
    private final SimpleStringProperty startTime;
    private final SimpleStringProperty endTime;
    private final SimpleStringProperty type; //Distingushes between class, event, assignments 
    
    public ClassMeeting(int id, String className, String classDay, String startTime, String endTime, String type) {
       
        this.id = new SimpleIntegerProperty(id);
        this.className = new SimpleStringProperty(className);
        this.classDay = new SimpleStringProperty(classDay);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.type = new SimpleStringProperty(type);
    }
    
    public int getId() {return id.get();}
    public String getClassName() {return className.get();}
    public String getClassDay() {return classDay.get();}
    public String getStartTime() {return startTime.get();}
    public String getEndTime() {return endTime.get();}
    public String getType() {return type.get();}
    
    public void setClassName(String className) {this.className.set = (className);}
    public void setClassDay(String classDay) {this.classDay.set = (classDay);}
    public void setStartTime(String startTime) {this.startTime.set = (startTime);}
    public void setEndTime(String endTime) {this.endTime.set = (endTime);}
    public void setType(String type) {this.type.set(type);}
    
    public SimpleStringProperty classNameProperty(){return className;}
    public SimpleStringProperty classDayProperty(){return classDay;}
    public SimpleStringProperty startTimeProperty() {return startTime;}
    public SimpleStringProperty endTimeProperty(){return endTime;}
    public SimpleStringProperty typeProperty() {return type;}
}
