package com.vcsService;

import com.config.WebAppConfig;
import com.service.gitServise.GitService;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.test.context.web.WebAppConfiguration;

//import org.testng.annotations.Test;

@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public class GitAuthTesting extends AbstractJUnit4SpringContextTests {
    final String REMOTE_URL = "https://github.com/javasd11/testJGit.git";
    final String USER_NAME = "javasd11";
    final String USER_PASSORD = "";
    final String REPO_PATH = "D://Inbox//testGitRepo";
    final String FILE_NAME = "Newfile";

    @Autowired
    private GitService gitJob;

    @Before
    public void init() {
        removeGitFolder();
    }

    @Test
    public void testOpenGit() {
        Git git = gitJob.openRepository(REPO_PATH);
        assertNotNull("Repository not initialized", git);
        File gitDir = new File(REPO_PATH + "/.git");
        assertNotNull(git);
        assertTrue("Folder .git does not exist", gitDir.exists());
        assertTrue("Folder .git not found", gitDir.isDirectory());
    }

    @Test
    public void testInitGit() {
        Git git = gitJob.createAndInitLocalRepository(REPO_PATH);
        git.getRepository().getDirectory();
        File gitDir = new File(REPO_PATH + "/.git");
        assertNotNull(git);
        assertTrue("Folder .git does not exist", gitDir.exists());
        assertTrue("Folder .git not found", gitDir.isDirectory());
    }

    @Test
    public void testCloneGit() {
        Git git = gitJob.cloneRepository(REMOTE_URL, REPO_PATH);
        File gitDir = new File(REPO_PATH);
        List<File> foldersList = Arrays.asList(gitDir.listFiles());
        assertNotNull(git);
        boolean fileResultClone = false;
        for (File fileItem : foldersList) {
            if (!fileItem.getName().equals(".git")) {
                fileResultClone = true;
                break;
            }
        }
        assertTrue("files were not cloned from repository", fileResultClone);
        assertTrue("Folder git does not exist", gitDir.exists());
        assertTrue("Folder git not found", gitDir.isDirectory());
        assertTrue(new File(REPO_PATH+"/README.md").exists());
    }

    @Test
    public void testAddToGit() {
        gitJob.createAndInitLocalRepository(REPO_PATH);
        List<String> filesList = Arrays.asList(
                crateNewFile(FILE_NAME+1),
                crateNewFile(FILE_NAME+2),
                crateNewFile(FILE_NAME+3)
                );
        gitJob.addFiles(filesList);

        Git git = gitJob.openRepository(REPO_PATH);
        try {
            Status status = git.status().call();
            Set<String> addedFiles = status.getAdded();
            assertEquals(3, addedFiles.size());
            assertTrue("file not found",addedFiles.containsAll(filesList));
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        git.close();
    }

    @Test
    public void testAddFilesByPattern(){
        gitJob.createAndInitLocalRepository(REPO_PATH);
        List<String> filesList = Arrays.asList(
                crateNewFile(FILE_NAME+1),
                crateNewFile(FILE_NAME+2),
                crateNewFile(FILE_NAME+3)
        );
        gitJob.addFiles(".");
        Git git = gitJob.openRepository(REPO_PATH);
        try {
            Status status = git.status().call();
            Set<String> addedFiles = status.getAdded();
            assertEquals(3, addedFiles.size());
            assertTrue("file not  found",addedFiles.containsAll(filesList));
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        git.close();
    }

    @Test
    public void testCommitJob(){
        gitJob.createAndInitLocalRepository(REPO_PATH);

        crateNewFile(FILE_NAME);
        gitJob.addFiles(".");
        gitJob.commitJob("init commit");
        Git git = gitJob.openRepository(REPO_PATH);

        RevCommit youngestCommit = null;
        List<Ref> branches = null;
        try {
            branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        try(RevWalk walk = new RevWalk(git.getRepository())) {
            for(Ref branch : branches) {
                RevCommit commit = walk.parseCommit(branch.getObjectId());
                    youngestCommit = commit;
                assertEquals(youngestCommit.getFullMessage(), "init commit");
            }
        }catch (IOException  e){
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateBrunch(){
        Git git = gitJob.createAndInitLocalRepository(REPO_PATH);
        List<Ref> branchlist = null;
        gitJob.createBrunch("UAT");

        try {
            branchlist = git.branchList().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        for (Ref ref : branchlist) {
            System.out.println("Branch-Created: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
        }
//        List<Ref> branches = null;
//        try {
//            branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//        }
//          git.push();
//        assertEquals(2,branches.size());

        git.close();


    }

    public String crateNewFile(String filename) {
        File f = new File(REPO_PATH + "/"+filename);
        if (!f.exists()) {
            try {
                f.createNewFile();
                return filename;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void removeGitFolder() {
        try {
            FileUtils.forceDelete(new File(REPO_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
