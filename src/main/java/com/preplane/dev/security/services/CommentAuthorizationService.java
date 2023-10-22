package com.preplane.dev.security.services;

import com.preplane.dev.models.User;
import com.preplane.dev.models.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentAuthorizationService {

    public boolean canCreateComment(User user) {
        // Only logged-in users (non-null) can create comments.
        return user != null;
    }

    public boolean canUpdateComment(User user, Comment comment) {
        // Users can update their own comments, and admins can update any comment.
        return user != null && (user.getRole().equals("ADMIN")  || comment.getUserId() == user.getId());
    }

    public boolean canDeleteComment(User user, Comment comment) {
        // Users can delete their own comments, and admins can delete any comment.
        return user != null && (user.getRole().equals("ADMIN")  || comment.getUserId() == user.getId());
    }
}
