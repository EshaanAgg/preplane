package com.preplane.dev.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int userId;
    private String username;
    private String password;

    // Constructors
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    // Getter and setters
    public int getId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Debuging and mapping
    public Map<String, Object> toMap() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", this.userId);
        parameters.put("username", this.username);
        parameters.put("password", this.password);
        return parameters;
    }

    @Override
    public String toString() {
        return "User {" +
                " userId = " + this.userId +
                ", username = '" + this.username + '\'' +
                ", password = '" + this.password + '\'' +
                " }";
    }

}