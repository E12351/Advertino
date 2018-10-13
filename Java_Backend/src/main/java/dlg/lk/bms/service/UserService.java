/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service;

import dlg.lk.bms.dto.user.UserAddRoleReciveDTO;
import dlg.lk.bms.dto.user.UserLoginReciveDTO;
import dlg.lk.bms.entity.User;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dinuka_08966
 */
public interface UserService {

    public User createUser(User reciveUser);

    public User validateUser(UserLoginReciveDTO userLoginDTO);

    public User updateUser(User reciveUser);

    public User findByEmail(String email);

    public List<User> findAll();

    public User addRoleToUser(UserAddRoleReciveDTO addRoleReciveDTO);

    public Optional<User> findById(Long id);

    boolean delete(User user);

    User login(UserLoginReciveDTO userLoginReciveDTO);
}
