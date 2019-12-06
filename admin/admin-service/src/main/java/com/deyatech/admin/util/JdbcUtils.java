package com.deyatech.admin.util;

import cn.hutool.http.HttpStatus;
import com.deyatech.common.exception.BusinessException;
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

    public String getDatabaseName() {
        // "jdbc:mysql://mysql.deyatong.com:3306/deyatech-cms?characterEncoding=utf8&autoReConnect=true&useSSL=false"
        String name = url.substring(url.lastIndexOf("/") + 1);
        int index = name.lastIndexOf("?");
        if (index > -1) {
            name = name.substring(0, index);
        }
        return name;
    }

    public Connection getConnection() {
        try {
            if (connection == null || (connection != null && connection.isClosed())) {
                Class.forName(driverClassName);
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "数据库连接创建失败");
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

    public void close() {
        if (connection == null) {
            return;
        }
        close(connection);
    }

    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
