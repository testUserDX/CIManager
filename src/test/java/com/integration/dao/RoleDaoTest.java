package com.integration.dao;

import com.DomainTestBase;
import com.model.Role;
import com.model.User;
import com.service.daoService.RoleDao;
import com.service.daoService.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class RoleDaoTest extends DomainTestBase{

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;


    @Test
    public void testAdd(){
        int size = roleDao.list().size();
        roleDao.add(simpleRole());
        assertTrue(size<roleDao.list().size());
    }

    @Test
    public void testUpdate(){
        Role role = simpleRole();
        roleDao.add(role);
        int id = role.getId();

        role.setRoleName("updated");
        roleDao.update(role);
        assertEquals("updated", roleDao.find(id).getRoleName());
    }

    @Test
    public void testFind(){
        Role role = simpleRole();
        roleDao.add(role);
        assertEquals(role, roleDao.find(role.getId()));
    }

    @Test
    public void testList(){
        assertTrue(roleDao.list().isEmpty());
        List<Role> roleList = Arrays.asList(
                new Role("test1"),
                new Role("test2"),
                new Role("test3")
        );
        for (Role roleItem:roleList){
            roleDao.add(roleItem);
        }
        List<Role> foundRoleList = roleDao.list();
        assertEquals(3, roleDao.list().size());
        assertTrue(roleList.containsAll(foundRoleList));
    }

    @Test
    public void testRemove(){
        Role role = simpleRole();
        roleDao.add(role);
        Integer idRole = role.getId();
        roleDao.remove(role);
        assertNull(roleDao.find(idRole));
    }

    @Test
    public void testRemoveRole(){
        Role role = simpleRole();
        roleDao.add(role);
        User user = new User("test","login", "pass",role);
        userDao.add(user);
        assertFalse(roleDao.removeRole(role));

        userDao.remove(user);

        assertTrue(roleDao.removeRole(role));
        assertNull(roleDao.find(role.getId()));
    }

    private Role simpleRole(){
        Role role = new Role();
        role.setRoleName("Test");
        return role;
    }
}
