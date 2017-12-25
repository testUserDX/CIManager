package com.integration.dao;

import com.DomainTestBase;
import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.*;
import com.sun.xml.internal.stream.Entity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Name;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class ProjectDaoTest extends DomainTestBase {

    @Test
    public void testAdd() {
        int size = projectDao.list().size();
        projectDao.add(getProject());
        assertTrue(size < projectDao.list().size());
    }

    @Test
    public void testUpdate() {
        Project project = getProject();
        projectDao.add(project);
        long projectId = project.getId();
        project.setName("updated");
        projectDao.update(project);
        assertEquals("updated", projectDao.find(projectId).getName());
    }

    @Test
    public void testFind() {
        Project project = getProject();
        projectDao.add(project);
        long projectId = project.getId();
        assertEquals(project, projectDao.find(projectId));
    }

    @Test
    public void testList() {
        assertTrue(projectDao.list().isEmpty());
        List<Project> projectList = Arrays.asList(
                new Project("test-name-1", "test-url-1", "test-login-1", "test-pass-1", "test-folder-1"),
                new Project("test-name-2", "test-url-2", "test-login-2", "test-pass-2", "test-folder-2"),
                new Project("test-name-3", "test-url-3", "test-login-3", "test-pass-3", "test-folder-3")
        );
        for (Project project : projectList) {
            projectDao.add(project);
        }
        List<Project> foundedList = projectDao.list();
        assertEquals(3, foundedList.size());
        assertTrue(projectList.containsAll(foundedList));
    }

    @Test
    public void testRemove() {
        Project project = getProject();
        projectDao.add(project);
        long projectId = project.getId();
        projectDao.remove(project);
        assertNull(projectDao.find(projectId));
    }

    @Test
    public void testUsersProjectList() {
        User user = getUser();
        userDao.add(user);

        Project project = getProject();
        projectDao.add(project);

        Org org = getOrg(project);
        orgDao.add(org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        List<Project> projectList = projectDao.usersProjectList(user);
        assertEquals(1, projectList.size());
    }

    @Test
    public void testUsersProjectListByEmail() {
        User user = getUser();
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

        List<Project> projectList = projectDao.usersProjectListByEmail("test@test.com");
        assertEquals(2, projectList.size());
    }

    @Test
    public void testProjectUsersList() {
        User user = getUser();
        userDao.add(user);
        Project project = getProject();
        projectDao.add(project);
        Org org = new Org("login", "password", "branch", "link", project);
        orgDao.add(org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        List<User> testUserList = projectDao.usersProjectListByProjectName("test-name");
        assertEquals(1, testUserList.size());
    }

    @Test
    public void testFullFindProject() {
        User user = getUser();
        userDao.add(user);

        Project project = getProject();
        projectDao.add(project);

        Org org = new Org("login1", "password1", "branch1", "link", project);
        orgDao.add(org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        Project project1 = projectDao.findFullProject(project.getId());
        assertEquals(1, project1.getOrgList().get(0).getUserList().size());
    }
}
