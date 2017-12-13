package com.service.userService;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.springframework.stereotype.Service;


public interface UserFlowService {

   Git cloneRemoteRopository(Long projid, String userEmail, String url, String path);

   boolean commitAll(String message, String path, String branch, CredentialsProvider credentials) throws GitAPIException;



}
