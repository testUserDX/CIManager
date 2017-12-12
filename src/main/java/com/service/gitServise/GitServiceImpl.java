package com.service.gitServise;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
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
    public void addFiles(String pattern,String path) {
        try (Git git = Git.open(new File(path))) {
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
    public boolean commitJob(String message,String path, String branch) {
        try(Git git = Git.open(new File(path))) {
            Status status  = git.status().call();
            if (!status.getAdded().isEmpty() && !message.isEmpty()
                    || !status.getChanged().isEmpty()
                    || !status.getModified().isEmpty()
                    ||  status.hasUncommittedChanges()
                    || !status.getUntracked().isEmpty()) {
                CheckoutCommand checkout = git.checkout();
                checkout.setName(branch).call();
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


    public boolean pushToRemote(String path, String branch, CredentialsProvider credentials) {

        try(Git git = Git.open(new File(path))) {
            CheckoutCommand checkout = git.checkout();
            checkout.setName(branch);
            checkout.call();

            PushCommand push = git.push();
            push.setCredentialsProvider(credentials);
            push.setRemote("origin");
            push.call();
        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public List<DiffEntry> getFilesInDiff(String path,String branch){

        try(Git git = Git.open(new File(path))){
            ObjectId head = git.getRepository().resolve("HEAD^{tree}");
            ObjectId oldHead = git.getRepository().resolve("HEAD~1^{tree}");

            try(ObjectReader reader = git.getRepository().newObjectReader()){
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader,oldHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader,head);

                List<DiffEntry> diffEntries = git.diff()
                                                .setNewTree(newTreeIter)
                                                .setOldTree(oldTreeIter)
                                                .call();
                return diffEntries;

            } catch (GitAPIException e) {
                e.printStackTrace();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
