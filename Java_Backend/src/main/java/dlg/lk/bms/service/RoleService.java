/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service;

import dlg.lk.bms.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dinuka_08966
 */
public interface RoleService {

    public Role addNewRole(Role reciveRole);

    public Optional<Role> getRole(Long id);

    public List<Role> getRoles();

    public Role findById(Long id);
    
    Role updateRole(Role role);

    boolean delete(Role role);

    boolean isExist(Long id);
}
