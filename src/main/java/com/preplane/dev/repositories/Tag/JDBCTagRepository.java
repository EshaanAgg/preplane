package com.preplane.dev.repositories.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Tag;
import com.preplane.dev.rowMappers.Tag.TagRowMapper;

@Repository
public class JDBCTagRepository implements TagRepository {
    @Autowired
    private JdbcTemplate template;

    @Override
    @Transactional
    public SQLResult<List<Tag>> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM tags LIMIT ? OFFSET ?";
        var result = new SQLResult<List<Tag>>();

        try {
            List<Tag> tags = template.query(sqlQuery, new TagRowMapper(), limit, offset);
            result.message = "Fetched tags successfully";
            result.response = tags;
            result.statusCode = HttpStatus.OK;
        } catch (Exception e) {
            result.message = "There was an error in fetching the tags for this problem. Error Message: "
                    + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }
}
