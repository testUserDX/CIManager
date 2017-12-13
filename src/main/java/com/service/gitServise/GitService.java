package com.service.gitServise;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.transport.CredentialsProvider;

import java.util.List;
import java.util.Set;

public interface GitService {

    Git createAndInitLocalRepository(String localRepositoryPath);

    Git openRepository(String localRepositoryPath);

    Git cloneRepository(String url, String path);

    void addFiles(String pattern, String path);

    boolean addFiles(List<String> filesList);

    boolean commitJob(String message, String path, String branch);

    void createBrunch(String name);

    boolean pushToRemote(String path, String branch, CredentialsProvider credentials);

    Set<String> getStatus(String path);

    List<DiffEntry> getFilesInDiff(String path, String branch);



//
//    public boolean removeFileFromRepo(List<String> fileString);
//
//    public Set<String> getStatus(Git git);
//
//    public void createBrunch(String name);

}