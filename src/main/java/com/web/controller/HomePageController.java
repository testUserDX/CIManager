package com.web.controller;

import com.model.Project;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/homepage")
    public String showProjectList(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        List<Project> projectList = userDao.usersProjectListByEmail(userEmail);
        model.addAttribute("userProjects", projectList);
        return "homepage";
    }
}
