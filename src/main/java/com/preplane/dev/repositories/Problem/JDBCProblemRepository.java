package com.preplane.dev.repositories.Problem;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Problem;
import com.preplane.dev.models.Tag;
import com.preplane.dev.rowMappers.Tag.TagRowMapper;

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
                } else {
                    problem.setTags(tagResponse.response);
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

    @Transactional
    // Made for internal use
    // Used to find all the tags for a particular problemId
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

}

class ProblemRowMapper implements RowMapper<Problem> {
    @Override
    @Transactional
    public Problem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Problem problem = new Problem();
        problem.setProblemId(rs.getInt("problem_id"));
        problem.setTitle(rs.getString("title"));
        problem.setStatement(rs.getString("statement"));
        problem.setAuthor(rs.getInt("author"));
        problem.setAuthorsSolution(rs.getString("authors_solution"));
        problem.setTestcases(rs.getString("testcases"));
        problem.setTimeLimit(rs.getDouble("time_limit"));
        problem.setMemoryLimit(rs.getDouble("memory_limit"));

        return problem;
    }
}
