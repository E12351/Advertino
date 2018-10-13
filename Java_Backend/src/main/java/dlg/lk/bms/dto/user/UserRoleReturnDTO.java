/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.dto.user;

import dlg.lk.bms.dto.role.RoleReturnDTO;

/**
 *
 * @author Dinuka_08966
 */
public class UserRoleReturnDTO {

    private Long id;
    private String name;

    private String type;
    private RoleReturnDTO role;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RoleReturnDTO getRole() {
        return role;
    }

    public void setRole(RoleReturnDTO role) {
        this.role = role;
    }


}
