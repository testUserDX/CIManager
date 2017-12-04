package com.service.daoService.serviseDaoImpl;

import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Role;
import org.springframework.stereotype.Repository;
import com.service.daoService.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends HibernateDao<Role, Integer> implements RoleDao{

}
