package com.service.daoService;

import com.model.Org;
import com.model.User;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;


public interface UserDao extends GenericDao<User, Long> {

    public boolean verifyUserByEmailAndPassword(String email, String passord);

    public User getUserByEmil(String email);

    public List<User> getAllUsersWithoutAdmins();

    public List<User> getOrgUserWithoutAdmin(Org org);

    public User getOrgAdmin(Org org);

    public User getUserById(Long userId);
}
