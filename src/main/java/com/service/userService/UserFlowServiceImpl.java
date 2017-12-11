package com.service.userService;

import com.service.FilesTools;
import com.service.daoService.UserDao;
import com.service.gitServise.GitService;
import com.service.gitServise.UserSettings;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.List;

@Service
public class UserFlowServiceImpl implements UserFlowService {


    @Autowired
    UserDao userDao;

    @Autowired
    GitService gitService;

    @Autowired
    FilesTools filesTools;

    private String path;

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Git cloneRemoteRopository(String url, String path) {

        String repoPath = filesTools.createFolder(path,"tmp");
        this.path =repoPath;
        Git git = gitService.cloneRepository(url,repoPath);

        try {
            syncBranchesWithRemotes(git);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        return git;
    }

    @Override
    public boolean commitAll(String message, String path, String branch, CredentialsProvider credentials) throws GitAPIException {
        gitService.addFiles(".",path);
        gitService.commitJob(message,path,branch);
        boolean failPush = gitService.pushToRemote(path,branch,credentials);
        if(!failPush){
            return true;
        }else{
            return false;
        }
    }

    private static void syncBranchesWithRemotes(Git git) throws GitAPIException {
        List<Ref> remoteBranches = git.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();

        for(Ref ref : remoteBranches){
            if(!ref.getName().contains("master")){
                CreateBranchCommand createBranch = git.branchCreate();
                String[] branch = ref.getName().split("/");
                String branchName = branch[2] +"/" + branch[3];

                createBranch.setName(branch[3]);
                createBranch.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK);
                createBranch.setStartPoint(branchName);
                createBranch.call();
            }
        }
    }
}
