package com.preplane.dev.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.preplane.dev.security.services.UserDetailsImpl;

public class Auth {
    public static UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
