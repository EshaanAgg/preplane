package com.preplane.dev.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;

import com.preplane.dev.security.services.UserDetailsImpl;

public class Auth {
    public static UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static int getCurrentUserId() {
        return ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static String getCurrentUserRole() {
        UserDetailsImpl currentUser = getCurrentUser();
        List<String> roles = currentUser.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toList());
        return roles.get(0);
    }

    public static boolean VerifySelfOrAdmin(int userId) {
        return userId == getCurrentUserId() || getCurrentUserRole() == "ROLE_ADMIN";
    }
}
