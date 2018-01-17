package com.utilities;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginUtils {


    public static MetadataConnection login(String username, String password, String orgType,Double apiVersion ) throws ConnectionException, IOException, JSONException {


        final String authEndPoint = CommonUtils.getAuthEndPoint(orgType, apiVersion);

        final LoginResult loginResult = loginToSalesforce(username, password, authEndPoint);
        System.out.println("userName: " + loginResult.getUserInfo().getUserName());
        System.out.println("sessionId: " + loginResult.getSessionId());

        final MetadataConnection metadataConnection = createMetadataConnection(loginResult);
        return metadataConnection;
    }

    private static LoginResult loginToSalesforce(final String username, final String password, final String authEndPoint)
            throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(authEndPoint);
        config.setServiceEndpoint(authEndPoint);
        config.setManualLogin(true);

        final PartnerConnection partnerConnection = new PartnerConnection(config);
        return partnerConnection.login(username, password);
    }

    private static MetadataConnection createMetadataConnection(final LoginResult loginResult)
            throws ConnectionException {
        final ConnectorConfig config = new ConnectorConfig();
        config.setServiceEndpoint(loginResult.getMetadataServerUrl());
        config.setSessionId(loginResult.getSessionId());

        final MetadataConnection metadataConnection = new MetadataConnection(config);
        return metadataConnection;
    }

}