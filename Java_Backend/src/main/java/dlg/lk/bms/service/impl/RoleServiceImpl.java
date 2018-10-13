/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service.impl;

import dlg.lk.bms.dao.RoleDao;
import dlg.lk.bms.entity.Role;
import dlg.lk.bms.service.RoleService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dinuka_08966
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;
    

    
    @Override
    public Role addNewRole(Role reciveRole) {

        Role role = roleDao.save(reciveRole);
        
        return role;

    }

    @Override
    public Optional<Role> getRole(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleDao.getOne(id);
    }

    @Override
    public Role updateRole(Role role) {
        Role existingRole = roleDao.getOne(role.getId());
        if(existingRole != null) {
            return roleDao.save(role);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Role role) {
        roleDao.delete(role);

        return roleDao.getOne(role.getId()) == null;
    }

    @Override
    public boolean isExist(Long id) {
        return roleDao.existsById(id);
    }


}
