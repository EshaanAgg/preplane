package com.preplane.dev.repositories.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public int save(User user) {
        String sqlQuery = "INSERT INTO user (username, password) VALUES(?,?)";
        Object payload = new Object[] { user.getUsername(), user.getPassword() };

        return template.update(sqlQuery, payload);
    }

    @Override
    @Transactional
    public int update(User user) {
        String sqlQuery = "UPDATE user SET username = ?, password = ?, where user_id = ?";
        Object payload = new Object[] { user.getUsername(), user.getPassword(), user.getId() };

        return template.update(sqlQuery, payload);
    }

    @Override
    @Transactional
    public User findById(int userId) {
        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        Object payload = new Object[] { userId };

        try {
            User user = template.queryForObject(sqlQuery, this.mapper, payload);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public int deleteById(int userId) {
        String sqlQuery = "DELETE FROM user WHERE user_id = ?";
        return template.update(sqlQuery, userId);
    }

    @Override
    @Transactional
    public List<User> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM user LIMIT ? OFFSET ?";
        Object payload = new Object[] { limit, offset };

        return template.query(sqlQuery, this.mapper, payload);
    }
}
