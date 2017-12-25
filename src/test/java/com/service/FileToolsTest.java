package com.service;

import com.config.WebAppConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;

import static org.junit.Assert.*;


@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public class FileToolsTest extends AbstractJUnit4SpringContextTests {
    private static final String path= "C:";
    private static final String folderName= "newfolderTest";

    @Autowired
    private FilesTools filesTools;

    @Test
    public void testCreateAndRemoveFolder() {
        String newFullPath = filesTools.createFolder(path, folderName);
        assertEquals(path+"\\"+folderName, newFullPath);
        assertNotNull(filesTools.createFolder(path, folderName));
        File newDirectory = new File(path + "/" + folderName);
        assertTrue(newDirectory.exists());
        filesTools.removeGitFolder(newFullPath);
        assertFalse(newDirectory.exists());
    }
}
