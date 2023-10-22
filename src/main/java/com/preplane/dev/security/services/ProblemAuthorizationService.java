package com.preplane.dev.security.services;

import com.preplane.dev.models.User;
import org.springframework.stereotype.Service;

@Service
public class ProblemAuthorizationService {

    public boolean canDeleteProblem(User loggedInUser, int problemAuthorId) {
        if (loggedInUser.getRole().equals("ROLE_ADMIN")) {
            return true; // Admin can delete any problem
        } else if (loggedInUser.getId() == problemAuthorId) {
            return true; // The author can delete their own problem
        }

        return false; // Default: User is not authorized to delete the problem
    }
}
