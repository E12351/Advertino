package dlg.lk.bms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@javax.persistence.Entity
//@Table(name = "device", uniqueConstraints = {@UniqueConstraint(columnNames = "mac_address")})
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String iotID;

    @NotNull
    private String macAddress;

    private String remark;

    private boolean isVirtual;

    @ManyToMany
    @JoinTable(name = "device_entity", joinColumns = {
            @JoinColumn(referencedColumnName = "id", name = "device_id")},
            inverseJoinColumns = {
                    @JoinColumn(referencedColumnName = "id", name = "entity_id")
            })
    private List<Entity> entities;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<Event> events;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIotID() {
        return iotID;
    }

    public void setIotID(String iotID) {
        this.iotID = iotID;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<dlg.lk.bms.entity.Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<dlg.lk.bms.entity.Entity> entities) {
        this.entities = entities;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
