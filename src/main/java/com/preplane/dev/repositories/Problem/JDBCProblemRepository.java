package com.preplane.dev.repositories.Problem;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.CodingSubmission;
import com.preplane.dev.models.Problem;
import com.preplane.dev.models.Tag;
import com.preplane.dev.rowMappers.CodingSubmissionRowMapper;
import com.preplane.dev.rowMappers.ProblemRowMapper;
import com.preplane.dev.rowMappers.TagRowMapper;
import com.preplane.dev.security.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JDBCProblemRepository implements ProblemRepository {
    @Autowired
    private JdbcTemplate template;

    @Override
    @Transactional
    public SQLResult<Integer> save(Problem problem) {
        String sqlQuery = "INSERT INTO coding_problem (title, statement, author, authors_solution, testcases, time_limit, memory_limit) "
                +
                "VALUES (?,?,?,?,?,?,?)";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, problem.getTitle(), problem.getStatement(), problem.getAuthor(),
                    problem.getAuthorsSolution(), problem.getTestcases(), problem.getTimeLimit(),
                    problem.getMemoryLimit());
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "The problem was created successfully.";
                result.statusCode = HttpStatus.CREATED;
            } else {
                result.message = "There was an error in creating the problem.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in creating the problem. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Problem> findById(int problemId) {
        String sqlQuery = "SELECT * FROM coding_problem WHERE problem_id = ?";
        var result = new SQLResult<Problem>();

        try {
            Problem problem = template.queryForObject(sqlQuery, new ProblemRowMapper(), problemId);
            if (problem != null) {
                result.message = "Problem fetched successfully.";
                result.statusCode = HttpStatus.OK;
                result.response = problem;
            } else {
                result.message = "There is no problem with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the problem. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<Integer> deleteById(int problemId) {
        String sqlQuery = "DELETE FROM coding_problem WHERE problem_id = ?";
        var result = new SQLResult<Integer>();

        try {
            int rowCount = template.update(sqlQuery, problemId);
            result.response = rowCount;

            if (rowCount == 1) {
                result.message = "Problem deleted successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There is no problem with the provided ID.";
                result.statusCode = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            result.message = "There was an error in deleting the problem. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    @Override
    @Transactional
    public SQLResult<List<Problem>> findAll(int limit, int offset) {
        String sqlQuery = "SELECT * FROM coding_problem ORDER BY problem_id LIMIT ? OFFSET ?";
        var result = new SQLResult<List<Problem>>();

        try {
            List<Problem> problems = template.query(sqlQuery, new ProblemRowMapper(), limit, offset);
            for (var problem : problems) {

                var tagResponse = this.findTagsForProblem(problem.getProblemId());
                if (tagResponse.statusCode != HttpStatus.OK) {
                    problem.setTags(null);
                    System.out.println("[ERROR] Fetching the tags failed for a problem");
                    System.out.println(tagResponse.message);
                } else {
                    problem.setTags(tagResponse.response);
                }

                var submissionResponse = this.findSubmissionsForProblem(problem.getProblemId());
                if (submissionResponse.statusCode != HttpStatus.OK) {
                    problem.setSubmissions(null);
                    System.out.println("[ERROR] Fetching the submissions failed for a problem");
                    System.out.println(submissionResponse.message);
                } else {
                    problem.setSubmissions(submissionResponse.response);
                }
            }

            result.response = problems;

            if (!problems.isEmpty()) {
                result.message = "Problems fetched successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "There are no problems available.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.message = "There was an error in fetching the problems. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return result;
    }

    @Override
    @Transactional
    public SQLResult<List<Problem>> findByTag(String tag) {
        String sqlQuery = "SELECT * FROM coding_problem WHERE tag = ?";
        var result = new SQLResult<List<Problem>>();

        try {
            List<Problem> problems = template.query(sqlQuery, new ProblemRowMapper(), tag);
            result.response = problems;

            if (!problems.isEmpty()) {
                result.message = "Problems fetched by tag successfully.";
                result.statusCode = HttpStatus.OK;
            } else {
                result.message = "No problems available for the specified tag.";
                result.statusCode = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.message = "Error while fetching problems by tag. Error Message: " + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    // Made for internal use
    // Finds all the tags for a particular problem
    @Override
    @Transactional
    public SQLResult<List<Tag>> findTagsForProblem(int problemId) {
        String sqlQuery = "SELECT * FROM tags WHERE tag_id IN (SELECT tag_id FROM coding_tag WHERE problem_id = ?)";
        var result = new SQLResult<List<Tag>>();

        try {
            List<Tag> tags = template.query(sqlQuery, new TagRowMapper(), problemId);
            result.message = "Fetched tags successfully";
            result.response = tags;
            result.statusCode = HttpStatus.OK;
        } catch (Exception e) {
            result.message = "There was an error in fetching the tags for this problem. Error Message: "
                    + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    // Made for internal use
    // Finds all the submissions made by the logged in user for a particular problem
    // The submissions are sorted as latest first
    @Transactional
    private SQLResult<List<CodingSubmission>> findSubmissionsForProblem(int problemId) {
        String sqlQuery = "SELECT * FROM coding_submission WHERE problem_id = ? AND user_id = ? ORDER BY submission_time DESC";
        var result = new SQLResult<List<CodingSubmission>>();

        try {
            List<CodingSubmission> submissions = template.query(sqlQuery, new CodingSubmissionRowMapper(), problemId,
                    Auth.getCurrentUserId());
            result.message = "Fetched the submissions successfully";
            result.response = submissions;
            result.statusCode = HttpStatus.OK;
        } catch (Exception e) {
            result.message = "There was an error in fetching the submissions for this problem. Error Message: "
                    + e.getMessage();
            result.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }
}
