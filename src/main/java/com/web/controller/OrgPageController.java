package com.web.controller;

import com.model.Org;
import com.model.Project;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orgs")
public class OrgPageController {

    public static final String TITLE_ORG_PAGE = "Orgs";

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOrg(@PathVariable("id") Long id, Model model){
        Org org = orgDao.getFullOrg(id);
        model.addAttribute("org",org);
        List<User> users = userDao.list();
        Set<User> unassignedUsers = new HashSet();

        for (User user: users){
            if(!org.getUserList().contains(user)){
                unassignedUsers.add(user);
            }
        }
        model.addAttribute("unassigned", unassignedUsers);
        model.addAttribute("title", TITLE_ORG_PAGE);
        return "orgs/view";
    }


    @RequestMapping(value = "/{id}/users/del/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable("id") Long orgId, @PathVariable("userId") Long userId) {
        Org org = orgDao.find(orgId);

    }

    @RequestMapping(value = "/{id}/orgs/add{userId}", method = RequestMethod.POST)
    public String addEmployee(@PathVariable("id") long orgId, @PathVariable("userId") long userId) {

        Org org = orgDao.find(orgId);

//        Employee employee = employeeDao.find(employeeId);
//        Task task = taskDao.find(taskId);
//
//        task.addEmployee(employee);
//        taskDao.update(task);

        return "redirect:/orgs/" + userId;
    }







//    @RequestMapping(method = RequestMethod.POST)
//    public String addNewProject(@ModelAttribute("project") Project project, @ModelAttribute("org") Org org, HttpSession session) {
//        User user = userDao.getUserByEmil((String) session.getAttribute("userEmail"));
//        org.setUserList(Arrays.asList(user));
//        org.setProjectId(project);
//        orgDao.add(org);
//        return "redirect:/projects?list";
//    }
//
//    @RequestMapping(params = "edit", method = RequestMethod.GET)
//    public String editOrg(@ModelAttribute("org") Org org, HttpSession session) {
//        User user = userDao.getUserByEmil((String) session.getAttribute("userEmail"));
//        org.setUserList(Arrays.asList(user));
//        orgDao.add(org);
//        return "";
//    }
}
