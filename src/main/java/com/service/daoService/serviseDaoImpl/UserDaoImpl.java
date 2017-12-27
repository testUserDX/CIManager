package com.service.daoService.serviseDaoImpl;

import com.model.Org;
import com.model.User;
import com.service.daoService.UserDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")

public class UserDaoImpl extends HibernateDao<User, Long> implements UserDao {

    @Override
    public boolean verifyUserByEmailAndPassword(String email, String password) {
        Query query = currentSession().createQuery("select u from User u where  u.email=:email and u.password =:password_encoded");
        query.setParameter("email", email);
        query.setParameter("password_encoded", password);
        List userList = query.list();
        if (userList.isEmpty()) {
            return false;
        } else return true;
    }

    @Override
    public User getUserByEmil(String email) {
        Query query = currentSession().createQuery("select u from User u where  u.email=:email");
        query.setParameter("email", email);
        List<User> userList = query.list();
        return userList.get(0);
    }

    @Override
    public List<User> getAllUsersWithoutAdmins() {
        Query query = currentSession().createQuery("select u from User u where  u.roleId.roleName!='admin'");
        List<User> userList = query.list();
        return userList;
    }

    @Override
    public List<User> getOrgUserWithoutAdmin(Org org) {
        String userQueryString = "select u from User u join u.orgList o where  u.roleId.roleName != 'admin' and o.id =:idOrg";
        Query userQuery = currentSession().createQuery(userQueryString);
        userQuery.setParameter("idOrg", org.getId());
       List<User> userList = userQuery.list();
        return userList;
    }

    @Override
    public User getOrgAdmin(Org org) {
        String userQueryString = "select u from User u join u.orgList o where  u.roleId.roleName = 'admin' and o.id =:idOrg";
        Query userQuery = currentSession().createQuery(userQueryString);
        userQuery.setParameter("idOrg", org.getId());
        List<User> userList = userQuery.list();

        return userList.get(0);
    }
}
