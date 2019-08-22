package com.deyatech.admin.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/20 15:46
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class JdbcUtils {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private Connection connection;

    public Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public DatabaseMetaData getMetaData() {
        DatabaseMetaData metaData = null;
        try {
            metaData = getConnection().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metaData;
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
