package com.preplane.dev.rowMappers;

import com.preplane.dev.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setContent(rs.getString("content"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setThreadId(rs.getInt("thread_id"));
        comment.setUpdatedAt(rs.getTimestamp("updated_at"));
        return comment;
    }
}
