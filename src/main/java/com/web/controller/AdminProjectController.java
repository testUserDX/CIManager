package com.web.controller;


import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminProjectController {


    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserDao userDao;

    @Autowired
    OrgDao orgDao;


    @RequestMapping(value = "/projectmanagepage" , method = RequestMethod.GET)
    public String showData(@RequestParam(value = "projid" ,required = false) Long projid,
                           Model model, HttpSession session){

            session.setAttribute("projid",projid);

            Project project = projectDao.findFullProject(projid);
            model.addAttribute("project",project);

            List<Org> orgs = project.getOrgList();

            model.addAttribute("projectOrgs",orgs);

            System.out.println(project.getOrgList().size() +"==============================================");
//            if(project.getOrgList())
//            System.out.println(project.getOrgList().get(0).getUserList().size() +"==============================================");


            List<User> users = projectDao.usersProjectListByProjectName(project.getName());
            model.addAttribute("projectUsers",users);
            model.addAttribute("org",new Org());

        return "projectmanagepage";
    }


    @RequestMapping(value = "/projectmanagepage",method = RequestMethod.POST)
    public String createOrg(@ModelAttribute("org") Org org, HttpSession session){

        org.setProjectId(projectDao.find((Long)session.getAttribute("projid")));
        org.setUserList(Arrays.asList(userDao.getUserByEmil((String)session.getAttribute("userEmail"))));
        orgDao.add(org);
        return "redirect: /projectmanagepage?projid="+session.getAttribute("projid");


    }

    @RequestMapping(value="/projectmanagepage/{orgid}",method = RequestMethod.GET)
    public String deleteOrg(@PathVariable("orgid") Long orgid,HttpSession session){
        Project project = projectDao.findFullProject((Long)session.getAttribute("projid"));
        List<Org> orgs = project.getOrgList();
        orgDao.remove(orgDao.find(orgid));
        return "redirect: /projectmanagepage?projid="+session.getAttribute("projid");
    }


}
