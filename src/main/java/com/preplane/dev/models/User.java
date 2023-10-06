package com.preplane.dev.models;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class User {
    private int userId;
    private String username;
    private String password;
    private String emailAddress;
    private Set<String> roles;

    // Constructors
    public User() {
        this.roles.add("USER");
    }

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.roles.add("USER");
    }

    public User(int userId, String username, String password, String emailAddress) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.roles.add("USER");
    }

    // Getters for all the fields
    public int getId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    // Setters for all the fields
    public void setId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Debuging and mapping
    public Map<String, Object> toMap() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("userId", this.userId);
        parameters.put("username", this.username);
        parameters.put("password", this.password);
        parameters.put("emailAddress", this.emailAddress);

        return parameters;
    }

    @Override
    public String toString() {
        return "User {\n" +
                "  userId = " + this.userId + "\n" +
                ", username = '" + this.username + "\'\n" +
                ", emailAdress = '" + this.emailAddress + "\'\n" +
                ", password = '" + this.password + "\'\n" +
                " }";
    }

}