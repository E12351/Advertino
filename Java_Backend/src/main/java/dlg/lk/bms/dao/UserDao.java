/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.dao;

import dlg.lk.bms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dinuka_08966
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>{

    public User findByName(String name);

    public User findByEmail(String email);

    public User findByEmailAndPassword(String email, String password);
    
}
