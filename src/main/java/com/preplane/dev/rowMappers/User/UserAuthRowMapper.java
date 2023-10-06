package com.preplane.dev.rowMappers.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.User;

public class UserAuthRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String emailAddress = resultSet.getString("email_address");
        String role = resultSet.getString("role");

        return new User(userId, username, password, emailAddress, role);
    }
}
