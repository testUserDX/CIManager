package com.web.controller;

import com.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ErrorPageController {
    @RequestMapping("/error")
    public String showProjectList(Model model, HttpSession session) {
        model.addAttribute("error", "Some error");
        return "errorpage";
    }
}
