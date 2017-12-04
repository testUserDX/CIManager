package com.service.daoService.serviseDaoImpl;

import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Org;
import org.springframework.stereotype.Repository;
import com.service.daoService.OrgDao;

@Repository("orgDao")
public class OrgDaoImpl extends HibernateDao<Org, Long> implements OrgDao {

}
