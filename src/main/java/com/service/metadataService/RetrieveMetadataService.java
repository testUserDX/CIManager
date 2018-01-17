package com.service.metadataService;


import org.apache.commons.io.FileUtils;

import java.io.File;

public class RetrieveMetadataService {


    public static void retrieveMetadata(String password,String username,Double apiVersion, String orgType, String folderPath){


        File tmpFolder = new File(folderPath+"zip");
        tmpFolder.mkdir();
        GenerateManifest.generatePackageXml(username,password,orgType,apiVersion,folderPath);
        try {
            RetrieveSample retrieveSample = new RetrieveSample(folderPath,apiVersion);
            retrieveSample.createMetadataConnection(username,password,orgType,apiVersion);
            retrieveSample.retrieveMetadata();
            FileUtils.forceDelete(tmpFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
