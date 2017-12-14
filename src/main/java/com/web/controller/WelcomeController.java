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
import javax.servlet.http.HttpSession;

@Controller
public class WelcomeController {

    @Autowired
    private DataGenerator dataGenerator;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectToLogin(HttpSession session) {
        if(session.getAttribute("userEmail") == null){
            return "redirect:/loginpage";
        }else if(session.getAttribute("role") == "admin"){
            return "redirect:/adminprojectlist";
        }else if(session.getAttribute("role") == "user"){
            return "redirect:/homepage";
        }
    }

    @RequestMapping(value = {"/loginpage"}, method = RequestMethod.GET)
    public ModelAndView showMenu() {
        return new ModelAndView("loginpage", "credentials", /*new UserCredentials()*/ createTestUserCreds());
    }

    @RequestMapping(value = "/loginpage", method = RequestMethod.POST)
    public String verifyUser(@ModelAttribute UserCredentials userCredentials, HttpServletRequest request) {
        if(userDao.verifyUserByEmailAndPassword(userCredentials.getEmail(), userCredentials.getPassword())){
            request.getSession().setAttribute("userEmail", userCredentials.getEmail());
            request.getSession().setAttribute("role" ,userCredentials.getRole());
            return "redirect:/";
        }
        return "redirect:/";
    }

    private UserCredentials createTestUserCreds(){
        //TODO delete this method before production realise
        UserCredentials  userCredentials = new UserCredentials();
        userCredentials.setEmail("111@111.com");
        userCredentials.setPassword("111");
        userCredentials.setRole("admin");
        return userCredentials;
    }

    @PostConstruct
    public void generateTestData(){
        dataGenerator.genareteDomain();
    }

}



