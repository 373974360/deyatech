package com.deyatech.common.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 将操作日志存入数据库中
 */
public class LogInfoDatabaseHandler implements LogInfoHandler {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String sql = "insert into admin_logs(" +
            " id_,notes_, method_, request_url, user_id," +
            " params_, time_, ip_, enable_, remark_," +
            " create_by, create_time, update_by, update_time) " +
            " values (uuid_short(),?,?,?,?,?,?,?,1,?,?,now(),?,now())";

    @Override
    public void handle(LogsInfo logData) {
        jdbcTemplate.execute(sql, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {

                preparedStatement.setString(1, logData.getNotes());
                preparedStatement.setString(2, logData.getMethod());
                preparedStatement.setString(3, logData.getRequestUrl());
                preparedStatement.setString(4, logData.getUserId());
                preparedStatement.setString(5, logData.getParams());
                preparedStatement.setLong(6, logData.getTime());
                preparedStatement.setString(7, logData.getIp());
                preparedStatement.setString(8, logData.getNotes());
                preparedStatement.setString(9, logData.getCreateBy());
                preparedStatement.setString(10, logData.getCreateBy());
                return preparedStatement.executeUpdate();
            }
        });
    }
}
