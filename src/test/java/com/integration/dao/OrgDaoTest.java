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
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrgDaoTest extends DomainTestBase {

    @Test
    public void testGetOrgByUserAndProject() {
        Role role = getRole();
        roleDao.add(role);
        User user = getUser(role);
        user.setEmail("test@test.com");
        userDao.add(user);

        Project project = getProject();
        projectDao.add(project);
        Project project2 = getProject();
        project2.setName("test-name-2");
        projectDao.add(project2);

        Org org = getOrg(project);
        orgDao.add(org);
        Org org2 = getOrg(project2);
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

//    @Test
//    public void testGetOrgWithUser() {
//        Role adminRole = getRole("admin");
//        Role userRole = getRole("user");
//        roleDao.add(adminRole);
//        roleDao.add(userRole);
//
//        User userAdmin = getUser(adminRole);
//        User userUser = getUser(userRole);
//        User userUser2 = getUser(userRole);
//
//        userDao.add(userAdmin);
//        userDao.add(userUser);
//        userDao.add(userUser2);
//
//        Project project = getProject();
//        projectDao.add(project);
//
//        Org org = getOrg(project);
//        org.setUserList(Arrays.asList(userAdmin));
//        orgDao.add(org);
//
//        Org foundedOrg = orgDao.getfullOrgWithUsers(org.getId());
//        assertEquals(0, foundedOrg.getUserList().size());
//    }
    /* @Test
    public void testGetOrgsByNameOfProject() {
        Role role = new Role("test-role");
        roleDao.add(role);

        User user  = getUser(role);
        user.setEmail("test@test.com");
        userDao.add(user);

        Project project = getProject();
        projectDao.add(project);
        Project project2 = getProject();
        project2.setName("test2");
        projectDao.add(project2);

        Org org = getOrg(project);
        orgDao.add(org);
        Org org2 = getOrg(project2);
        orgDao.add(org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

        List<Org> foundedOrgList = orgDao.getOrgListByProjectName("test");
        assertEquals(2, foundedOrgList.size());
    }*/

//    @Test
//    public void testGetOrgsOfUser() {
//        Role role = new Role("test-role");
//        roleDao.add(role);
//
//        User user  = new User("test-name", "test-login", "test-pass", role);
//
//        user.setEmail("test@test.com");
//        userDao.add(user);
//
//        Project project = new Project("test", "git-1");
//        projectDao.add(project);
//        Project project2 = new Project("test", "git-2");
//        projectDao.add(project2);
//
//        Org org = new Org("login1", "password1", "branch1", "link", project);
//        orgDao.add(org);
//        Org org2 = new Org("login2", "password2", "branch2",  "link",project2);
//        orgDao.add(org2);
//
//        org.setUserList(Arrays.asList(user));
//        orgDao.update(org);
//        org2.setUserList(Arrays.asList(user));
//        orgDao.update(org2);
//
//        List<Org> testOrgs = orgDao.getOrgListOfUser("test-name");
//        assertEquals(2, testOrgs.size());
//    }


}
