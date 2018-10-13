package dlg.lk.bms.dto.role;

import java.util.List;

public class RoleDTO {
    private Long id;

    private String name;

    private String description;

    private List<Long> permissionIds;

    private boolean locConfigCreate;
    private boolean locConfigRead;
    private boolean locConfigUpdate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissions) {
        this.permissionIds = permissions;
    }

    public boolean isLocConfigCreate() {
        return locConfigCreate;
    }

    public void setLocConfigCreate(boolean locConfigCreate) {
        this.locConfigCreate = locConfigCreate;
    }

    public boolean isLocConfigRead() {
        return locConfigRead;
    }

    public void setLocConfigRead(boolean locConfigRead) {
        this.locConfigRead = locConfigRead;
    }

    public boolean isLocConfigUpdate() {
        return locConfigUpdate;
    }

    public void setLocConfigUpdate(boolean locConfigUpdate) {
        this.locConfigUpdate = locConfigUpdate;
    }
}
