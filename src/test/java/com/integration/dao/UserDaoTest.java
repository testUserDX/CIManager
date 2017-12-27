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

    @Test
    public void testAdd(){
        int size = userDao.list().size();
        User user = getUser();
        userDao.add(user);
        assertTrue(size<userDao.list().size());
    }

    @Test
    public void testUpdate(){
        User user = getUser();
        userDao.add(user);
        long userId = user.getId();
        user.setName("updated");
        userDao.update(user);
        assertEquals("updated", userDao.find(userId).getName());
    }

    @Test
    public void testFind(){
        User user = getUser();
        userDao.add(user);
        long userId = user.getId();
        assertEquals(user, userDao.find(userId));
    }

    @Test
    public void testList(){
        assertTrue(userDao.list().isEmpty());
        User user1 = getUser();

        List<User> userList = Arrays.asList(getUser(),
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
        User user = getUser();
        userDao.add(user);
        Long idUser = user.getId();
        userDao.remove(user);
        assertNull(userDao.find(idUser));
    }

    @Test
    public void testVerifyUserByEmailAndPass(){
        User user = getUser();
        user.setEmail("test@test.com");
        userDao.add(user);
        assertFalse(userDao.verifyUserByEmailAndPassword("fake@test.com","test-pass" ));
        assertFalse(userDao.verifyUserByEmailAndPassword("test@test.com","fake-test-pass" ));
    }
}
