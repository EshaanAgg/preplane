package com.preplane.dev.controllers.backend;

import com.preplane.dev.models.Thread;
import com.preplane.dev.models.User;
import com.preplane.dev.models.VoteThread;
import com.preplane.dev.repositories.Thread.JDBCThreadRepository;
import com.preplane.dev.repositories.ThreadVoting.JDBCThreadVotingRepository;
import com.preplane.dev.security.Auth;
import com.preplane.dev.security.services.ThreadAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/threads")
public class ThreadController {

    @Autowired
    private JDBCThreadRepository threadRepository;

    @Autowired
    private JDBCThreadVotingRepository voteThreadRepository;

    @Autowired
    private ThreadAuthorizationService threadAuthorizationService;

    @GetMapping("/")
    public ResponseEntity<List<Thread>> getAllThreads(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset) {

        try {
            int lim = limit.orElse(50);
            int off = offset.orElse(0);

            var response = threadRepository.findAll(lim, off);
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Thread>> getThreadsByUserId(@PathVariable("userId") int userId) {
        try {
            var response = threadRepository.findByUserId(userId);
            return new ResponseEntity<>(response.response, response.statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThreadById(@PathVariable("id") int id) {
        var response = threadRepository.findById(id);
        return new ResponseEntity<>(response.response, response.statusCode);
    }

    @PostMapping("/")
    public ResponseEntity<Thread> createThread(@RequestBody Thread thread, @AuthenticationPrincipal User loggedInUser) {
        try {
            if (thread.getTitle() == null || thread.getContent() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            thread.setUserCreated(loggedInUser.getId());

            var response = threadRepository.save(thread);
            if (response.statusCode == HttpStatus.CREATED) {
                return new ResponseEntity<>(thread, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thread> updateThread(@PathVariable("id") int id, @RequestBody Thread thread,
            @AuthenticationPrincipal User loggedInUser) {
        try {
            var existingThreadResponse = threadRepository.findById(id);
            if (existingThreadResponse.statusCode == HttpStatus.OK) {
                Thread existingThread = existingThreadResponse.response;

                // Check if the logged-in user is the author of the thread
                if (existingThread.getUserCreated() == loggedInUser.getId()) {
                    existingThread.setTitle(thread.getTitle());
                    existingThread.setContent(thread.getContent());

                    var response = threadRepository.save(existingThread);
                    if (response.statusCode == HttpStatus.CREATED) {
                        return new ResponseEntity<>(existingThread, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/upvote/{id}")
    public ResponseEntity<Thread> upvoteThread(@PathVariable("id") int id) {
        try {
            var existingThreadResponse = voteThreadRepository.check(id, Auth.getCurrentUser().getId());
            if (existingThreadResponse.statusCode == HttpStatus.OK && existingThreadResponse.response) {
                var voteThread = new VoteThread(id, Auth.getCurrentUser().getId(), 1);
                var check = voteThreadRepository.save(voteThread);
                if(check.statusCode == HttpStatus.CREATED) {
                    threadRepository.upvoteThread(id);
                    Thread existingThread = threadRepository.findById(id).response;
                    return new ResponseEntity<>(existingThread, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/downvote/{id}")
    public ResponseEntity<Thread> downvoteThread(@PathVariable("id") int id) {
        try {
            var existingThreadResponse = voteThreadRepository.check(id, Auth.getCurrentUser().getId());
            if (existingThreadResponse.statusCode == HttpStatus.OK && existingThreadResponse.response) {
                var voteThread = new VoteThread(id, Auth.getCurrentUser().getId(), -1);
                var check = voteThreadRepository.save(voteThread);
                if(check.statusCode == HttpStatus.CREATED) {
                    threadRepository.downvoteThread(id);
                    Thread existingThread = threadRepository.findById(id).response;
                    return new ResponseEntity<>(existingThread, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> deleteThread(@PathVariable("id") int id, @AuthenticationPrincipal User loggedInUser) {
        try {
            var existingThreadResponse = threadRepository.findById(id);

            if (existingThreadResponse.statusCode != HttpStatus.OK) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Thread existingThread = existingThreadResponse.response;
            int threadAuthorId = existingThread.getUserCreated();

            if (threadAuthorizationService.canDeleteThread(loggedInUser, threadAuthorId)) {
                var response = threadRepository.deleteById(id);
                return new ResponseEntity<>(response.message, response.statusCode);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
