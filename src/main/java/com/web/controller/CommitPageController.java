package com.web.controller;

import com.data.CommitMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommitPageController {

    @RequestMapping(value = "/commitpage", method = RequestMethod.GET)
    public String commit(Model model){
        model.addAttribute("cmessage",new CommitMessage());
        return "commitpage";
    }

    @RequestMapping(value = "/commitpage", method = RequestMethod.POST)
    public String getMessage(@ModelAttribute("cmessage") CommitMessage message){
        System.out.println(message.getMessage());
        return "redirect: /commitpage";
    }
}
