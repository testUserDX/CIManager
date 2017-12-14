package com.service.daoService;

import com.model.Project;
import com.model.User;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;

public interface ProjectDao extends GenericDao<Project, Long> {

    public List<Project> usersProjectList(User user);

    public List<Project> usersProjectListByEmail(String email);

    public List<User> usersProjectListByProjectName(String projectName);

    public List<Project> getAllProjects();
}
