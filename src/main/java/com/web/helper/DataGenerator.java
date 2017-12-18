package com.web.helper;

import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.RoleDao;
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

    @Autowired
    RoleDao roleDao;

    public void genareteDomain() {
        clearDatabase();

        User userBA = new User("User BA", "", "222", "222@222.com");
        Role baRole = new Role("BA");
        roleDao.add( baRole);
        userBA.setRoleId(baRole);
        userDao.add(userBA);
        User user = simpleUser();

        userDao.add(user);

        Project project = new Project("testProject-12", "https://github.com/testandreytsykh/ciamanager.git");
        project.setGitLogin("testandreytsykh");
        project.setGitPasword("ciamanager1");
//        Project project2 = new Project("testProject-2", "git-2-url");
        projectDao.add(project);
//        projectDao.add(project2);

        Org org = new Org("login1", "password1", "master", "link",  project);
//        Org org2 = new Org("login2", "password2", "branch2", "link",project2);
        orgDao.add(org);
//        orgDao.add(org2);

        org.setUserList(Arrays.asList(user, userBA));
        orgDao.update(org);
//        org2.setUserList(Arrays.asList(user));
//        orgDao.update(org2);

//        User user = simpleUser();
//        userDao.add(user);
//
//        Project project  = new Project("test-1", "git-1");
//        projectDao.add(project);
//
//        Org org = new Org("login1","password1", "branch1",  "link", project );
//        orgDao.add( org);
//        org.setUserList(Arrays.asList(user));
//        orgDao.update(org);

    }

    private User simpleUser(){
        Role role = new Role("test-role");
        roleDao.add(role);
        User user = new User("Test name", "test login", "111", "111@111.com");
        user.setRoleId(role);
        return user;
    }

    public void clearDatabase() {


        List<Org> orgs = orgDao.list();
        for (Org org : orgs) {
            orgDao.remove(org);
        }

        List<Project> projectList = projectDao.list();
        for (Project project : projectList) {
            projectDao.remove(project);
        }

        List<User> userList = userDao.list();
        for (User user : userList) {
            userDao.remove(user);
        }

        List<Role> roleList = roleDao.list();
        for (Role role:roleList){
            roleDao.remove(role);
        }
    }
}
