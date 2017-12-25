package com.service.daoService.serviseDaoImpl;

import com.model.Project;
import com.model.User;
import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Org;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.service.daoService.OrgDao;

import java.util.List;

@Repository("orgDao")
public class OrgDaoImpl extends HibernateDao<Org, Long> implements OrgDao {

    @Override
    public List<Org> getOrgByUserAndProject(Long projectId, String email) {
        String projectString = "select o from Org o join o.userList u" +
                " where o.projectId=:projectId and u.id in (SELECT u.id from User where u.email =:email)";
        Query projectQuery = currentSession().createQuery(projectString);
        projectQuery.setParameter("email", email);
        projectQuery.setParameter("projectId", new Project(projectId));

        List<Org> orgsList = projectQuery.list();
        return orgsList;
    }

    @Override
    public Org getFullOrg(Long key) {
        Org org = find(key);
        Hibernate.initialize(org.getUserList());
        return org;
    }

    //todo clean code
//    @Override
//    public List<Org> getOrgListByProjectName(String projectName) {
//        String orgString = "select o from Org o where o.projectId in (select p from Project p where name =:projectName)";
//        Query orgQuery = currentSession().createQuery(orgString);
//        orgQuery.setParameter("projectName", projectName);
//        List<Org> orgList = orgQuery.list();
//        return orgList;
//    }

//    @Override
//    public List<Org> getOrgListOfUser(String username) {
//        String orgString = "select o from Org o join o.userList u " +
//                "where u.id in (SELECT u.id from User where u.name =:username)";
//        Query orgQuery = currentSession().createQuery(orgString);
//        orgQuery.setParameter("username", username);
//        List<Org> orgList = orgQuery.list();
//        return orgList;
//    }


//    @Override
//    public Org getfullOrgWithUsers(Long key) {
//        Org org = find(key);
//        String userQueryString = "select u from User u join u.orgList o where  u.roleId != (select r from Role r where r.roleName='admin') and o.id =:idOrg ";
//        Query userQuery = currentSession().createQuery(userQueryString);
//        userQuery.setParameter("idOrg", key);
//        List<User> userList = userQuery.list();
//        org.setUserList(userList);
//        return  org;
//    }
}
