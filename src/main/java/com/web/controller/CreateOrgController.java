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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/createOrg")
public class CreateOrgController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

//    @RequestMapping(method = RequestMethod.GET)
//    public String createAndAddOrgToProject(Model model) {
//        model.addAttribute("projects", projectDao.getAllProjects());
//        model.addAttribute("org", new Org());
//        return "asd";
//    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewProject(@ModelAttribute("project") Project project, @ModelAttribute("org") Org org, HttpSession session) {
        User user = userDao.getUserByEmil((String) session.getAttribute("userEmail"));
        org.setUserList(Arrays.asList(user));
        org.setProjectId(project);
        orgDao.add(org);
        return "redirect:/projects?list";
    }
}
