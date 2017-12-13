package com.web.controller;

import com.data.CommitMessage;
import com.model.Org;
import com.model.Project;
import com.service.FilesTools;
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

    @Autowired
    FilesTools filesTools;

    private String path = "C:\\Users\\new\\Desktop\\Новая папка";

    @RequestMapping(value = "/commitpage", method = RequestMethod.GET)
    public String commit(@RequestParam(value = "projid",required = true) Long projid, Model model,  HttpSession session){

        String userEmail = (String)session.getAttribute("userEmail");

        if(!(userEmail == null)){

            model.addAttribute("cmessage",new CommitMessage());
            session.setAttribute("projid",projid);

            Project project = projectDao.find(projid);

            File gitSource = new File(path+"\\"+userEmail+projid+"\\.git");
            boolean isRepoExist;
            isRepoExist = gitSource.exists();
            if(!isRepoExist){
                userFlowService.cloneRemoteRopository(projid,userEmail,project.getGitUrl(),"C:\\Users\\new\\Desktop\\Новая папка");
            }
            Set<String> changes = gitService.getStatus(path+"\\"+userEmail+projid);

            model.addAttribute("changes",changes);
            return "commitpage";
        }else{
            return "redirect: /loginpage";
        }
    }

    @RequestMapping(value = "/commitpage", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String getMessage(@ModelAttribute("cmessage") CommitMessage message, Model model, HttpSession session){

        Long projid = (Long)session.getAttribute("projid");
        String userEmail = (String)session.getAttribute("userEmail");
        List<Org> userOrg =  orgDao.getOrgByUserAndProject(projid,(String)session.getAttribute("userEmail"));

        Project project = projectDao.find(projid);
        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider(project.getGitLogin(),project.getGitPasword());
        try {
            boolean result = userFlowService.commitAll(message.getMessage(),path+"\\"+userEmail+projid,userOrg.get(0).getBranchName(),credentials);
            if(result){
                filesTools.removeGitFolder(path+"\\"+userEmail+projid);
            }

        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return "redirect: /commitpage?projid="+session.getAttribute("projid");
    }

    @RequestMapping(value = "/commitpage/refresh")
    public String refresh(HttpSession session){
        Long projid = (Long)session.getAttribute("projid");
        String userEmail = (String)session.getAttribute("userEmail");
        gitService.addFiles(".",path+"\\"+userEmail+projid);
        return "redirect: /commitpage?projid="+projid;
    }
}
