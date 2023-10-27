package com.preplane.dev.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.preplane.dev.models.Problem;

public class ProblemRowMapper implements RowMapper<Problem> {
    @Override
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
