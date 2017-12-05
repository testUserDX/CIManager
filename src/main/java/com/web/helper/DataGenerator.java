package com.web.helper;

import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class DataGenerator {


    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    OrgDao orgDao;


    public void genareteDomain() {
        clearDatabase();

        User user = new User("Test name", "test login", "test-password", "test-email@test.com");
        userDao.add(user);

        Project project = new Project("testProject-1", "git-1-url");
        Project project2 = new Project("testProject-2", "git-2-url");
        projectDao.add(project);
        projectDao.add(project2);

        Org org = new Org("login1", "password1", "branch1", "type1", project);
        Org org2 = new Org("login2", "password2", "branch2", "type1", project2);
        orgDao.add(org);
        orgDao.add(org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

    }

    public void clearDatabase() {
        List<User> userList = userDao.list();
        for (User user : userList) {
            userDao.remove(user);
        }

        List<Org> orgs = orgDao.list();
        for (Org org : orgs) {
            orgDao.remove(org);
        }

        List<Project> projectList = projectDao.list();
        for (Project project : projectList) {
            projectDao.remove(project);
        }
    }

}
