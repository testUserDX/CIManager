package com;

import com.config.WebAppConfig;
import com.model.Org;
import com.model.Project;
import com.model.Role;
import com.model.User;
import com.service.daoService.OrgDao;
import com.service.daoService.ProjectDao;
import com.service.daoService.RoleDao;
import com.service.daoService.UserDao;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public abstract class DomainTestBase extends AbstractJUnit4SpringContextTests {

    private final String deleteScript = "src/main/resources/sql/cleanup.sql";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    protected RoleDao roleDao;

    @Autowired
    protected UserDao userDao;

    @Autowired
    protected ProjectDao projectDao;

    @Autowired
    protected OrgDao orgDao;


    @Before
    public void deleteAlldomainEntities() {
        executeScript(jdbcTemplate, deleteScript);
    }

    public Project getProject() {
        return new Project("test-name", "test-url", "test-login", "test-pass", "test-folder");
    }

    public User getUser() {
        Role role = new Role("test-role");
        roleDao.add(role);
        return new User("test-name", "test-login", "test-pass", role);
    }

    public User getUser(Role role){
        return new User("test-name", "test-login", "test-pass", role);
    }

    public Role getRole(){
        return new Role("testRole");
    }

    public Role getRole(String roleName){
        return new Role(roleName);
    }

    public Org getOrg(Project project) {
        return new Org("login1", "password1", "branch1", "link", project);
    }

    private static void executeScript(JdbcTemplate jt, String script) {
        try {
            ScriptUtils.executeSqlScript(jt.getDataSource().getConnection(), new FileSystemResource(script));
        } catch (SQLException ex) {
            Logger.getLogger(DomainTestBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
