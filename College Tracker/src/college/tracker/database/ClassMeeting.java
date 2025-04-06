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
    private int id;
    private String className;
    private String classDay;
    private String startTime;
    private String endTime;
    
    public ClassMeeting(int id, String className, String classDay, String startTime, String endTime) {
       
        this.id = id;
        this.className = className;
        this.classDay = classDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public int getId () {return id;}
    public String getClassName() {return className;}
    public String getClassDay() {return classDay;}
    public String getStartTime() {return startTime;}
    public String getEndTime() {return endTime;}
    
    public void setClassName(String className) {this.className = className;}
    public void setClassDay(String classDay) {this.classDay = classDay;}
    public void setStartTime(String startTime) {this.startTime = startTime;}
    public void setEndTime(String endTime) {this.endTime = endTime;}
}
