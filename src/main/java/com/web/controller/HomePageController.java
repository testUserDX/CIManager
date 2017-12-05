package com.web.controller;

import com.data.UserCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/homepage")
    public String showProjectList(Model model) {
//        UserCredentials userCredentials = (UserCredentials) model.asMap().get("userCred");

        System.out.println("*****************" +  model.asMap().get("userCred"));
        return "homepage";
    }


}
