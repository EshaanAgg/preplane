package com.preplane.dev.repositories.Comment;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Comment;
import com.preplane.dev.models.User;
import com.preplane.dev.rowMappers.UserRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCCommentRepository implements CommentRepository {

    @Autowired
    private JdbcTemplate template;

    @Override
    @Transactional
    public SQLResult<Integer> save(Comment comment) {
        String sqlQuery = "INSERT INTO comments (content, user_id, thread_id) VALUES (?,?,?)";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, comment.getContent(), comment.getUserId(), comment.getThreadId());
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "Comment created successfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in creating the comment.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in creating the comment. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Comment> findById(int commentId) {
        String sqlQuery = "SELECT * FROM comments WHERE comment_id = ?";
        var result = new SQLResult<Comment>();

        try {
            Comment comment = template.queryForObject(sqlQuery, new CommentRowMapper(), commentId);
            if (comment != null) {
                result.message = "Comment fetched successfully.";
                result.statusCode = HttpStatus.OK;
                result.response = comment;
            } else {
                result.message = "There is no comment with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the comment. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Integer> deleteById(int commentId) {
        String sqlQuery = "DELETE FROM comments WHERE comment_id = ?";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, commentId);
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "Comment deleted successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There is no comment with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in deleting the comment. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<List<Comment>> findCommentsForThread(int threadId) {
        String sqlQuery = "SELECT * FROM comments WHERE thread_id = ?";
        var result = new SQLResult<List<Comment>>();

        try {
            List<Comment> comments = template.query(sqlQuery, new CommentRowMapper(), threadId);
            result.response = comments;

            if (!comments.isEmpty()) {
                result.message = "Comments fetched successfully.";
                result.statusCode = HttpStatus.OK;

                for (Comment comment : result.response) {
                    var creatorResponse = getUserById(comment.getUserId());
                    if (creatorResponse.statusCode != HttpStatus.OK) {
                        comment.setCreator(null);
                        System.out.println("[ERROR] Fetching the users failed for a comment");
                        System.out.println(creatorResponse.message);
                    } else {
                        comment.setCreator(creatorResponse.response);
                    }

                }

            } else {
                result.message = "There are no comments available for this thread.";
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the comments. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    // Made for internal use
    // Used to fetch the user object for the given comment
    @Transactional
    private SQLResult<User> getUserById(int userId) {
        String sqlQuery = "SELECT * FROM user WHERE user_id = ?";
        var result = new SQLResult<User>();

        try {
            var response = template.query(sqlQuery, new UserRowMapper(), userId);

            if (!response.isEmpty()) {
                result.message = "User fetched successfully.";
                result.statusCode = HttpStatus.OK;
                result.response = response.get(0);
            } else {
                result.message = "There is no user with such the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            System.out.println(e);
            result.message = "There was an error in fetching the user. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }
}

class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setContent(rs.getString("content"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setThreadId(rs.getInt("thread_id"));
        return comment;
    }
}
