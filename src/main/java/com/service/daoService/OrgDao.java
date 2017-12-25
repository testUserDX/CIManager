package com.service.daoService;

import com.model.Org;
import com.service.daoService.generalDaoService.GenericDao;

import java.util.List;

public interface OrgDao extends GenericDao<Org, Long> {
    public List<Org> getOrgByUserAndProject(Long projectId, String email);

    public Org getFullOrg(Long key);
//TODO clean code
    //public List<Org> getOrgListByProjectName(String projectName);

    //public List<Org> getOrgListOfUser(String username);

   // public Org getfullOrgWithUsers(Long key);
}
