package com.chewy.ReleaseLockedOrders.configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;


public class AwsSecretConfig {

    public Map<String, Object> getSecrets(String storeName, String region) throws Exception {

        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder
                .standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance());
        clientBuilder.setRegion(region);
        AWSSecretsManager client = clientBuilder.build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(storeName);

        GetSecretValueResult getSecretValueResult = client.getSecretValue(getSecretValueRequest);

        final String secretBinaryString = getSecretValueResult.getSecretString();
        final ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(secretBinaryString, HashMap.class);
    }
}
