package com.web.controller;

import com.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class ErrorPageController {

    public static final String TITLE_ERROR = "Error";

    @RequestMapping("/error")
    public String showError(Model model, String message) {
        if (message == null || message == "") {
            model.addAttribute("error", "Unknown error");
        } else {
            model.addAttribute("error", message);
        }
        model.addAttribute("title", TITLE_ERROR);
        return "errors/errorpage";
    }

    @RequestMapping(value = "errors/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("/errors/403");
        return model;

    }
}
