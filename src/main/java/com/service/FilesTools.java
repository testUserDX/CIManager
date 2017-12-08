package com.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FilesTools {

    public boolean cretateFolder(String path, String foldername){

        File newDerectory = new File(path+"/"+foldername);

        if(!newDerectory.exists()){
            return true;
        }

        try {
            newDerectory.mkdir();

        }catch (SecurityException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public void removeGitFolder(String path, String folderName) {
        try {
            FileUtils.forceDelete(new File(path+"/"+folderName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
