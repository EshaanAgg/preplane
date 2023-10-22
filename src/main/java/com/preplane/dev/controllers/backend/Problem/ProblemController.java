package com.preplane.dev.controllers.backend.Problem;

import com.preplane.dev.models.Problem;
import com.preplane.dev.models.User;
import com.preplane.dev.repositories.Problem.JDBCProblemRepository;
import com.preplane.dev.security.services.ProblemAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private JDBCProblemRepository problemRepository;

    @Autowired
    private ProblemAuthorizationService problemAuthorizationService;

    @GetMapping("/")
    public ResponseEntity<List<Problem>> getAllProblems(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset) {

        try {
            int lim = limit.orElse(50);
            int off = offset.orElse(0);

            var response = problemRepository.findAll(lim, off);
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblemById(@PathVariable("id") int id) {
        var response = problemRepository.findById(id);
        return new ResponseEntity<>(response.response, response.statusCode);
    }

    @PostMapping("/")
    public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
        try {
            if (problem.getTitle() == null || problem.getStatement() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            var response = problemRepository.save(problem);
            if (response.statusCode == HttpStatus.CREATED) {
                return new ResponseEntity<>(problem, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problem> updateProblem(@PathVariable("id") int id, @RequestBody Problem problem) {
        try {
            var existingProblemResponse = problemRepository.findById(id);
            if (existingProblemResponse.statusCode == HttpStatus.OK) {
                Problem existingProblem = existingProblemResponse.response;

                existingProblem.setTitle(problem.getTitle());
                existingProblem.setStatement(problem.getStatement());
                existingProblem.setAuthorsSolution(problem.getAuthorsSolution());
                existingProblem.setTestcases(problem.getTestcases());
                existingProblem.setTimeLimit(problem.getTimeLimit());
                existingProblem.setMemoryLimit(problem.getMemoryLimit());

                var response = problemRepository.save(existingProblem);
                if (response.statusCode == HttpStatus.CREATED) {
                    return new ResponseEntity<>(existingProblem, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProblem(@PathVariable("id") int id, @AuthenticationPrincipal User loggedInUser) {
        try {
            var existingProblemResponse = problemRepository.findById(id);

            if (existingProblemResponse.statusCode != HttpStatus.OK) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Problem existingProblem = existingProblemResponse.response;
            int problemAuthorId = existingProblem.getAuthor();

            if (problemAuthorizationService.canDeleteProblem(loggedInUser, problemAuthorId)) {
                var response = problemRepository.deleteById(id);
                return new ResponseEntity<>(response.message, response.statusCode);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
