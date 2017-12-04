package com.service.daoService.serviseDaoImpl;

import com.service.daoService.generalDaoService.HibernateDao;
import com.model.Project;
import org.springframework.stereotype.Repository;
import com.service.daoService.ProjectDao;

@Repository("projectDao")
public class ProjectDaoImpl extends HibernateDao<Project, Long> implements ProjectDao{

}
