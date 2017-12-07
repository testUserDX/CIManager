package com.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommitPageController {

    @RequestMapping("/commitpage")
    public String commit(){
        return "commitpage";
    }
}
