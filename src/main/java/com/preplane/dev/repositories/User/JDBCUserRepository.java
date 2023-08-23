package com.preplane.dev.repositories.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.preplane.dev.models.User;
import com.preplane.dev.rowMappers.UserRowMapper;

@Repository
public class JDBCUserRepository implements UserRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;
    private final RowMapper<User> rowMapper;
    private final SimpleJdbcInsert insert;

    public JDBCUserRepository() {
        this.rowMapper = new UserRowMapper();
        this.insert = new SimpleJdbcInsert(template.getJdbcTemplate());
        this.insert.setTableName("user");
        this.insert.usingGeneratedKeyColumns("user_id");
    }

    @Override
    @Transactional
    public User save(User user) {
        Number id = insert.executeAndReturnKey(user.toMap());

        return findById(id.intValue())
                .orElseThrow(() -> new IllegalStateException("The newly created user has no auto-assigned user_id. "));

    }

    @Override
    @Transactional
    public User update(User user) {
        String sqlQuery = "UPDATE user SET username = :username, password = :password, where user_id = :userId";

        Map<String, Object> paramMap = user.toMap();
        template.update(sqlQuery, paramMap);

        return findById(user.getId()).orElseThrow(
                () -> new IllegalStateException("The user sent to be updated has an ID which does not exist."));

    }

    @Override
    @Transactional
    public Optional<User> findById(int userId) {
        String sqlQuery = "SELECT * FROM user WHERE user_id = :userId";

        Map<String, Object> parameters = Collections.singletonMap("userId", userId);

        return template.queryForStream(sqlQuery, parameters, rowMapper).findFirst();
    }

    @Override
    @Transactional
    public boolean deleteById(int userId) {
        String sqlQuery = "DELETE FROM user WHERE user_id = :userId";

        Map<String, Object> paramMap = Collections.singletonMap("userId", userId);

        return template.update(sqlQuery, paramMap) == 1;
    }

    @Override
    @Transactional
    public Stream<User> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM user LIMIT :limit OFFSET :offset";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("limit", limit);
        paramMap.put("offset", offset);

        return template.queryForStream(sqlQuery, paramMap, rowMapper);
    }
}
