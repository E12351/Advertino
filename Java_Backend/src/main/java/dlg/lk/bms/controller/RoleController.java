/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.controller;

import dlg.lk.bms.dto.role.RoleAddReciveDTO;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.dto.role.RoleDTO;
import dlg.lk.bms.dto.role.RoleListDTO;
import dlg.lk.bms.dto.role.RoleReturnDTO;
import dlg.lk.bms.entity.Permission;
import dlg.lk.bms.service.PermissionService;
import dlg.lk.bms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Dinuka_08966
 */
@RestController
@Api(value = "rolecontroller", description = "API for role related functions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "Get all roles")
    @RequestMapping(value = "/api/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RoleDTO>> findAll() {
        List<Role> roles = roleService.getRoles();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for(Role role : roles) {
            roleDTOS.add(fromRole(role));
        }

        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one role by roleId")
    @RequestMapping(value = "/api/roles/{roleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> findOne(@PathVariable long roleId) {
        Role role = roleService.findById(roleId);
        RoleDTO roleDTO = fromRole(role);

        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a role")
    @RequestMapping(value = "/api/roles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDTO) {
        Role role = toRole(roleDTO);
        Role savedRole = roleService.addNewRole(role);
        RoleDTO savedRoleDTO = fromRole(savedRole);

        return new ResponseEntity<>(savedRoleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a role")
    @RequestMapping(value = "/api/roles", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDTO> modify(@RequestBody RoleDTO roleDTO) {
        Role role = toRole(roleDTO);
        Role savedRole = roleService.updateRole(role);
        RoleDTO savedRoleDTO = fromRole(savedRole);

        return new ResponseEntity<>(savedRoleDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a role")
    @RequestMapping(value = "/api/roles/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long roleId) {
        Role role = roleService.findById(roleId);

        if(role != null) {
            roleService.delete(role);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Role toRole(RoleDTO roleDTO) {
        Role role = new Role();

        if(roleDTO.getId() != null) {
            role.setId(roleDTO.getId());
        }

        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());

        List<Long> permissionIds = roleDTO.getPermissionIds();
        List<Permission> permissions = new ArrayList<>();

        for(Long permissionId : permissionIds) {
            Optional<Permission> permission = permissionService.findOne(permissionId);
            if(!permission.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Permission Not Found.");
            }
            permissions.add(permission.get());
        }

        role.setPermissions(permissions);

        role.setLocConfigCreate(roleDTO.isLocConfigCreate());
        role.setLocConfigRead(roleDTO.isLocConfigRead());
        role.setLocConfigUpdate(roleDTO.isLocConfigUpdate());

        return role;
    }

    private RoleDTO fromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());

        List<Permission> permissions = role.getPermissions();
        List<Long> permissionIds = new ArrayList<>();
        for(Permission permission : permissions) {
            permissionIds.add(permission.getId());
        }

        roleDTO.setPermissionIds(permissionIds);

        roleDTO.setLocConfigCreate(role.isLocConfigCreate());
        roleDTO.setLocConfigRead(role.isLocConfigRead());
        roleDTO.setLocConfigUpdate(role.isLocConfigUpdate());

        return roleDTO;
    }
}
