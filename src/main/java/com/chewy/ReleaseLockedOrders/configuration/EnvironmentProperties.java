package com.chewy.ReleaseLockedOrders.configuration;

import lombok.Data;

@Data
public class EnvironmentProperties {

    private String environmentProperties;

    public EnvironmentProperties() {
        switch (System.getenv().get("env") != null ? System.getenv().get("env") : "") {
            case "qa":
                environmentProperties = "application-qa.yml";
                break;
            case "stg":
                environmentProperties = "application-stg.yml";
                break;
            case "prod":
                environmentProperties = "application-prod.yml";
                break;
            default:
                environmentProperties = "application-dev.yml";
        }
    }
}
