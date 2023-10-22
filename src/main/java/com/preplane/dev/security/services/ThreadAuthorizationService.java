package com.preplane.dev.security.services;

import com.preplane.dev.models.Thread;
import com.preplane.dev.models.User;
import com.preplane.dev.repositories.Thread.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThreadAuthorizationService {

    @Autowired
    private ThreadRepository threadRepository;

    public boolean canDeleteThread(User loggedInUser, int threadId) {
        Thread thread = threadRepository.findById(threadId).response;

        // Check if the logged-in user is the creator of the thread or has admin privileges
        return thread != null && (loggedInUser.getRole().equals("ADMIN") || loggedInUser.getId() == thread.getUserCreated());
    }

    // Add more authorization methods as needed
}

