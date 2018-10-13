/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.dao;

import dlg.lk.bms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dinuka_08966
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long>{
    
}
