package com.chewy.ReleaseLockedOrders.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class HikariDriverConfig {

    private HikariConfig config = new HikariConfig();

    private HikariDataSource ds;

    private AwsProperties awsProperties;

    public HikariDriverConfig() throws Exception{

        awsProperties = new AwsProperties();

        config.setJdbcUrl(awsProperties.getDatabaseUrl());
        config.setUsername(awsProperties.getDatabaseUsername());
        config.setPassword(awsProperties.getDatabasePassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return ds.getConnection();
    }
}
