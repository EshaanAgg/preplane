package com.preplane.dev.rowMappers;

import org.springframework.jdbc.core.RowMapper;
import com.preplane.dev.models.CodingSubmission;
import com.preplane.dev.models.CodingSubmission.CompilerVerdict;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CodingSubmissionRowMapper implements RowMapper<CodingSubmission> {
    @Override
    public CodingSubmission mapRow(ResultSet rs, int rowNum) throws SQLException {
        CodingSubmission submission = new CodingSubmission();

        submission.setSubmissionId(rs.getInt("submission_id"));
        submission.setProblemId(rs.getInt("problem_id"));
        submission.setUserId(rs.getInt("user_id"));
        submission.setSubmissionTime(rs.getTimestamp("submission_time"));
        submission.setCompilerVerdict(CompilerVerdict.valueOf(rs.getString("compiler_verdict")));
        submission.setCode(rs.getString("code"));
        submission.setExecutionTime(rs.getDouble("execution_time"));
        submission.setExecutionMemory(rs.getDouble("execution_memory"));

        return submission;
    }
}
