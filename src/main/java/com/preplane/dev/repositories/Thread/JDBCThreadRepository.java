package com.preplane.dev.repositories.Thread;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JDBCThreadRepository implements ThreadRepository {

    @Autowired
    private JdbcTemplate template;

    private RowMapper<Thread> threadMapper;

    public JDBCThreadRepository() {
        this.threadMapper = new ThreadRowMapper();
    }

    @Override
    @Transactional
    public SQLResult<Integer> save(Thread thread) {
        String sqlQuery = "INSERT INTO thread (title, content, user_created) VALUES (?,?,?)";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, thread.getTitle(), thread.getContent(), thread.getUserCreated());
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "The thread was created successfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in creating the thread.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in creating the thread. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Thread> findById(int threadId) {
        String sqlQuery = "SELECT * FROM thread WHERE thread_id = ?";
        var result = new SQLResult<Thread>();

        try {
            var response = template.query(sqlQuery, this.threadMapper, threadId);

            if (!response.isEmpty()) {
                result.message = "Thread fetched successfully.";
                result.statusCode = HttpStatus.OK;
                result.response = response.get(0);
            } else {
                result.message = "There is no thread with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the thread. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Integer> deleteById(int threadId) {
        String sqlQuery = "DELETE FROM thread WHERE thread_id = ?";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, threadId);
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "Thread deleted successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There is no thread with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in deleting the thread. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<List<Thread>> findByUserId(int userId) {
    String sqlQuery = "SELECT * FROM thread WHERE user_created = ?";
    var result = new SQLResult<List<Thread>>();

    try {
        List<Thread> threads = template.query(sqlQuery, new ThreadRowMapper(), userId);
        result.response = threads;

        if (!threads.isEmpty()) {
            result.message = "Threads fetched successfully.";
            result.statusCode = HttpStatus.OK;
        } else {
            result.message = "No threads available for the specified user.";
            result.statusCode = HttpStatus.NO_CONTENT;
        }
    } catch (Exception e) {
        result.message = "Error fetching threads. Error Message: " + e.getMessage();
        result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    return result;
}


    @Override
    @Transactional
    public SQLResult<List<Thread>> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM thread ORDER BY thread_id LIMIT ? OFFSET ?";
        var result = new SQLResult<List<Thread>>();

        try {
            var response = template.query(sqlQuery, this.threadMapper, limit, offset);
            result.response = response;

            if (!response.isEmpty()) {
                result.message = "Threads fetched successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There are no threads available.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the threads. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }
}

class ThreadRowMapper implements RowMapper<Thread> {
    @Override
    public Thread mapRow(ResultSet rs, int rowNum) throws SQLException {
        Thread thread = new Thread();
        thread.setThreadId(rs.getInt("thread_id"));
        thread.setTitle(rs.getString("title"));
        thread.setContent(rs.getString("content"));
        thread.setUserCreated(rs.getInt("user_created"));
        return thread;
    }
}
