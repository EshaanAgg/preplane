package com.preplane.dev.controllers.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.preplane.dev.models.User;
import com.preplane.dev.repositories.User.JDBCUserRepository;
import com.preplane.dev.security.Auth;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    JDBCUserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Optional<Integer> limit,
            @RequestParam Optional<Integer> offset) {

        try {
            int lim = limit.orElse(50);
            int off = offset.orElse(0);

            var response = userRepository.findAll(lim, off);
            return new ResponseEntity<>(response.response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {

        var response = userRepository.findById(id);
        return new ResponseEntity<>(response.response, response.statusCode);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") int id) {
        // The user can only delete himself, or be deleted by an admin
        if (Auth.getCurrentUser().getId() != id)
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        var response = userRepository.deleteById(id);
        return new ResponseEntity<>(response.message, response.statusCode);
    }
}
