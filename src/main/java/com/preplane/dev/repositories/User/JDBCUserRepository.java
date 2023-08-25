package com.preplane.dev.repositories.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.User;
import com.preplane.dev.rowMappers.UserRowMapper;

import org.springframework.jdbc.core.RowMapper;

@Repository
public class JDBCUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate template;
    private RowMapper<User> mapper;

    public JDBCUserRepository() {
        this.mapper = new UserRowMapper();
    }

    @Override
    @Transactional
    public SQLResult<Integer> save(User user) {
        String sqlQuery = "INSERT INTO user (username, password) VALUES (?,?)";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, user.getUsername(), user.getPassword());
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "The user was created succesfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in creating the user.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            System.out.println(e);
            result.message = "There was an error in creating the user. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;

    }

    // @Override
    // @Transactional
    // public SQLResult<Integer> update(User user) {
    // String sqlQuery = "UPDATE user SET username = ?, password = ?, where user_id
    // = ?";
    // Object payload = new Object[] { user.getUsername(), user.getPassword(),
    // user.getId() };

    // return template.update(sqlQuery, payload);
    // }

    // @Override
    // @Transactional
    // public User findById(int userId) {
    // String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
    // Object payload = new Object[] { userId };

    // try {
    // User user = template.queryForObject(sqlQuery, this.mapper, payload);
    // return user;
    // } catch (IncorrectResultSizeDataAccessException e) {
    // return null;
    // }
    // }

    // @Override
    // @Transactional
    // public int deleteById(int userId) {
    // String sqlQuery = "DELETE FROM user WHERE user_id = ?";
    // return template.update(sqlQuery, userId);
    // }

    @Override
    @Transactional
    public SQLResult<List<User>> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM user ORDER BY user_id LIMIT ? OFFSET ?";
        var result = new SQLResult<List<User>>();

        try {
            var response = template.query(sqlQuery, this.mapper, limit, offset);
            result.response = response;

            if (!response.isEmpty()) {
                result.message = "Users fetched successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There are no registered users.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            System.out.println(e);
            result.message = "There was an error in fetching the users. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }
}
