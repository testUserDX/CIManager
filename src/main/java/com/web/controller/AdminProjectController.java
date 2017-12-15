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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
    public String showData(@RequestParam(value = "projid" ,required = false) Long projid, Model model, HttpSession session){

        session.setAttribute("projid",projid);

        Project project = projectDao.findFullProject(projid);
        model.addAttribute("project",project);

//        List<Org> orgs = project.getOrgList();
//        model.addAttribute("projectOrgs",orgs);

        System.out.println(project.getOrgList().size() +"==============================================");
        System.out.println(project.getOrgList().get(0).getUserList().size() +"==============================================");


        List<User> users = projectDao.usersProjectListByProjectName(project.getName());
        model.addAttribute("projectUsers",users);
        model.addAttribute("org",new Org());

        return "projectmanagepage";
    }

    @RequestMapping(value = "/projectmanagepage",method = RequestMethod.POST)
    public void createOrg(@ModelAttribute("org") Org org){

    }
}
