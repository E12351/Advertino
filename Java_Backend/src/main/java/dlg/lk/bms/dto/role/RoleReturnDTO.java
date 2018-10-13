/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.dto.role;

import dlg.lk.bms.dto.permission.PermissionReturnDTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dinuka_08966
 */
public class RoleReturnDTO {

    private Long id;

    private String name;

    private String description;
    private List<PermissionReturnDTO> permissions = new ArrayList<>();

    private String locConfigCreate;
    private String locConfigRead;
    private String locConfigUpdate;

    public String getLocConfigCreate() {
        return locConfigCreate;
    }

    public void setLocConfigCreate(String locConfigCreate) {
        this.locConfigCreate = locConfigCreate;
    }

    public String getLocConfigRead() {
        return locConfigRead;
    }

    public void setLocConfigRead(String locConfigRead) {
        this.locConfigRead = locConfigRead;
    }

    public String getLocConfigUpdate() {
        return locConfigUpdate;
    }

    public void setLocConfigUpdate(String locConfigUpdate) {
        this.locConfigUpdate = locConfigUpdate;
    }

  
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

    public List<PermissionReturnDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionReturnDTO> permissions) {
        this.permissions = permissions;
    }

}
