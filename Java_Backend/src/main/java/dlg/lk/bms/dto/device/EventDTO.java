package dlg.lk.bms.dto.device;

import dlg.lk.bms.entity.EventParameter;

import java.util.List;

/**
 * Created by sahan on 9/6/18.
 */
public class EventDTO {
    private int id;

    private String name;

    private String type;

    private List<EventParameterDTO> eventParameters;

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

    public List<EventParameterDTO> getEventParameters() {
        return eventParameters;
    }

    public void setEventParameters(List<EventParameterDTO> eventParameters) {
        this.eventParameters = eventParameters;
    }
}
