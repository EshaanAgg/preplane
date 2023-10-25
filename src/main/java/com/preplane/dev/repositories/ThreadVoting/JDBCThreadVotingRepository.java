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
public class JDBCThreadVotingRepository implements ThreadVotingRepository{
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
            int rowCount = template.update(sqlQuery, voteThread.getThreadId(), voteThread.getUserId(), voteThread.getVote());
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
    public SQLResult<Boolean> check(int threadId, int userId) {
        String sqlQuery = "select * from user_thread where thread_id = ? and user_id = ?";
        var result = new SQLResult<Boolean>();
        
        try {
            var response = template.query(sqlQuery, this.voteThreadMapper, threadId, userId);
            if (response.isEmpty()) {
                result.message = "User is eligible to vote";
                result.statusCode = HttpStatus.OK;
                result.response = true;
            } else {
                result.message = "User already voted";
                result.statusCode = HttpStatus.BAD_REQUEST;
                result.response = false;
            }
        } catch (Exception e) {
            result.message = "There was an error in retrieving the thread. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            result.response = false;
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
