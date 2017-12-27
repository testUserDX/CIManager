package com.web.helper;

import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.RoleDao;
import com.service.daoService.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class DataGenerator {
    @Autowired
    UserDao userDao;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    OrgDao orgDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void genareteDomain() {
        clearDatabase();
        String windowsUserName = System.getProperty("user.name");
        System.out.println(windowsUserName);


        /* ------------- Project ----------------------------------*/
        Project testProject = new Project("Test SF Project",
                "https://github.com/testandreytsykh/ciamanager.git",
                "testandreytsykh",
                "ciamanager1",
                "C:\\Users\\"+windowsUserName+"\\Desktop\\Новая папка",
                "This test project with real salesforce org");

        Project airportProject = new Project("Borispol Airport",
                "https://github.com/testUserDX/testCVorg.git",
                "git-login",
                "git-pass",
                "C:",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        Project novaPoshtaProject = new Project("Nova poshta",
                "https://github.com/testUserDX/testRepoForJgit.git",
                "D:",
                "git-login",
                "git-pass",
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");

        projectDao.add(testProject);
        projectDao.add(airportProject);
        projectDao.add(novaPoshtaProject);

        /* ------------- Role ----------------------------------*/
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        roleDao.add( userRole);
        roleDao.add( adminRole);

        // --------------------- Users ---------------------------------------
        User adminSteveBA = new User("Steve Ballmer Admin", "steve-login", passwordEncoder.encode("111"), "111@ctdev.com", adminRole);
        User userPoleBA = new User("Pole Allen BA", "pole-login", passwordEncoder.encode("222"), "222@ctdev.com", userRole);
        User userBillDev = new User("Bill Gates Dev", "bill-login", passwordEncoder.encode("333"), "333@ctdev.com", userRole);
        User userMikeDev = new User("Mike Jordan Dev", "Mike-login", passwordEncoder.encode("444"), "444@ctdev.com",userRole);
        userDao.add(adminSteveBA);
        userDao.add(userPoleBA);
        userDao.add(userBillDev);
        userDao.add(userMikeDev);

        // --------------------- Orgs ---------------------------------------
        Org testorg1 = new Org("taxweb@atlanta.com", "1q2w3e4r5t6y", "master", "https://login.salesforce.com/",  testProject);
        Org testorg2 = new Org("test1@atlanta.com", "112345678", "master", "https://login.salesforce.com/",  testProject);
        Org testorg3 = new Org("test2@atlanta.com", "12345678", "master", "https://login.salesforce.com/",  testProject);
        Org testorg4 = new Org("test3@atlanta.com", "12345678", "master", "https://login.salesforce.com/",  airportProject);
        testorg1.setUserList(Arrays.asList(adminSteveBA,userPoleBA ));
        testorg4.setUserList(Arrays.asList(adminSteveBA));

        orgDao.add(testorg1);
        orgDao.add(testorg2);
        orgDao.add(testorg3);
        orgDao.add(testorg4);
    }

    public void clearDatabase() {

        List<Org> orgs = orgDao.list();
        for (Org org : orgs) {
            orgDao.remove(org);
        }

        List<Project> projectList = projectDao.list();
        for (Project project : projectList) {
            projectDao.remove(project);
        }

        List<User> userList = userDao.list();
        for (User user : userList) {
            userDao.remove(user);
        }

        List<Role> roleList = roleDao.list();
        for (Role role:roleList){
            roleDao.remove(role);
        }
    }
}
