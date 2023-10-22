package com.preplane.dev.controllers.backend;

import com.preplane.dev.models.CodingSubmission;
import com.preplane.dev.payload.submission.CodeSubmission;
import com.preplane.dev.payload.submission.CodeVerdict;
import com.preplane.dev.repositories.CodingSubmission.JDBCCodingSubmissionRepository;
import com.preplane.dev.security.Auth;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submit")
public class SubmissionController {
    @Autowired
    private JDBCCodingSubmissionRepository codeSubmissionRepository;

    @PutMapping("/")
    public ResponseEntity<?> submitCode(@Valid @RequestBody CodeSubmission submission) {

        try {
            var currentUser = Auth.getCurrentUser();
            var response = codeSubmissionRepository
                    .save(new CodingSubmission(submission.problemId, currentUser.getId(), submission.code));
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/verdict")
    public ResponseEntity<?> updateVerdict(@Valid @RequestBody CodeVerdict verdict) {

        try {
            var response = codeSubmissionRepository
                    .updateVerdict(verdict.submissionId, verdict.verdict, verdict.executionTime,
                            verdict.executionMemory);
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
