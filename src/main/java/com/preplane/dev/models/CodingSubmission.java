package com.preplane.dev.models;

import java.util.Date;

public class CodingSubmission {
    public enum CompilerVerdict {
        AC, // Accepted
        WA, // Wrong Answer
        MLE, // Memory Limit Exceeded
        TLE, // Time Limit Exceeded
        CE, // Compilation Error
        IN_QUEUE, // In Queue
    }

    private int submissionId;
    private int problemId;
    private int userId;
    private Date submissionTime;
    private CompilerVerdict compilerVerdict;
    private String code;
    private double executionTime;
    private double executionMemory;

    public CodingSubmission() {
    }

    public CodingSubmission(int problemId, int userId, String code) {
        this.problemId = problemId;
        this.userId = userId;
        this.code = code;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Date submissionTime) {
        this.submissionTime = submissionTime;
    }

    public CompilerVerdict getCompilerVerdict() {
        return compilerVerdict;
    }

    public void setCompilerVerdict(CompilerVerdict compilerVerdict) {
        this.compilerVerdict = compilerVerdict;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public double getExecutionMemory() {
        return executionMemory;
    }

    public void setExecutionMemory(double executionMemory) {
        this.executionMemory = executionMemory;
    }

    @Override
    public String toString() {
        return "CodingSubmission {\n" +
                "  submissionId = " + submissionId + ",\n" +
                "  problemId = " + problemId + ",\n" +
                "  userId = " + userId + ",\n" +
                "  submissionTime = " + submissionTime + ",\n" +
                "  compilerVerdict = " + compilerVerdict + ",\n" +
                "  code = '" + code + "',\n" +
                "  executionTime = " + executionTime + ",\n" +
                "  executionMemory = " + executionMemory + "\n" +
                "}";
    }
}
