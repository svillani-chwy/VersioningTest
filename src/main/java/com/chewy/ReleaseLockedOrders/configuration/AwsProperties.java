package com.chewy.ReleaseLockedOrders.configuration;

import lombok.Data;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Data
public class AwsProperties {

    AwsSecretConfig awsSecretConfig = new AwsSecretConfig();

    EnvironmentProperties environmentProperties = new EnvironmentProperties();
    private String region;
    private String storeName;
    private String apiToken;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;

    public AwsProperties() throws Exception{
        try {
            Properties prop = new Properties();
            InputStream input = AwsSecretConfig.class.getClassLoader().getResourceAsStream(environmentProperties.getEnvironmentProperties());
            prop.load(input);
            storeName = prop.getProperty("dbSecretName");
            region = prop.getProperty("region");
            Map<String, Object> secrets = awsSecretConfig.getSecrets(storeName, region);
            apiToken = secrets.get("cartApiKey").toString();
            databaseUrl = "jdbc:postgresql://" + secrets.get("host") + ":" + secrets.get("port").toString() + "/" + secrets.get("dbname");
            databaseUsername = secrets.get("username").toString();
            databasePassword = secrets.get("password").toString();
        } catch (Exception e) {
           throw e;
        }
    }
}