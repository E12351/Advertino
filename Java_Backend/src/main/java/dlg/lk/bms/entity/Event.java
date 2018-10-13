package dlg.lk.bms.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sahan on 9/6/18.
 */
@javax.persistence.Entity
public class Event {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String type;

    @ManyToOne
    private Device device;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventParameter> eventParameters;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<EventParameter> getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(List<EventParameter> eventParameters) {
        this.eventParameters = eventParameters;
    }
}
