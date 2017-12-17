package com.web.controller;

import com.model.Project;
import com.service.daoService.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    private ProjectDao projectDao;

    @RequestMapping("/homepage")
    public String showProjectList(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        List<Project> projectList = projectDao.usersProjectListByEmail(userEmail);
        model.addAttribute("userProjects", projectList);
        return "homepage";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "loginpage";
    }
}
