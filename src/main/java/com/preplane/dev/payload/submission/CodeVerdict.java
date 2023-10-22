package com.preplane.dev.payload.submission;

import jakarta.validation.constraints.NotBlank;

public class CodeVerdict {
    @NotBlank(message = "The submission id can't be blank.")
    public int submissionId;

    public String verict;
    public double executionTime;
    public double executionMemory;

}
