package com.integration.dao;


import com.DomainTestBase;
import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.RoleDao;
import com.service.daoService.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrgDaoTest extends DomainTestBase {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testGetOrgByUserAndProject() {

        Role role = new Role("test-role");
        roleDao.add(role);

        User user  = new User("test-name", "test-login", "test-pass", role);

        user.setEmail("test@test.com");
        userDao.add(user);

        Project project = new Project("test-1", "git-1");
        projectDao.add(project);
        Project project2 = new Project("test-2", "git-2");
        projectDao.add(project2);

        Org org = new Org("login1", "password1", "branch1", "link", project);
        orgDao.add(org);
        Org org2 = new Org("login2", "password2", "branch2", "link", project2);
        orgDao.add(org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

        List<Org> orgList = orgDao.getOrgByUserAndProject(project.getId(), "test@test.com");
        assertEquals(1, orgList.size());
        //TODO uncomment this method
//        assertEquals(org, orgList.get(0));
    }

    @Test
    public void testGetOrgsByNameOfProject() {
        Role role = new Role("test-role");
        roleDao.add(role);

        User user  = new User("test-name", "test-login", "test-pass", role);

        user.setEmail("test@test.com");
        userDao.add(user);

        Project project = new Project("test", "git-1");
        projectDao.add(project);
        Project project2 = new Project("test", "git-2");
        projectDao.add(project2);

        Org org = new Org("login1", "password1", "branch1",  "link", project);
        orgDao.add(org);
        Org org2 = new Org("login2", "password2", "branch2",  "link", project2);
        orgDao.add(org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

        List<Org> testOrgs = orgDao.getOrgListByProjectName("test");
        assertEquals(2, testOrgs.size());
    }

    @Test
    public void testGetOrgsOfUser() {
        Role role = new Role("test-role");
        roleDao.add(role);

        User user  = new User("test-name", "test-login", "test-pass", role);

        user.setEmail("test@test.com");
        userDao.add(user);

        Project project = new Project("test", "git-1");
        projectDao.add(project);
        Project project2 = new Project("test", "git-2");
        projectDao.add(project2);

        Org org = new Org("login1", "password1", "branch1", "link", project);
        orgDao.add(org);
        Org org2 = new Org("login2", "password2", "branch2",  "link",project2);
        orgDao.add(org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

        List<Org> testOrgs = orgDao.getOrgListOfUser("test-name");
        assertEquals(2, testOrgs.size());
    }
}
