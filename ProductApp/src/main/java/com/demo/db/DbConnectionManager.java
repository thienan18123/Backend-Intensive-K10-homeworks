package com.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionManager {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", requireEnv("DB_USER"));
        props.setProperty("password", requireEnv("DB_PASSWORD"));
        props.setProperty("cloudSqlInstance", requireEnv("CLOUD_SQL_INSTANCE"));
        props.setProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");

        
        String url = "jdbc:postgresql:///" + requireEnv("DB_NAME");
        return DriverManager.getConnection(url, props);
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing environment variable: " + name);
        }
        return value;
    }
}