package com.service.daoService;

import com.model.User;
import com.service.daoService.generalDaoService.GenericDao;


public interface UserDao extends GenericDao<User, Long> {

    public boolean verifyUserByEmailAndPassword(String email, String passord);

    public User getUserByEmil(String email);

}
