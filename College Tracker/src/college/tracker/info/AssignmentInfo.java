
package college.tracker.info;

import java.time.LocalDate;

/**
 *
 * @author kayla
 */
public class AssignmentInfo {
    
    private int id;
    private String name;
    private LocalDate dueDate;
    private String status;
    private int classId;

    public AssignmentInfo(String name, LocalDate dueDate, String status, int classId) {
        this.name = name;
        this.dueDate = dueDate;
        this.status = "Pending";
        this.classId = classId;
    }

    public AssignmentInfo(int id, String name, LocalDate dueDate, String status, int classId) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.status = status;
        this.classId = classId;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
    
    
}
