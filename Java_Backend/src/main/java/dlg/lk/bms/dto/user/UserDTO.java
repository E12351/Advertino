package dlg.lk.bms.dto.user;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private long addedAdminId;
    private long roleId;

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

    public long getAddedAdminId() {
        return addedAdminId;
    }

    public void setAddedAdminId(long addedAdminId) {
        this.addedAdminId = addedAdminId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
