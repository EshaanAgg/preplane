package com.preplane.dev.payload.submission;

import jakarta.validation.constraints.NotBlank;

public class CodeVerdict {
    public int submissionId;

    @NotBlank(message = "The verdict can't be blank.")
    public String verdict;

    public double executionTime;
    public double executionMemory;

}
