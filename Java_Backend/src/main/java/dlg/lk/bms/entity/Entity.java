/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Dinuka_08966
 */
@javax.persistence.Entity
@Table(name = "entity")
public class Entity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)    //id field has been set to auto increment
    private Long id;

    private String name;
    private String location;
    private String telephone;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_PARENT_ID")
    private Entity parentEntity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "entity", joinColumns = {
        @JoinColumn(name = "ENTITY_PARENT_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ID")})
    private List<Entity> subEntity = new ArrayList<>();

    @ManyToMany(mappedBy = "entities")
    private List<Device> devices;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "entity")
    private List<Permission> permissions;
    
    public Entity getParentEntity() {
        return parentEntity;
    }

    public List<Entity> getSubEntity() {
        return subEntity;
    }

    public void setParentEntity(Entity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public void setSubEntity(List<Entity> subEntity) {
        this.subEntity = subEntity;
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
