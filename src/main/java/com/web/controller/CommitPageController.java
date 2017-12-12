package com.web.controller;

import com.data.CommitMessage;
import com.model.Org;
import com.model.Project;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.gitServise.GitService;
import com.service.userService.UserFlowService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Set;


@Controller
public class CommitPageController {

    @Autowired
    OrgDao orgDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserFlowService userFlowService;

    @Autowired
    GitService gitService;

    private String path = "C:\\Users\\new\\Desktop\\Новая папка\\tmp";

    @RequestMapping(value = "/commitpage", method = RequestMethod.GET)
    public String commit(@RequestParam(value = "projid",required = false) Long projid, Model model,  HttpSession session){

        gitService.addFiles(".",path);
        Set<String> changes = gitService.getStatus(path);

        model.addAttribute("changes",changes);
        model.addAttribute("cmessage",new CommitMessage());
        session.setAttribute("projid",projid);

        Project project = projectDao.find(projid);


        File gitSource = new File(path+"\\.git");
        boolean isRepoExist;
        isRepoExist = gitSource.exists();
        if(!isRepoExist){
            userFlowService.cloneRemoteRopository(project.getGitUrl(),"C:\\Users\\new\\Desktop\\Новая папка");
        }
        return "commitpage";
    }

    @RequestMapping(value = "/commitpage", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String getMessage(@ModelAttribute("cmessage") CommitMessage message, Model model, HttpSession session){

        Long projid = (Long)session.getAttribute("projid");

        List<Org> userOrg =  orgDao.getOrgByUserAndProject(projid,(String)session.getAttribute("userEmail"));

        Project project = projectDao.find(projid);
        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider(project.getGitLogin(),project.getGitPasword());


        try {
            userFlowService.commitAll(message.getMessage(),path,userOrg.get(0).getBranchName(),credentials);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return "redirect: /commitpage?projid="+session.getAttribute("projid");
    }

//    @RequestMapping(value = "/commitpage/refresh")
//    public String refresh(HttpSession session){
//        gitService.addFiles(".",path);
//        return "redirect: /commitpage?projid="+session.getAttribute("projid");
//    }
}
