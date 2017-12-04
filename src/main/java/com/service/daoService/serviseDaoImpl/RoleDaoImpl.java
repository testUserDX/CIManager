package com.service.daoService.serviseDaoImpl;

import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Role;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.service.daoService.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends HibernateDao<Role, Integer> implements RoleDao{

    @Override
    public boolean removeRole(Role role) {
        String queryString = "from User where roleId.id=:id";
        Query roleQuery = currentSession().createQuery(queryString);
        roleQuery.setParameter("id",role.getId());
        if(!roleQuery.list().isEmpty()){
            return false;
        }
        remove(role);
        return true;
    }
}
