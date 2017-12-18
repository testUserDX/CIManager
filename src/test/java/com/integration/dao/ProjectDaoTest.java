package com.integration.dao;

import com.DomainTestBase;
import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Name;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class ProjectDaoTest extends DomainTestBase {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Test
    public void testAdd(){
        int size = projectDao.list().size();
        projectDao.add(getSimpleProject());
        assertTrue(size<projectDao.list().size());
    }

    @Test
    public void testUpdate(){
        Project project = getSimpleProject();
        projectDao.add(project);
        long projectId = project.getId();
        project.setName("updated");
        projectDao.update(project);
        assertEquals("updated", projectDao.find(projectId).getName());
    }


    @Test
    public void testFind(){
        Project project = getSimpleProject();
        projectDao.add(project);
        long projectId = project.getId();
        assertEquals(project, projectDao.find(projectId));
    }

    @Test
    public void testList(){
        assertTrue(projectDao.list().isEmpty());
        List<Project> projectList = Arrays.asList(getSimpleProject(),
                new Project("test1","https://github.com/testUserDX/CIManager"),
        new Project("test2","https://github.com/testUserDX/CIManager"),
        new Project("test3","https://github.com/testUserDX/CIManager")
        );

        for(Project project : projectList){
            projectDao.add(project);
        }

        List<Project> foundedList = projectDao.list();
        assertEquals(4, foundedList.size());
        assertTrue(projectList.containsAll(foundedList));
    }

    @Test
    public void testRemove(){
        Project project = getSimpleProject();
        projectDao.add(project);
        long projectId = project.getId();
        projectDao.remove(project);
        assertNull(projectDao.find(projectId));
    }

    @Test
    public void testUsersProjectList(){
        User user = simpleUser();
        userDao.add(user);

        Project project  = new Project("test-1", "git-1");
        projectDao.add(project);

        Org org = new Org("login1","password1", "branch1",  "link", project );
        orgDao.add( org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        List<Project> projectList = projectDao.usersProjectList(user);
        assertEquals(1, projectList.size());
    }

    @Test
    public void testUsersProjectListByEmail(){
        User user = simpleUser();
        user.setEmail("test@test.com");
        userDao.add(user);

        Project project  = new Project("test-1", "git-1");
        projectDao.add(project);
        Project project2  = new Project("test-2", "git-2");
        projectDao.add(project2);

        Org org = new Org("login1","password1", "branch1",  "link", project );
        orgDao.add( org);
        Org org2 = new Org("login2","password2", "branch2",   "link",project2 );
        orgDao.add( org2);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);
        org2.setUserList(Arrays.asList(user));
        orgDao.update(org2);

        List<Project> projectList = projectDao.usersProjectListByEmail("test@test.com");
        assertEquals(2, projectList.size());
    }

    @Test
    public void testProjectUsersList(){
        User user = simpleUser();
        userDao.add(user);

        Project project  = new Project("name", "git-1");
        projectDao.add(project);

        Org org = new Org("login","password", "branch",  "link", project );
        orgDao.add( org);

        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        List<User> testUserList = projectDao.usersProjectListByProjectName("name");
        assertEquals(1, testUserList.size());
    }

    @Test
    public void testFullFindProject(){
        User user = simpleUser();
        userDao.add(user);

        Project project  = new Project("test-1", "git-1");
        projectDao.add(project);

        Org org = new Org("login1","password1", "branch1",  "link", project );
        orgDao.add( org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);


        Project project1 = projectDao.findFullProject(project.getId());
        assertEquals(1, project1.getOrgList().get(0).getUserList().size());
    }

    private User simpleUser(){
        Role role = new Role("test-role");
        roleDao.add(role);
        return new User("test-name", "test-login", "test-pass", role);
    }

    private Project getSimpleProject(){
        Project project = new Project("test","https://github.com/testUserDX/CIManager");
        return project;
    }
}
