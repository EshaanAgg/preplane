package com.preplane.dev.payload.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Username can't be blank.")
    public String username;

    @NotBlank(message = "Password cannot be blank.")
    public String password;
}
