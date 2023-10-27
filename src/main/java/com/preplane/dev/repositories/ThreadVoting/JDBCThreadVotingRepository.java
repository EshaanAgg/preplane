package com.preplane.dev.repositories.ThreadVoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.VoteThread;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JDBCThreadVotingRepository implements ThreadVotingRepository {
    @Autowired
    private JdbcTemplate template;

    private RowMapper<VoteThread> voteThreadMapper;

    public JDBCThreadVotingRepository() {
        this.voteThreadMapper = new voteThreadMapper();
    }

    @Override
    @Transactional
    public SQLResult<Integer> save(VoteThread voteThread) {
        String sqlQuery = "INSERT INTO user_thread VALUES (?,?,?)";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, voteThread.getThreadId(), voteThread.getUserId(),
                    voteThread.getVote());
            result.response = rowCount;
            if (rowCount == 1) {
                result.message = "The vote was saved successfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in making the vote.";
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
    public SQLResult<Integer> check(int threadId, int userId) {
        String sqlQuery = "SELECT * FROM user_thread WHERE thread_id = ? AND user_id = ?";
        var result = new SQLResult<Integer>();

        try {
            var response = template.query(sqlQuery, this.voteThreadMapper, threadId, userId);
            if (response.isEmpty()) {
                result.message = "User is eligible to vote";
                result.statusCode = HttpStatus.OK;
                result.response = 0;
            } else {
                result.message = "User already voted";
                result.statusCode = HttpStatus.OK;
                result.response = response.get(0).getVote();
            }
        } catch (Exception e) {
            result.message = "There was an error in retrieving the thread. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return result;
    }

    @Override
    @Transactional
    public SQLResult<Integer> delete(int threadId, int userId) {
        String sqlQuery = "SELECT * FROM user_thread WHERE thread_id = ? AND user_id = ?";
        var result = new SQLResult<Integer>();

        try {
            var response = template.query(sqlQuery, this.voteThreadMapper, threadId, userId);
            if (response.get(0).getVote() == 1)
                template.update("UPDATE thread SET upvotes = upvotes - 1 WHERE thread_id = ?", threadId);
            else
                template.update("UPDATE thread SET downvotes = downvotes - 1 WHERE thread_id = ?", threadId);

            var rowCount = template.update("DELETE FROM user_thread WHERE thread_id = ? AND user_id = ?", threadId,
                    userId);
            result.response = rowCount;
        } catch (Exception e) {
            result.message = "There was an error in deleting the thread's vote. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return result;
    }
}

class voteThreadMapper implements RowMapper<VoteThread> {
    @Override
    public VoteThread mapRow(ResultSet rs, int rowNum) throws SQLException {
        VoteThread thread = new VoteThread();
        thread.setThreadId(rs.getInt("thread_id"));
        thread.setUserId(rs.getInt("user_id"));
        thread.setVote(rs.getInt("vote"));
        return thread;
    }
}
