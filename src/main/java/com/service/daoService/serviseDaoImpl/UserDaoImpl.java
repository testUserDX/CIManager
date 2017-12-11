package com.service.daoService.serviseDaoImpl;

import com.model.User;
import com.service.daoService.UserDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")

public class UserDaoImpl extends HibernateDao<User, Long> implements UserDao {

    @Override
    public boolean verifyUserByEmailAndPassword(String email, String passord) {
        Query query = currentSession().createQuery("select u from User u where  u.email=:email and u.password =:password ");
        query.setParameter("email", email);
        query.setParameter("password", passord);
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
}
