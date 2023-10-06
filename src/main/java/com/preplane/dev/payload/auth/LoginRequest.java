package com.preplane.dev.payload.auth;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "'username' is a required property")
    public String username;

    @NotBlank(message = "'Password' is a required property")
    public String password;
}
