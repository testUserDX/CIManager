package com.web.controller;


import com.data.UserCredentials;
import com.service.daoService.UserDao;
import com.web.helper.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {
    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_USER = "user";
    private static final String ATR_EMAIL = "userEmail";

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLogin(HttpSession session) {
        if (session.getAttribute(ATR_EMAIL) == null) {
            return "redirect:/loginpage";
        } else {
            switch ((String) session.getAttribute("role")) {
                case ROLE_ADMIN:
                    return "redirect:/homepage";
                case ROLE_USER:
                    return "redirect:/homepage";
                default:
                    return "redirect:/";
            }
        }
    }

    @RequestMapping(value = {"/loginpage"}, method = RequestMethod.GET)
    public ModelAndView showMenu() {
        return new ModelAndView("loginpage", "credentials", /*new UserCredentials()*/ createTestUserCreds());
    }

    @RequestMapping(value = "/loginpage", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute UserCredentials userCredentials, HttpSession session) {
        if (userDao.verifyUserByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())) {
            session.setAttribute("userEmail", userCredentials.getEmail());
            session.setAttribute("role", userCredentials.getRole());
        }
        return "redirect:/";
    }

    private UserCredentials createTestUserCreds() {
        //TODO delete this method before production realise
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail("111@111.com");
        userCredentials.setRole("admin");
        userCredentials.setPassword("111");

        return userCredentials;
    }

 //   @PostConstruct
    public void generateTestData() {
        dataGenerator.genareteDomain();
    }

}



