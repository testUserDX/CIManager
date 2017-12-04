package com.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;

@Controller
@RequestMapping({"/loginpage","/" })
public class WelcomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMenu() {
        return new ModelAndView("loginpage");
    }

    @RequestMapping("/homepage")
    public String showHomepage(){
        return "homepage";
    }
}



