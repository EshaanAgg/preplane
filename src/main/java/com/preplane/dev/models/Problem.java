package com.preplane.dev.models;

import java.util.HashMap;
import java.util.Map;

public class Problem {
    private int problemId;
    private String title;
    private String statement;
    private int author;// Foreign Key from Users
    private String authorsSolution;
    private String testcases;
    private double timeLimit;
    private double memoryLimit;

    public Problem() {
    }

    public Problem(String title, String statement, int author, String authorsSolution, String testcases,
            double timeLimit, double memoryLimit) {
        this.title = title;
        this.statement = statement;
        this.author = author;
        this.authorsSolution = authorsSolution;
        this.testcases = testcases;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getAuthorsSolution() {
        return authorsSolution;
    }

    public void setAuthorsSolution(String authorsSolution) {
        this.authorsSolution = authorsSolution;
    }

    public String getTestcases() {
        return testcases;
    }

    public void setTestcases(String testcases) {
        this.testcases = testcases;
    }

    public double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public double getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(double memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("problemId", this.problemId);
        parameters.put("title", this.title);
        parameters.put("statement", this.statement);
        parameters.put("author", this.author);
        parameters.put("authorsSolution", this.authorsSolution);
        parameters.put("testcases", this.testcases);
        parameters.put("timeLimit", this.timeLimit);
        parameters.put("memoryLimit", this.memoryLimit);

        return parameters;
    }

    @Override
    public String toString() {
        return "Problem {\n" +
                "  problemId = " + this.problemId + ",\n" +
                "  title = '" + this.title + "\',\n" +
                "  statement = '" + this.statement + ",\'\n" +
                "  author = " + this.author + ",\n" +
                "  authorsSolution = '" + this.authorsSolution + "\',\n" +
                "  testcases = '" + this.testcases + "\',\n" +
                "  timeLimit = " + this.timeLimit + ",\n" +
                "  memoryLimit = " + this.memoryLimit + "\n" +
                "}";
    }
}
