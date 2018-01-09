package com.web.controller;

import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.FilesTools;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import com.service.gitServise.GitService;
import com.service.userService.UserFlowService;
import org.omg.CORBA.NO_RESPONSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("projects")
public class ProjectController {
    public static final String MASTER_BRANCH ="master";
    public static final String TITLE_NEW_PROJECT = "New Project";
    public static final String TITLE_PROJECT_LIST = "Projects list";
    public static final String TITLE_PROJECT_MANAGER = "Project Manager";

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    UserFlowService userFlowService;

    @Autowired
    GitService gitService;

    @Autowired
    FilesTools filesTools;

    @RequestMapping(value = "/list"/*,params = "list"*/, method = RequestMethod.GET)
    public ModelAndView projectList(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = auth.getAuthorities().stream().map(r-> r.getAuthority()).collect(Collectors.toSet());

        for(String role : roles){
            session.setAttribute("role",role);
        }

        String email = auth.getName();
        session.setAttribute("userEmail",email);
        String userEmail = (String) session.getAttribute("userEmail");
        ModelAndView modelAndView = new ModelAndView("projects/list");
        modelAndView.addObject("userProjects", projectDao.usersProjectListByEmail(userEmail));
        modelAndView.addObject("title", TITLE_PROJECT_LIST);


        return modelAndView;
    }

    @RequestMapping(/*params = "new"*/value = "/new", method = RequestMethod.GET)
    public ModelAndView createNewProject(Model model) {
        Org org = new Org();
        Project project = new Project();
        project.setOrgList(Arrays.asList(org));
        model.addAttribute("title", TITLE_NEW_PROJECT);
        return new ModelAndView("projects/new", "project", project);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addNewProject(@ModelAttribute("project") Project project, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");

        if(userEmail.isEmpty() || userEmail==null){
            return "redirect: /error";
        }

        Org org = project.getOrgList().get(0);
        Project project1 = project;
        project1.setOrgList(null);
        project.setFolder("C:/"+project.getName());

        projectDao.add(project1);
        User user = userDao.getUserByEmil(userEmail);
        org.setUserList(Arrays.asList(user));
        org.setProjectId(project1);
        org.setBranchName(MASTER_BRANCH);
        org.setIsProduction(true);
        orgDao.add(org);
        return "redirect:/projects/list";
    }

    @RequestMapping(/*params = "view",*/value = "/view", method = RequestMethod.GET)
    public String showData(@RequestParam(value = "projid", required = false) Long projid, Model model, HttpSession session) {
        session.setAttribute("projid", projid);
        Project project = projectDao.findFullProject(projid);
        model.addAttribute("project", project);
        List<Org> orgs = project.getOrgList();
        model.addAttribute("projectOrgs", orgs);
        List<User> users = projectDao.usersProjectListByProjectName(project.getName());
        model.addAttribute("projectUsers", users);
        model.addAttribute("org", new Org());
        model.addAttribute("title", TITLE_PROJECT_MANAGER);
        return "/projects/view";
    }

    @RequestMapping(/*params = "add_org"*/value = "/add_org", method = RequestMethod.POST)
    public String addNewOrgPromProject(@ModelAttribute("org") Org org, HttpSession session) {
        org.setProjectId(projectDao.find((Long) session.getAttribute("projid")));
        org.setUserList(Arrays.asList(userDao.getUserByEmil((String) session.getAttribute("userEmail"))));
        orgDao.add(org);
        return "redirect:/projects/view?projid=" + session.getAttribute("projid");
    }

    @RequestMapping(value = "/clone")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void asyncCloneRepo(@RequestParam(value = "projid") Long idProject, Model model, HttpSession session){
        session.setAttribute("projid", idProject);
        String userEmail = (String) session.getAttribute("userEmail");

        Project project = projectDao.find(idProject);
        String path = project.getFolder();

        File gitSource = new File(path + "\\" + userEmail + idProject + "\\.git");
        boolean isRepoExist;
        isRepoExist = gitSource.exists();
        if(isRepoExist){
            filesTools.removeGitFolder(path + "\\" + userEmail + idProject);
        }
        userFlowService.cloneRemoteRopository(idProject, userEmail, project.getGitUrl(), path);

    }
}
