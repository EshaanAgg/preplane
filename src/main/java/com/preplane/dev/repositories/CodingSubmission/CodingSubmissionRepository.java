package com.preplane.dev.repositories.CodingSubmission;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.CodingSubmission;

public interface CodingSubmissionRepository {
    // Used to add a submission initally when the user submits the same on the
    // frontend. It only creates the object, and sets the basic properties
    SQLResult<Integer> save(CodingSubmission codingSubmission);

    // Used to update the verdict of a submission after the code has been submitted
    // and tested against the supplied test cases
    SQLResult<Integer> updateVerdict(int submissionId, String verdict, double time, double memory);
}
