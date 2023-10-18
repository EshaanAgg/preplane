package com.preplane.dev.payload.auth;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Email;

public class SignUpRequest {
    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters.")
    public String username;

    @NotBlank(message = "Wmail address cannot be blank.")
    @Size(max = 100, message = "Email address must be at max 100 characters.")
    @Email(message = "The email address is not well formed.")
    public String emailAddress;

    @NotBlank(message = "Password is a required property.")
    @Size(min = 8, max = 40, message = "The password must be between 8 and 40 characters.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "The password must be a combination of uppercase letters, lowercase letters, numbers and special characters.")
    public String password;

    // Optional parameters with their default values
    public String firstName = "John";
    public String lastName = "Doe";
}
