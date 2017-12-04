package com.service.daoService.serviseDaoImpl;

import com.model.Branch;
import com.service.daoService.BranchDao;
import com.service.daoService.generalDaoService.HibernateDao;
import org.springframework.stereotype.Repository;

@Repository("branchDao")
public class BranchDaoImpl extends HibernateDao<Branch, Long> implements BranchDao {
}
