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
public class LocationPermissionDTO {

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
    
}
