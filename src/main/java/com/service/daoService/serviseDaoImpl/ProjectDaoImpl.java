package com.service.daoService.serviseDaoImpl;

import com.model.Project;
import com.model.User;
import com.service.daoService.ProjectDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("projectDao")
public class ProjectDaoImpl extends HibernateDao<Project, Long> implements ProjectDao {
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
    public List<Project> usersProjectListByEmail(String email) {
        String projectString = "select p from Project p where p.id in (" +
                "select o.projectId from Org  o join o.userList u where u.id in (SELECT u.id from User where u.email =:email))";
        Query projectQuery = currentSession().createQuery(projectString);
        projectQuery.setParameter("email", email);
        List<Project> projectList = projectQuery.list();
        return projectList;
    }

    @Override
    public List<User> usersProjectListByProjectName(String projectName) {
        Query query = currentSession().createQuery("select u from User u join u.orgList o where o.id in" +
                " (select o from Org o where o.projectId in (select p from Project p where p.name =:projectName))");
        query.setParameter("projectName", projectName);
        List userList = query.list();
        return userList;
    }

    @Override
    public List<Project> getAllProjects() {
        String projectString = "select p from Project p";
        Query projectQuery = currentSession().createQuery(projectString);
        List<Project> projectList = projectQuery.list();
        return projectList;
    }
}
