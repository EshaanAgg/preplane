package com.preplane.dev.repositories.Thread;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Thread;
import com.preplane.dev.models.User;
import com.preplane.dev.rowMappers.ThreadRowMapper;
import com.preplane.dev.rowMappers.UserRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
                var creatorResponse = getUserById(response.get(0).getUserCreated());
                if (creatorResponse.statusCode != HttpStatus.OK) {
                    response.get(0).setCreator(null);
                    System.out.println("[ERROR] Fetching the users failed for a thread");
                    System.out.println(creatorResponse.message);
                } else {
                    response.get(0).setCreator(creatorResponse.response);
                }

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
            List<Thread> response = template.query(sqlQuery, new ThreadRowMapper(), userId);
            for (var thread : response) {
                String query = "SELECT * FROM user WHERE user_id = ?";
                thread.setCreator(template.query(query, new UserRowMapper(), thread.getUserCreated()).get(0));
            }
            result.response = response;

            if (!response.isEmpty()) {
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

            for (var thread : response) {
                String query = "SELECT * FROM user WHERE user_id = ?";
                thread.setCreator(template.query(query, new UserRowMapper(), thread.getUserCreated()).get(0));
            }
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

    // Made for internal use
    // Used to fetch the user object for the given thread
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

    @Override
    @Transactional
    public void upvoteThread(int threadId) {
        String sqlQuery = "UPDATE thread SET upvotes = upvotes + 1 WHERE thread_id = ?";
        try {
            template.update(sqlQuery, threadId);
        } catch (Exception e) {
            System.out.println("There was an error in upvoting the thread");
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public void downvoteThread(int threadId) {
        String sqlQuery = "UPDATE thread SET downvotes = downvotes + 1 WHERE thread_id = ?";
        try {
            template.update(sqlQuery, threadId);
        } catch (Exception e) {
            System.out.println("There was an error in downvoting the thread");
            System.out.println(e);
        }
    }
}
