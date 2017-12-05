package com.service.daoService.serviseDaoImpl;

import com.model.Project;
import com.model.User;
import com.service.daoService.UserDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")

public class UserDaoImpl extends HibernateDao<User, Long> implements UserDao {

    @Override
    public List<Project> usersProjectList(User user) {
        String projectString = "select p from Project p where p.id in (" +
                "select o.projectId from Org  o join o.userList u where u.id=:id)";
        Query projectQuery = currentSession().createQuery(projectString);
        projectQuery.setParameter("id", user.getId());
        List<Project> projectList = projectQuery.list();
        return projectList;
    }

    @Override
    public boolean verifyUserByEmailAndPassword(String email, String passord) {
        Query query = currentSession().createQuery("select u from User u where  u.email=:email and u.password =:password ");
        query.setParameter("email", email);
        query.setParameter("password", passord);
        List userList = query.list();
        if(userList.isEmpty()) {
            return false;
        }
        else return true;
    }
}
