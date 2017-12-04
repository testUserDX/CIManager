
package com.integration;

import com.config.WebAppConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

@WebAppConfiguration
@ContextConfiguration(classes=WebAppConfig.class)
public class JdbcConfigurationTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Test
    public void testJdbcConfiguration(){
        assertNotNull(jdbcTemplate);
    }

}
