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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.swing.text.PasswordView;
import java.util.Arrays;

@Controller
@RequestMapping("users")
public class UserController {

    public static final String TITLE_NEW_USER = "New User";
    public static final String TITLE_USERS_LIST = "Users list";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public String createNewUser(Model model) {
        model.addAttribute("title", TITLE_NEW_USER);
        model.addAttribute("user", new User());
        model.addAttribute("roleList", roleDao.list());
        return "users/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return "redirect:/users?list";
    }

    @RequestMapping(params = "list", method = RequestMethod.GET)
    public ModelAndView projectList() {
        ModelAndView modelAndView = new ModelAndView("users/list");
        modelAndView.addObject("users", userDao.list());
        modelAndView.addObject("title", TITLE_USERS_LIST);
        return modelAndView;
    }

    @RequestMapping(params = "edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value = "userId") Long userId, Model model) {
        model.addAttribute("title", TITLE_NEW_USER);
        model.addAttribute("user", userDao.getUserById(userId));
        model.addAttribute("roleList", roleDao.list());
        return "users/edit";
    }

    @RequestMapping(params = "edit", method = RequestMethod.POST)
    public String postEditUser(@ModelAttribute("user") User user, @RequestParam(value = "userId") Long userId) {
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
        return "redirect:/users?list";
    }

    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "userId") Long userId) {
        userDao.remove(userDao.getUserById(userId));
        return "redirect:/users?list";
    }
}
