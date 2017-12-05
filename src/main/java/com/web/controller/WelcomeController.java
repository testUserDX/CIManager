package com.web.controller;


import com.data.UserCredentials;
import com.service.daoService.UserDao;
import com.web.helper.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WelcomeController {

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLogin() {
        return "redirect:/loginpage";
    }

    @RequestMapping(value = {"/loginpage"}, method = RequestMethod.GET)
    public ModelAndView showMenu() {
        return new ModelAndView("loginpage", "credentials", new UserCredentials());
    }


    @RequestMapping(value = "/loginpage", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute UserCredentials userCredentials, HttpServletRequest request) {
        if(userDao.verifyUserByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())){
            request.getSession().setAttribute("userEmail", userCredentials.getEmail());
            return "redirect:/homepage";
        }
        return "redirect:/";
    }

    @PostConstruct
    public void generateTestData(){
        dataGenerator.genareteDomain();
    }

}



