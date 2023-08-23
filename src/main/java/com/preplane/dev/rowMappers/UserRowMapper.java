package com.preplane.dev.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.User;

public class UserRowMapper implements RowMapper<User> {
    
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");

        return new User(userId, username, password);
    }
}
