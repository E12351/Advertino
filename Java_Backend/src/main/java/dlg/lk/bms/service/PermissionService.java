/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service;

import dlg.lk.bms.dto.permission.LocationPermissionDTO;
import dlg.lk.bms.entity.Permission;
import dlg.lk.bms.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dinuka_08966
 */
public interface PermissionService {

    public Permission create(Permission permission);

    public LocationPermissionDTO getPermissionForLocation(String userId);

    public Permission getPermissionForEntity(String entityId, String userId);

    Optional<Permission> findOne(Long id);

    List<Permission> findAll();

    Permission update(Permission permission);

    boolean delete(Permission permission);

    List<Permission> getPermissionsByRole(Role role);
}
