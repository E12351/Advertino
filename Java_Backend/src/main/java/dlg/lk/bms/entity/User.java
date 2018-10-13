/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Collection;

/**
 *
 * @author Dinuka_08966
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    //id field has been set to auto increment
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String createdDate;
    private String status;
    private String remembertoken;
    private String type;

    @ManyToOne
    private User addedAdmin;

    @OneToMany(mappedBy = "addedAdmin")
    private Collection<User> addedUsers;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemembertoken() {
        return remembertoken;
    }

    public void setRemembertoken(String remembertoken) {
        this.remembertoken = remembertoken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }       
    
    public User getAddedAdmin() {
        return addedAdmin;
    }

    public void setAddedAdmin(User addedAdmin) {
        this.addedAdmin = addedAdmin;
    }

    public Collection<User> getAddedUsers() {
        return addedUsers;
    }

    public void setAddedUsers(Collection<User> addedUsers) {
        this.addedUsers = addedUsers;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
