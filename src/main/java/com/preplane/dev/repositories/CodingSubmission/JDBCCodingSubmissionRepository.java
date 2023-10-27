package com.preplane.dev.repositories.CodingSubmission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.CodingSubmission;
import com.preplane.dev.rowMappers.CodingSubmissionRowMapper;

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

    @Override
    public SQLResult<List<CodingSubmission>> findSubmissionsByUserAndProblem(int userId, int problemId) {
        String sqlQuery = "SELECT * FROM coding_submission WHERE user_id = ? AND problem_id = ?";
        var result = new SQLResult<List<CodingSubmission>>();

        try {
            List<CodingSubmission> submissions = template.query(sqlQuery, new CodingSubmissionRowMapper(), userId,
                    problemId);
            result.response = submissions;

            if (!submissions.isEmpty()) {
                result.message = "Submissions fetched successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There are no submissions available for the user and problem.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the submissions. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    public SQLResult<CodingSubmission> findSubmissionById(int submissionId) {
        String sqlQuery = "SELECT * FROM coding_submission WHERE submission_id = ?";
        var result = new SQLResult<CodingSubmission>();

        try {
            CodingSubmission submission = template.queryForObject(sqlQuery, new CodingSubmissionRowMapper(),
                    submissionId);
            if (submission != null) {
                result.message = "Submission fetched successfully.";
                result.statusCode = HttpStatus.OK;
                result.response = submission;
            } else {
                result.message = "There is no submission with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the submission. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    public SQLResult<List<CodingSubmission>> findSubmissionsByProblem(int problemId) {
        String sqlQuery = "SELECT * FROM coding_submission WHERE problem_id = ?";
        var result = new SQLResult<List<CodingSubmission>>();

        try {
            List<CodingSubmission> submissions = template.query(sqlQuery, new CodingSubmissionRowMapper(), problemId);
            result.response = submissions;

            if (!submissions.isEmpty()) {
                result.message = "Submissions for the problem fetched successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There are no submissions available for the specified problem.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the submissions for the problem. Error Message: "
                    + e.getMessage();
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
