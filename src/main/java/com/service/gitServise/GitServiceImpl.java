package com.service.gitServise;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class GitServiceImpl implements GitService {
    private Git git;
    private String localRepositoryPath;

    @Override
    public Git createAndInitLocalRepository(String localRepositoryPath) {
        this.localRepositoryPath = localRepositoryPath;
        try (Git git = Git.init().setDirectory(new File(localRepositoryPath)).call()) {
            this.git = git;
            return git;
        } catch (IllegalStateException | GitAPIException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Git openRepository(String localRepositoryPath) {
        this.localRepositoryPath=localRepositoryPath;
        try {
            git = Git.open(new File(localRepositoryPath));
        } catch (IOException e) {
            System.out.println("Repository does not exist");
            if (git == null) {
                this.createAndInitLocalRepository(localRepositoryPath);
            }
        }
        return git;
    }

    @Override
    public Git cloneRepository(String url, String path) {
        try (Git result = Git.cloneRepository()
                .setURI(url)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("username", "password"))
                .setDirectory(new File(path))
                .call()) {
            return result;
        } catch (IllegalStateException | GitAPIException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

//    private Git getLocalRepository() {
//        try {
//            git = Git.open(new File(localRepositoryPath));
//        } catch (IOException e) {
//            System.out.println("Repository does not exist");
//        }
//        return git;
//    }

    @Override
    public void addFiles(String pattern) {
        try (Git git = Git.open(new File(localRepositoryPath))) {
            git.add().addFilepattern(pattern).call();
        } catch (IOException | GitAPIException e) {
            System.err.println(e.getMessage());
        }
//        return false;
    }

    @Override
    public boolean addFiles(List<String> filesList) {
        try (Git git = Git.open(new File(localRepositoryPath))) {
            for (String fileItem : filesList) {
                git.add().addFilepattern(fileItem).call();
            }
            Status status = git.status().call();
            Set<String> set = status.getAdded();
            System.out.println(set.size());

        } catch (IOException | GitAPIException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean commitJob(String message) {
        try(Git git = Git.open(new File(localRepositoryPath))) {
            Status status  = git.status().call();
            if (!status.getAdded().isEmpty() && !message.isEmpty()) {
                git.commit().setMessage(message).call();
            }
        } catch (IOException | GitAPIException e) {
            System.err.println(e.getMessage());
            //TODO: Log4j
        }
        return false;
    }


    @Override
    public void createBrunch(String name) {
        try(Git git = Git.open(new File(localRepositoryPath))) {
            git.branchCreate().setName(name).call();
//            git.push().call();
        } catch (IOException| GitAPIException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean removeFileFromRepo(List<String> fileString) {
//        return false;
//    }
//
//    @Override
//    public Set<String> getStatus(Git git) {
//        return null;
//    }


}
