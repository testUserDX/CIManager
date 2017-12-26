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
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Set;


@Controller
public class CommitPageController {

    public static final String TITLE_COMMIT_PAGE = "Commit";

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

    private String path;// = "C:\\Users\\new\\Desktop\\Новая папка";

    @RequestMapping(value = "/commitpage", params = "projid")
    public String commit(@RequestParam(value = "projid") Long idProject, Model model, HttpSession session) {

        String userEmail = (String) session.getAttribute("userEmail");
        if (!(userEmail == null)) {

            model.addAttribute("cmessage", new CommitMessage());
            session.setAttribute("projid", idProject);

            Project project = projectDao.find(idProject);
            path = project.getFolder();

            File gitSource = new File(path + "\\" + userEmail + idProject + "\\.git");
            boolean isRepoExist;
            isRepoExist = gitSource.exists();
            if (!isRepoExist) {
                userFlowService.cloneRemoteRopository(idProject, userEmail, project.getGitUrl(), path);
            }
            model.addAttribute("title", TITLE_COMMIT_PAGE);
            return "commitpage";
        } else {
            return "redirect: /loginpage";
        }
    }

    @RequestMapping(value = "/commitpage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getMessage(/*@ModelAttribute("cmessage") CommitMessage message, */Model model, HttpSession session, RequestEntity<String> requestEntity) {

        Long projid = (Long) session.getAttribute("projid");
        String userEmail = (String) session.getAttribute("userEmail");
        List<Org> userOrg = orgDao.getOrgByUserAndProject(projid, (String) session.getAttribute("userEmail"));


        Project project = projectDao.find(projid);

        path = project.getFolder();
        CredentialsProvider credentials = new UsernamePasswordCredentialsProvider(project.getGitLogin(), project.getGitPasword());
        boolean result = false;
        try {
            result = userFlowService.commitAll(requestEntity.getBody(), path + "\\" + userEmail + projid, userOrg.get(0).getBranchName(), credentials);
            if (result) {
                filesTools.removeGitFolder(path + "\\" + userEmail + projid);
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> responseEntity;
        if (result) {
            responseEntity = new ResponseEntity<>("true", HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("false", HttpStatus.OK);
        }

        System.out.println(requestEntity.getBody());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_PLAIN);

        return responseEntity;
    }


    @RequestMapping(value = "/commitpage", params = "action")
    @ResponseBody
    public ResponseEntity<?> refresh(@RequestParam("action") int action, HttpSession session, Model model) {
        if (action == 1) {
            Long projid = (Long) session.getAttribute("projid");
            String userEmail = (String) session.getAttribute("userEmail");

            gitService.addFiles(".", path + "\\" + userEmail + projid);
//            List<DiffEntry> result = gitService.getFilesInDiff(path+"\\"+userEmail+projid,"master");
//            for (DiffEntry entry : result){
//                System.out.println(entry.toString());
//            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Set<String> changes = gitService.getStatus(path + "\\" + userEmail + projid);
            StringBuilder responseText = new StringBuilder();
            for (String change : changes) {
                responseText.append("<li class=\"list-group-item\">\n" +
                        "                        <div class=\"checkbox\">\n" +
                        "                            <input type=\"checkbox\" id=\"checkbox\" />\n" +
                        "                            <label for=\"checkbox\">\n" +
                        change + "\n" +
                        "                            </label>\n" +
                        "                        </div>\n" +
                        "                    </li>");
            }
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.TEXT_HTML);


            return new ResponseEntity<>(responseText.toString(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.OK);

//        return "redirect: /commitpage?projid="+projid;
    }
}
