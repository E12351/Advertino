/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.controller;

import dlg.lk.bms.dto.permission.LocationPermissionDTO;
import dlg.lk.bms.entity.Entity;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.dto.permission.PermissionDTO;
import dlg.lk.bms.entity.Permission;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.EntityService;
import dlg.lk.bms.service.PermissionService;
import dlg.lk.bms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dinuka_08966
 */
@RestController
@Api(value = "permissioncontroller", description = "API for rental entity related functions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
public class PermisssonController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private EntityService entityService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Get all permissions")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PermissionDTO>> findAll() {
        Collection<Permission> permissions = permissionService.findAll();
        List<PermissionDTO> permissionDTOS = new ArrayList<>();
        for(Permission permission : permissions) {
            permissionDTOS.add(fromPermission(permission));
        }

        return new ResponseEntity<>(permissionDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one permission by permissionId")
    @RequestMapping(value = "/api/permissions/{permissionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissionDTO> findOne(@PathVariable long permissionId) {
        Permission permission = permissionService.findOne(permissionId).get();
        PermissionDTO permissionDTO = fromPermission(permission);

        return new ResponseEntity<>(permissionDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a permission")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissionDTO> create(@RequestBody PermissionDTO permissionDTO) {
        Permission permission = toPermission(permissionDTO);
        Permission savedPermission = permissionService.create(permission);
        PermissionDTO savedPermissionDTO = fromPermission(savedPermission);

        return new ResponseEntity<>(savedPermissionDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a permission")
    @RequestMapping(value = "/api/permissions", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissionDTO> modify(@RequestBody PermissionDTO permissionDTO) {
        Permission permission = toPermission(permissionDTO);
        Permission savedPermission = permissionService.update(permission);
        PermissionDTO savedPermissionDTO = fromPermission(savedPermission);

        return new ResponseEntity<>(savedPermissionDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a permission")
    @RequestMapping(value = "/api/permissions/{permissionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long permissionId) {
        Optional<Permission> permission = permissionService.findOne(permissionId);

        if(permission.isPresent()) {
            permissionService.delete(permission.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Permission toPermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();

        if(permissionDTO.getId() != null && permissionDTO.getId() != 0) {
            permission.setId(permissionDTO.getId());
        }

        permission.setAlertConfigCreate(permissionDTO.getAlertConfigCreate());
        permission.setAlertConfigRead(permissionDTO.getAlertConfigRead());
        permission.setAlertConfigUpdate(permissionDTO.getAlertConfigUpdate());
        permission.setDashBoardCreate(permissionDTO.getDashBoardCreate());
        permission.setDashBoardRead(permissionDTO.getDashBoardRead());
        permission.setDashBoardUpdate(permissionDTO.getDashBoardUpdate());
        permission.setReportsCreate(permissionDTO.getReportsCreate());
        permission.setReportsRead(permissionDTO.getReportsRead());
        permission.setReportsUpdate(permissionDTO.getReportsUpdate());

        Optional<Entity> entity = entityService.findOne(permissionDTO.getEntityId());
        if(!entity.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Entity Not Found.");
        }
        permission.setEntity(entity.get());

        Optional<Role> role = roleService.getRole(permissionDTO.getRoleId());
        if(!role.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Role Not Found.");
        }
        permission.setRole(role.get());

        return permission;
    }

    private PermissionDTO fromPermission(Permission permission) {
        PermissionDTO permissionDTO = new PermissionDTO();

        permissionDTO.setId(permission.getId());

        permissionDTO.setAlertConfigCreate(permission.getAlertConfigCreate());
        permissionDTO.setAlertConfigRead(permission.getAlertConfigRead());
        permissionDTO.setAlertConfigUpdate(permission.getAlertConfigUpdate());
        permissionDTO.setDashBoardCreate(permission.getDashBoardCreate());
        permissionDTO.setDashBoardRead(permission.getDashBoardRead());
        permissionDTO.setDashBoardUpdate(permission.getDashBoardUpdate());
        permissionDTO.setReportsCreate(permission.getReportsCreate());
        permissionDTO.setReportsRead(permission.getReportsRead());
        permissionDTO.setReportsUpdate(permission.getReportsUpdate());

        permissionDTO.setEntityId(permission.getEntity().getId());
        permissionDTO.setRoleId(permission.getRole().getId());

        return permissionDTO;
    }
}
