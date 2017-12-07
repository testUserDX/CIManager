package com.service.gitServise;

import org.eclipse.jgit.api.Git;

import java.util.List;
import java.util.Set;

public interface GitService {

    public Git createAndInitLocalRepository(String localRepositoryPath);

    public Git openRepository(String localRepositoryPath);

    public Git cloneRepository(String url, String path);

    public void addFiles(String pattern);

    public boolean addFiles(List<String> filesList);

    public boolean commitJob(String message);

    public void createBrunch(String name);
//
//    public boolean removeFileFromRepo(List<String> fileString);
//
//    public Set<String> getStatus(Git git);
//
//    public void createBrunch(String name);

}