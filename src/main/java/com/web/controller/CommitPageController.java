package com.web.controller;

import com.data.CommitMessage;
import com.model.Project;
import com.service.daoService.ProjectDao;
import com.service.userService.UserFlowService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class CommitPageController {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserFlowService userFlowService;


    @RequestMapping(value = "/commitpage", method = RequestMethod.GET)
    public String commit(@RequestParam(value = "projid",required = false) Long projid, Model model,  HttpSession session){

        model.addAttribute("cmessage",new CommitMessage());
        session.setAttribute("projid",projid);


        Project project = projectDao.find(projid);
        userFlowService.cloneRemoteRopository(project.getGitUrl(),"C:\\Users\\new\\Desktop\\Новая папка");
        return "commitpage";
    }

    @RequestMapping(value = "/commitpage", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getMessage(@ModelAttribute("cmessage") CommitMessage message, Model model, HttpSession session){

        Long projid = (Long)session.getAttribute("projid");
        System.out.println(projid);
        Project project = projectDao.find(projid);
        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider(project.getGitLogin(),project.getGitPasword());
        try {
            userFlowService.commitAll(message.getMessage(),userFlowService.getPath(),"master",credentials);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        System.out.println(message.getMessage());

    }
}
