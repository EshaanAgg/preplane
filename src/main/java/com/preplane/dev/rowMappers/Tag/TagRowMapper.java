package com.preplane.dev.rowMappers.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.Tag;

public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("tag_id");
        String type = rs.getString("tag_type");
        String name = rs.getString("tag_name");
        return new Tag(id, type, name);
    }
}