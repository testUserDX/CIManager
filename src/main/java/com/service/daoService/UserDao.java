package com.service.daoService;
import com.model.Project;
import com.model.User;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;


public interface UserDao extends GenericDao<User, Long> {

    public List<Project> usersProjectList(User user);

}
