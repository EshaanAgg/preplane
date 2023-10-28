package com.preplane.dev.controllers.backend;

import com.preplane.dev.models.Comment;
import com.preplane.dev.models.User;
import com.preplane.dev.repositories.Comment.JDBCCommentRepository;
import com.preplane.dev.security.Auth;
import com.preplane.dev.security.services.CommentAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private JDBCCommentRepository commentRepository;

    @Autowired
    private CommentAuthorizationService commentAuthorizationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") int id) {
        var response = commentRepository.findById(id);
        return new ResponseEntity<>(response.response, response.statusCode);
    }

    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        comment.setUserId(Auth.getCurrentUserId());
        var response = commentRepository.save(comment);

        if (response.statusCode == HttpStatus.CREATED) {
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("id") int id, @RequestBody Comment comment,
            @AuthenticationPrincipal User loggedInUser) {
        var existingCommentResponse = commentRepository.findById(id);

        if (existingCommentResponse.statusCode == HttpStatus.OK) {
            Comment existingComment = existingCommentResponse.response;

            if (existingComment.getUserId() == loggedInUser.getId()) {
                existingComment.setContent(comment.getContent());

                var response = commentRepository.save(existingComment);

                if (response.statusCode == HttpStatus.CREATED) {
                    return new ResponseEntity<>(existingComment, HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable("id") int id, @AuthenticationPrincipal User loggedInUser) {
        var existingCommentResponse = commentRepository.findById(id);

        if (existingCommentResponse.statusCode != HttpStatus.OK) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Comment existingComment = existingCommentResponse.response;

        if (commentAuthorizationService.canDeleteComment(loggedInUser, existingComment)) {
            var response = commentRepository.deleteById(id);
            return new ResponseEntity<>(response.message, response.statusCode);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<List<Comment>> getCommentsForThread(@PathVariable("threadId") int threadId) {
        var response = commentRepository.findCommentsForThread(threadId);
        return new ResponseEntity<>(response.response, response.statusCode);
    }
}
