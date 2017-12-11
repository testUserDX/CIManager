package com.service.daoService.serviseDaoImpl;

import com.model.Project;
import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Org;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.service.daoService.OrgDao;

import java.util.List;

@Repository("orgDao")
public class OrgDaoImpl extends HibernateDao<Org, Long> implements OrgDao {

    @Override
    public List<Org> getOrgByUserAndProject(Long projectId, String email) {
        String projectString = "from Org o join o.userList u" +
                " where o.projectId=:projectId and u.id in (SELECT u.id from User where u.email =:email)";

        Query projectQuery = currentSession().createQuery(projectString);
        projectQuery.setParameter("email", email);
        projectQuery.setParameter("projectId", new Project(projectId));

        List<Org> orgsList = projectQuery.list();
        return orgsList;
    }

    @Override
    public List<Org> getOrgListByProjectName(String projectName) {
        String orgString = "select o from Org o where o.projectId in (select p from Project p where name =:projectName)";
        Query orgQuery = currentSession().createQuery(orgString);
        orgQuery.setParameter("projectName", projectName);
        List<Org> orgList = orgQuery.list();
        return orgList;
    }

    @Override
    public List<Org> getOrgListOfUser(String username) {
        String orgString = "select o from Org o join o.userList u " +
                "where u.id in (SELECT u.id from User where u.name =:username)";
        Query orgQuery = currentSession().createQuery(orgString);
        orgQuery.setParameter("username", username);
        List<Org> orgList = orgQuery.list();
        return orgList;
    }
}
