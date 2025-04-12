
package college.tracker.info;

/**
 *
 * @author kayla
 */
public class ThemeInfo {
    
    private int id;
    private String name;
    private int isSelected;

    public ThemeInfo(int id, String name, int isSelected) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
