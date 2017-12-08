package com.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FilesTools {

    public String createFolder(String path, String foldername) {
        File newDirectory = new File(path + "//" + foldername);
        if (newDirectory.exists()) {
            return newDirectory.getAbsolutePath();
        }
        try {
            newDirectory.mkdir();
            return newDirectory.getAbsolutePath();
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void removeGitFolder(String path, String folderName) {
        try {
            FileUtils.forceDelete(new File(path + "/" + folderName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
