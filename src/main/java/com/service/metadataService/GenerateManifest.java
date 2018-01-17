package com.service.metadataService;

import com.sforce.soap.metadata.MetadataConnection;
import com.utilities.LoginUtils;
import com.utilities.MetadataUtils;


public class GenerateManifest {
    
    public static void generatePackageXml(String username, String password, String orgType, Double apiVersion,String folderPath){
        try {
            MetadataConnection metadataConnection = LoginUtils.login(username,password,orgType,apiVersion);
            MetadataUtils metadataUtils = new MetadataUtils(folderPath);
            metadataUtils.listMetadata(metadataConnection,apiVersion);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
