package dlg.lk.bms.dto.entity;

import java.util.List;

public class EntityDTO {
    private Long id;
    private String name;
    private String location;
    private String telephone;
    private String description;
    private Long parentEntityId;
    private List<Long> subEntityIds;
    private List<Long> deviceIds;
    private List<Long> virtualDeviceIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentEntityId() {
        return parentEntityId;
    }

    public void setParentEntityId(Long parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public List<Long> getSubEntityIds() {
        return subEntityIds;
    }

    public void setSubEntityIds(List<Long> subEntityIds) {
        this.subEntityIds = subEntityIds;
    }

    public List<Long> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<Long> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public List<Long> getVirtualDeviceIds() {
        return virtualDeviceIds;
    }

    public void setVirtualDeviceIds(List<Long> virtualDeviceIds) {
        this.virtualDeviceIds = virtualDeviceIds;
    }
}
