package com.web.controller;


import com.model.Project;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String showData(Model model, HttpSession session){
        List<Project> projects = projectDao.usersProjectListByEmail((String)session.getAttribute("userEmail"));
        model.addAttribute("projects",projects);
        return "projectmanagepage";
    }
}
