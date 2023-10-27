package com.preplane.dev.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.Thread;

public class ThreadRowMapper implements RowMapper<Thread> {
    @Override
    public Thread mapRow(ResultSet rs, int rowNum) throws SQLException {
        Thread thread = new Thread();
        thread.setThreadId(rs.getInt("thread_id"));
        thread.setTitle(rs.getString("title"));
        thread.setContent(rs.getString("content"));
        thread.setUserCreated(rs.getInt("user_created"));
        thread.setUpvotes(rs.getInt("upvotes"));
        thread.setDownvotes(rs.getInt("downvotes"));

        return thread;
    }
}
