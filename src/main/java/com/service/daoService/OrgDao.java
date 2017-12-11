package com.service.daoService;

import com.model.Org;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;

public interface OrgDao extends GenericDao<Org, Long> {
    public List<Org> getOrgByUserAndProject(Long projectId, String email);


}
