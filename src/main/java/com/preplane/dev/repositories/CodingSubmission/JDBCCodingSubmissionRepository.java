package com.preplane.dev.repositories.CodingSubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.CodingSubmission;

@Repository
public class JDBCCodingSubmissionRepository implements CodingSubmissionRepository {
    @Autowired
    private JdbcTemplate template;

    @Override
    @Transactional
    public SQLResult<Integer> save(CodingSubmission submission) {
        String sqlQuery = "INSERT INTO coding_submission (problem_id, user_id, code)  VALUES (?,?,?)";

        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, submission.getProblemId(), submission.getUserId(),
                    submission.getCode());
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "The submission was created successfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in creating the submission.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in creating the submission. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Transactional
    @Override
    public SQLResult<Integer> updateVerdict(int submissionId, String verdict, double time, double memory) {
        String sqlQuery = "UPDATE coding_submission SET compiler_verdict = ?, execution_time = ?, execution_memory = ? WHERE submission_id = ?";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, verdict, time, memory, submissionId);
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "The status was updated successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There is no submission with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in updating the status of the given submission. Error Message: "
                    + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

}
