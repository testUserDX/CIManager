package com.web.controller;


import com.data.UserCredentials;
import com.service.RetriveMetadataManager;
import com.service.daoService.UserDao;
import com.web.helper.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLogin() {
        return "redirect:/projects/list";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String verifyUser1(/*@ModelAttribute UserCredentials userCredentials, HttpSession session*/) {
//        if (userDao.verifyUserByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())) {
//            session.setAttribute(ATR_EMAIL, userCredentials.getEmail());
//            session.setAttribute("role", userCredentials.getRole());
//        }
        return "/loginpage";
    }

    @PostConstruct
    public void generateTestData() {
        //TODO remove method in production release
        dataGenerator.genareteDomain();
    }

}



