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

    private Project getSimpleProject(){
        Project project = new Project("test","https://github.com/testUserDX/CIManager");
        return project;
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
        Project project1 = getSimpleProject();
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
    public void testProjectUsersList(){
        //TODO
    }

    @Test
    public void testProjectUserListByEmail(){
        //TODO
    }
}
