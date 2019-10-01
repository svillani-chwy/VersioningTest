package com.chewy.ReleaseLockedOrders.configuration;

import lombok.Data;

import java.io.InputStream;
import java.util.Properties;

@Data
public class CartProperties {

    private EnvironmentProperties environmentProperties = new EnvironmentProperties();
    private String cartStoreBasePath;
    private String getCartsURL;
    private String unlockCartsURL;
    private String apiKey;

    public CartProperties() throws Exception{
        try {
            Properties prop = new Properties();
            InputStream input = AwsSecretConfig.class.getClassLoader().getResourceAsStream(environmentProperties.getEnvironmentProperties());
            prop.load(input);
            cartStoreBasePath = prop.getProperty("cartStoreBasePath");
            getCartsURL = prop.getProperty("getCartsURL");
            unlockCartsURL = prop.getProperty("unlockCartsURL");
            apiKey = prop.getProperty("apiKey");
        } catch (Exception e) {
            throw e;
        }
    }
}
