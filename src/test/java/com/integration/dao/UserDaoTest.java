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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserDaoTest extends DomainTestBase {

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
        int size = userDao.list().size();
        User user = simpleUser();
        userDao.add(user);
        assertTrue(size<userDao.list().size());
    }

    @Test
    public void testUpdate(){
        User user = simpleUser();
        userDao.add(user);

        long userId = user.getId();
        user.setName("updated");
        userDao.update(user);

        assertEquals("updated", userDao.find(userId).getName());
    }

    @Test
    public void testFind(){
        User user = simpleUser();
        userDao.add(user);
        long userId = user.getId();
        assertEquals(user, userDao.find(userId));
    }

    @Test
    public void testList(){

        assertTrue(userDao.list().isEmpty());
        User user1 = simpleUser();

        List<User> userList = Arrays.asList(simpleUser(),
                new User("test-name-1", "test-login-1", "test-pass-1", user1.getRoleId()),
                new User("test-name-2", "test-login-2", "test-pass-2", user1.getRoleId()),
                new User("test-name-3", "test-login-3", "test-pass-3", user1.getRoleId())
        );

        for(User user:userList){
            userDao.add(user);
        }

        List<User> foundedList = userDao.list();

        assertEquals(4, foundedList.size());
        assertTrue(userList.containsAll(foundedList));
    }

    @Test
    public void testRemove(){
        User user = simpleUser();
        userDao.add(user);
        Long idUser = user.getId();
        userDao.remove(user);
        assertNull(userDao.find(idUser));
    }

    @Test
    public void testUsersProjectList(){
        User user = simpleUser();
        userDao.add(user);

        Project project  = new Project("test-1", "git-1");
        projectDao.add(project);

        Org org = new Org("login1","password1", "branch1", "type1", project );
        orgDao.add( org);
        org.setUserList(Arrays.asList(user));
        orgDao.update(org);

        List<Project> projectList = userDao.usersProjectList(user);
        assertEquals(1, projectList.size());
    }


    private User simpleUser(){
        Role role = new Role("test-role");
        roleDao.add(role);
        return new User("test-name", "test-login", "test-pass", role);

    }
}
