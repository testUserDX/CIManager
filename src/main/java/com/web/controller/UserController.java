package com.web.controller;

import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.RoleDao;
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

@Controller
@RequestMapping("users")
public class UserController {

    public static final String TITLE_NEW_USER = "New User";

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public String createNewProject(Model model) {
        model.addAttribute("title", TITLE_NEW_USER);
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleDao.list());
        return "users/new";
    }

    @RequestMapping("/logout")
    public String logout(Model model, HttpSession session) {
        session.invalidate();
        return "loginpage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewProject(@ModelAttribute("user") User user) {
        //TODO delete this!!!
        user.setLogin("adss");
        userDao.add(user);
        return "redirect:/users?list";
    }
}
