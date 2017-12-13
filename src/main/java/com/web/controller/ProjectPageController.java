package com.web.controller;

import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("projects")
public class ProjectPageController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(params = "list", method = RequestMethod.GET)
    public ModelAndView projectList(HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        return new ModelAndView("homepage", "userProjects", projectDao.usersProjectListByEmail(userEmail));
    }

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public ModelAndView createNewProject() {
        Org org = new Org();
        Project project = new Project();
        project.setOrgList(Arrays.asList(org));
        return new ModelAndView("projects/newProjectPage", "project", project);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewProject(@ModelAttribute("project") Project project, HttpSession session) {
        Org org = project.getOrgList().get(0);
        Project project1 = project;
        project1.setOrgList(null);

        projectDao.add(project1);
        User user = userDao.getUserByEmil((String) session.getAttribute("userEmail"));
        org.setUserList(Arrays.asList(user));
        org.setProjectId(project1);
         orgDao.add(org);
        return "redirect:/projects?list";
    }
}
