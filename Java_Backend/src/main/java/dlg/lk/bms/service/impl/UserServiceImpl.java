/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service.impl;

import dlg.lk.bms.dao.RoleDao;
import dlg.lk.bms.dao.UserDao;
import dlg.lk.bms.dto.user.UserAddRoleReciveDTO;
import dlg.lk.bms.dto.user.UserLoginReciveDTO;
import dlg.lk.bms.validation.UserValidation;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.entity.User;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.UserService;
import dlg.lk.bms.util.CommanUtil;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dinuka_08966
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserValidation userValidation;

    @Autowired
    private CommanUtil commanUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User createUser(User reciveUser) {
        userValidation.checkEmailExsist(reciveUser.getEmail());
        reciveUser.setCreatedDate(commanUtil.getServerDateDataBase());
        return userDao.save(reciveUser);

    }

    @Override
    public User validateUser(UserLoginReciveDTO userLoginDTO) {
        User findByEmailAndPassword = userDao.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        UserValidation.userEmailExsists(findByEmailAndPassword);
        return findByEmailAndPassword;
    }

    @Override
    public User updateUser(User reciveUser) {

        User user = userDao.getOne(reciveUser.getId());

        reciveUser.setEmail(user.getEmail());
        reciveUser.setCreatedDate(user.getCreatedDate());
        reciveUser.setRole(user.getRole());
        User user1 = userDao.save(reciveUser);
        return user1;

    }

    @Override
    public User findByEmail(String email) {
        User findByEmail = userDao.findByEmail(email);
        UserValidation.userEmailExsists(findByEmail);
        return findByEmail;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User addRoleToUser(UserAddRoleReciveDTO addRoleReciveDTO) {
        User user = userDao.getOne(addRoleReciveDTO.getUserId());

        Role role = roleDao.findById(addRoleReciveDTO.getRoleId()).get();

        user.setRole(role);
        return userDao.save(user);
//         userDao.findById(addRoleReciveDTO.getUserId()).get();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean delete(User user) {
        Collection<User> addedUsers = user.getAddedUsers();
        for(User addedUser : addedUsers) {
            addedUser.setAddedAdmin(null);
            userDao.save(addedUser);
        }
        userDao.delete(user);

        return userDao.getOne(user.getId()) == null;
    }

    @Override
    public User login(UserLoginReciveDTO userLoginReciveDTO) {
        User user = userDao.findByEmail(userLoginReciveDTO.getEmail());
        if(user != null) {
            if(user.getPassword().equals(userLoginReciveDTO.getPassword())){
                return user;
            } else {
                throw new CustomException(HttpStatus.CONFLICT, "Wrong Password.");
            }
        } else {
            throw new CustomException(HttpStatus.NO_CONTENT, "No user found for email.");
        }
    }

}
