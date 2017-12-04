package com;

import com.config.WebAppConfig;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public abstract class DomainTestBase extends AbstractJUnit4SpringContextTests {

    private final String deleteScript = "src/main/resources/sql/cleanup.sql";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void deleteAlldomainEntities() {
        executeScript(jdbcTemplate, deleteScript);
//        try {
//            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(deleteScript));
//        } catch (SQLException ex) {
//            Logger.getLogger(DomainTestBase.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    public static void executeScript(JdbcTemplate jt, String script){
        try {
            ScriptUtils.executeSqlScript(jt.getDataSource().getConnection(), new FileSystemResource(script));
        } catch (SQLException ex) {
            Logger.getLogger(DomainTestBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
}
