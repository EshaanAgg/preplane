package com.preplane.dev.controllers.backend;

import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.preplane.dev.models.User;
import com.preplane.dev.payload.MessageResponse;
import com.preplane.dev.payload.auth.JWTResponse;
import com.preplane.dev.payload.auth.LoginRequest;
import com.preplane.dev.payload.auth.SignUpRequest;
import com.preplane.dev.security.jwt.JWTUtils;
import com.preplane.dev.repositories.User.JDBCUserRepository;
import com.preplane.dev.security.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JDBCUserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTUtils JWTUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JWTUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()).get(0);
        userRepository.updateLoginTime(userDetails.getId());

        return ResponseEntity.ok(new JWTResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("emailAddress") String emailAddress, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {

            if (userRepository.usernameExists(username)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.emailExists(emailAddress)) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create new user's account
            User user = new User(username,
                    encoder.encode(password),
                    emailAddress,
                    firstName,
                    lastName);

            var response = userRepository.save(user);
            return new ResponseEntity<>(response.message, response.statusCode);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}