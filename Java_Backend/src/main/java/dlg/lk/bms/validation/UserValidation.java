/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.validation;

import dlg.lk.bms.dao.UserDao;
import dlg.lk.bms.entity.User;
import dlg.lk.bms.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dinuka_08966
 */
@Component
public class UserValidation {

    public static void userEmailExsists(User findByEmail) {
        if (findByEmail == null) {
            throw new CustomException(HttpStatus.CONFLICT, "Email is incorrect");
        }
    }

    @Autowired
    private UserDao userDao;

    public void checkEmailExsist(String email) {

        User findByEmail = userDao.findByEmail(email);
        if (findByEmail != null) {
            throw new CustomException(HttpStatus.CONFLICT, "Email Already Exsists");
        }
    }

}
