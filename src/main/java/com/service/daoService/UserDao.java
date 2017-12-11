package com.service.daoService;
import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;


public interface UserDao extends GenericDao<User, Long> {

    public List<Project> usersProjectList(User user);

    public List<Project> usersProjectListByEmail(String email);

    public boolean verifyUserByEmailAndPassword(String email, String passord);

    public User getUserByEmil(String email);


}
