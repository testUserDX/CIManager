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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/orgs")
public class OrgController {

    public static final String TITLE_ORG_PAGE = "Orgs";

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private OrgDao orgDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOrg(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("orgs/view");
        Org org = orgDao.getFullOrg(id);

        List<User> assignedUsers = userDao.getOrgUserWithoutAdmin(org);
        List<User> allUsers = userDao.getAllUsersWithoutAdmins();
        Set<User> unassignedUsers = new HashSet();

        for (User user : allUsers) {
            if (!assignedUsers.contains(user)) {
                unassignedUsers.add(user);
            }
        }
        unassignedUsers.add(new User(-1L, "-----"));
        unassignedUsers.add(new User(-2L, "no users"));

        if(assignedUsers.isEmpty()){
            assignedUsers.add(new User(null, "no user"));
            org.setUserList(assignedUsers);
        }


        modelAndView.addObject("org", org);
        modelAndView.addObject("assigned", assignedUsers);
        modelAndView.addObject("unassigned", unassignedUsers);
        modelAndView.addObject("title", TITLE_ORG_PAGE);
        return modelAndView;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateOrg(@PathVariable("id") Long id, @ModelAttribute Org org , HttpSession session) {
        User orgAdmin = userDao.getOrgAdmin(new Org(id));
        User selectedUser = org.getUserList().get(0);
        List<User> newList = new ArrayList<>();
        newList.add(orgAdmin);
        if (selectedUser.getId() == -1L) {
            List<User> userList = userDao.getOrgUserWithoutAdmin(new Org(id));
            if (!userList.isEmpty()) {
                newList.add(userList.get(0));
            }
        } else if (selectedUser.getId() != -1L && selectedUser.getId() != -2L) {
            newList.add(selectedUser);
        }

        org.setUserList(newList);

        orgDao.update(org);
        return "redirect:/projects/view?projid=" + session.getAttribute("projid");
    }


//    @RequestMapping(value = "/{id}/users/del/{userId}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeEmployee(@PathVariable("id") Long orgId, @PathVariable("userId") Long userId) {
//        Org org = orgDao.getFullOrg(orgId);
//        User user = userDao.find(userId);
//        List<User> userList = org.getUserList();
//        userList.remove(user);
//        org.setUserList(userList);
//        orgDao.update(org);
//    }

//    @RequestMapping(value = "/{id}/users/add/{userId}", method = RequestMethod.POST)
//    public String addEmployee(@PathVariable("id") long orgId, @PathVariable("userId") Long userId) {
//        Org org = orgDao.getFullOrg(orgId);
//        if (userId != null) {
//            User user = userDao.find(userId);
//            List<User> userList = org.getUserList();
//            userList.add(user);
//            org.setUserList(userList);
//            orgDao.update(org);
//        }
//
//        return "redirect:/orgs/" + orgId;
//    }


    @RequestMapping(params = "delete", method = RequestMethod.GET)
    public String deleteOrg(@RequestParam(value = "orgid", required = false)  Long orgid, HttpSession session) {
       // Project project = projectDao.findFullProject((Long) session.getAttribute("projid"));
      //  List<Org> orgs = project.getOrgList();
        orgDao.remove(orgDao.find(orgid));
        return "redirect:/projects/view?projid=" + session.getAttribute("projid");
    }

}
