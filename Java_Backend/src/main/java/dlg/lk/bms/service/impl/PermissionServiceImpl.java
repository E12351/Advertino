/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service.impl;

import dlg.lk.bms.dao.UserDao;
import dlg.lk.bms.dto.permission.LocationPermissionDTO;
import dlg.lk.bms.dao.PermissionDao;
import dlg.lk.bms.entity.Permission;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.entity.User;
import dlg.lk.bms.service.PermissionService;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dinuka_08966
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Permission create(Permission permission) {

        return permissionDao.save(permission);
    }

    @Override
    public LocationPermissionDTO getPermissionForLocation(String userId) {
        long userID = Long.parseLong(userId);
        User user = userDao.findById(userID).get();
        Role role = user.getRole();
        LocationPermissionDTO lpdto = modelMapper.map(role, LocationPermissionDTO.class);
        return lpdto;
    }

    @Override
    public Permission getPermissionForEntity(String entityId, String userId) {
        long userID = Long.parseLong(userId);
        long entityID = Long.parseLong(entityId);
        User user = userDao.findById(userID).get();
        Role role = user.getRole();
        if (role == null) {
            return null;
        }

        List<Permission> permissions = role.getPermissions();
        if (permissions == null) {
            return null;
        }
        for (int i = 0; i < permissions.size(); i++) {
            Permission get = permissions.get(i);
            if (get.getEntity().getId() == entityID) {

                Permission map = modelMapper.map(get, Permission.class);
                return map;
            }
        }
        return null;
    }

    @Override
    public Optional<Permission> findOne(Long id) {
        return permissionDao.findById(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public Permission update(Permission permission) {
        if(permission.getId() != null && permission.getId() != 0) {
            Permission existingPermission = permissionDao.getOne(permission.getId());

            if(existingPermission != null) {
                return permissionDao.save(permission);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Permission permission) {
        if(permission.getId() != null && permission.getId() != 0) {
            Permission existingPermission = permissionDao.getOne(permission.getId());

            if(existingPermission != null) {
                permissionDao.delete(permission);

                Permission deletedDevice = permissionDao.getOne(existingPermission.getId());

                if(deletedDevice == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Permission> getPermissionsByRole(Role role) {
        return permissionDao.findByRole(role);
    }

}
