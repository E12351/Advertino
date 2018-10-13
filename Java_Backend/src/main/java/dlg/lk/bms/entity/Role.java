/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Dinuka_08966
 */
@javax.persistence.Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)   //id field has been set to auto increment
    private Long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "role")
    private List<Permission> permissions = new ArrayList<>();

    private boolean locConfigCreate;
    private boolean locConfigRead;
    private boolean locConfigUpdate;

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
