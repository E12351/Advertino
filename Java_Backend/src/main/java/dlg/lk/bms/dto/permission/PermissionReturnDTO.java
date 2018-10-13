/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.dto.permission;

/**
 *
 * @author Dinuka_08966
 */
public class PermissionReturnDTO {
    
     private Long id;


    private String alertConfigCreate;
    private String alertConfigRead;
    private String alertConfigUpdate;
    private String dashBoardCreate;
    private String dashBoardRead;
    private String dashBoardUpdate;
    private String reportsCreate;
    private String reportsRead;
    private String reportsUpdate;
    
    
    private Long entityID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlertConfigCreate() {
        return alertConfigCreate;
    }

    public void setAlertConfigCreate(String alertConfigCreate) {
        this.alertConfigCreate = alertConfigCreate;
    }

    public String getAlertConfigRead() {
        return alertConfigRead;
    }

    public void setAlertConfigRead(String alertConfigRead) {
        this.alertConfigRead = alertConfigRead;
    }

    public String getAlertConfigUpdate() {
        return alertConfigUpdate;
    }

    public void setAlertConfigUpdate(String alertConfigUpdate) {
        this.alertConfigUpdate = alertConfigUpdate;
    }

    public String getDashBoardCreate() {
        return dashBoardCreate;
    }

    public void setDashBoardCreate(String dashBoardCreate) {
        this.dashBoardCreate = dashBoardCreate;
    }

    public String getDashBoardRead() {
        return dashBoardRead;
    }

    public void setDashBoardRead(String dashBoardRead) {
        this.dashBoardRead = dashBoardRead;
    }

    public String getDashBoardUpdate() {
        return dashBoardUpdate;
    }

    public void setDashBoardUpdate(String dashBoardUpdate) {
        this.dashBoardUpdate = dashBoardUpdate;
    }

    public String getReportsCreate() {
        return reportsCreate;
    }

    public void setReportsCreate(String reportsCreate) {
        this.reportsCreate = reportsCreate;
    }

    public String getReportsRead() {
        return reportsRead;
    }

    public void setReportsRead(String reportsRead) {
        this.reportsRead = reportsRead;
    }

    public String getReportsUpdate() {
        return reportsUpdate;
    }

    public void setReportsUpdate(String reportsUpdate) {
        this.reportsUpdate = reportsUpdate;
    }

    public Long getEntityID() {
        return entityID;
    }

    public void setEntityID(Long entityID) {
        this.entityID = entityID;
    }

    
}
