package com.service.daoService.serviseDaoImpl;

import com.model.Project;
import com.model.User;
import com.service.daoService.UserDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository("userDao")

public class UserDaoImpl extends HibernateDao<User,Long> implements UserDao{

    @Override
    public List<Project> usersProjectList(User user) {
//        String projectString ="select * from project where id in (SELECT org.project_id FROM org " +
//                "INNER JOIN user_has_org on org.id = user_has_org.Org_id" +
//                "where user_has_org.User_id=:id)";

        String projectString = "select p from Project p where p.id in (select o.projectId from Org  o join o.userList u where u.id=:id)";
        Query projectQuery = currentSession().createQuery( projectString);
        projectQuery.setParameter("id",user.getId());

        List<Project> projectList = projectQuery.list();

        return projectList;
    }
}
