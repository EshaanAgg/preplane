package com.preplane.dev.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String emailAddress = resultSet.getString("email_address");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String role = resultSet.getString("role");
        String avatar = resultSet.getString("avatar");
        Date lastLoginAt = resultSet.getDate("last_login");

        return new User(userId, username, password, emailAddress, firstName, lastName, role, avatar, lastLoginAt);
    }
}
