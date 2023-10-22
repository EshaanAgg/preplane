package com.preplane.dev.payload.submission;

import jakarta.validation.constraints.NotBlank;

public class CodeSubmission {
    public int problemId;

    @NotBlank(message = "The submitted code cannot be blank.")
    public String code;
}
