/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.controller;

import dlg.lk.bms.dto.user.*;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.UserService;
import dlg.lk.bms.entity.User;
import dlg.lk.bms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
@Api(value = "usercontroller", description = "API for rental user related functions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Get all users")
    @RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users) {
            userDTOS.add(fromUser(user));
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one user by UserId")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findOne(@PathVariable long userId) {
        User user = userService.findById(userId).get();
        UserDTO userDTO = fromUser(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Create an user")
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        User user = toUser(userDTO);
        User savedUser = userService.createUser(user);
        UserDTO savedUserDTO = fromUser(savedUser);

        return new ResponseEntity<>(savedUserDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an user")
    @RequestMapping(value = "/api/users", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> modify(@RequestBody UserDTO userDTO) {
        User user = toUser(userDTO);
        User savedUser = userService.updateUser(user);
        UserDTO savedUserDTO = fromUser(savedUser);

        return new ResponseEntity<>(savedUserDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an user")
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long userId) {
        Optional<User> user = userService.findById(userId);

        if(user.isPresent()) {
            userService.delete(user.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Authenticate an user by email and password")
    @RequestMapping(value = "/api/users/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginReciveDTO userLoginDTO) {
        User validateUser = userService.login(userLoginDTO);
        UserDTO validatedUserDTO = null;
        if(validateUser != null) {
            validatedUserDTO = fromUser(validateUser);
        }
        return new ResponseEntity<>(validatedUserDTO, HttpStatus.OK);
    }

    private User toUser(UserDTO userDTO) {
        User user = new User();

        if(userDTO.getId() != null) {
            user.setId(userDTO.getId());
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(userDTO.getPassword());

        if(userDTO.getAddedAdminId() != 0) {
            Optional<User> addedAdmin = userService.findById(userDTO.getAddedAdminId());
            if (!addedAdmin.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Added Admin User Not Found.");
            }
            user.setAddedAdmin(addedAdmin.get());
        }

        Optional<Role> role = roleService.getRole(userDTO.getRoleId());
        if(!role.isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Role not found.");
        }
        user.setRole(role.get());

        return user;
    }

    private UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        if(user.getAddedAdmin() != null) {
            userDTO.setAddedAdminId(user.getAddedAdmin().getId());
        }
        userDTO.setRoleId(user.getRole().getId());

        return userDTO;
    }
}
