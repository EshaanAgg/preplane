package com.preplane.dev.payload.auth;

public class JWTResponse {
    public String token;
    public String type = "Bearer";
    public int id;
    public String username;
    public String email;
    public String role;

    public JWTResponse(String accessToken, int id, String username, String email, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
