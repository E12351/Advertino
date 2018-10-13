package dlg.lk.bms.dto.device;

/**
 * Created by sahan on 9/6/18.
 */
public class EventParameterDTO {
    private int id;

    private String name;

    private String mappedName;

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

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }
}
