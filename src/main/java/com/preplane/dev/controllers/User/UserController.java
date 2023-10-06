package com.preplane.dev.controllers.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.preplane.dev.models.User;
import com.preplane.dev.repositories.User.JDBCUserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    JDBCUserRepository userRepository;

    @GetMapping("/user")
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

    @PostMapping("/auth/signup")
    public ResponseEntity<String> createUser(@RequestBody User reqUser) {
        try {
            User user = new User(reqUser.getUsername(), reqUser.getPassword(), reqUser.getEmailAddress());
            var response = userRepository
                    .save(user);
            return new ResponseEntity<>(response.message, response.statusCode);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") int id,
            @RequestBody User user) {

        user.setId(id);
        var response = userRepository.update(user);
        return new ResponseEntity<>(response.message, response.statusCode);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteTutorial(@PathVariable("id") int id) {
        var response = userRepository.deleteById(id);
        return new ResponseEntity<>(response.message, response.statusCode);
    }
}