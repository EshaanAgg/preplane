package com.preplane.dev.payload.submission;

import jakarta.validation.constraints.NotBlank;

public class CodeSubmission {
    @NotBlank(message = "The problem id can't be blank.")
    public int problemId;

    @NotBlank(message = "The submitted code cannot be blank.")
    public String code;
}
