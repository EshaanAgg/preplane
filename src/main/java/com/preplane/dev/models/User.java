package com.preplane.dev.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private Date lastLoginAt;
    private String avatar;
    private String role = "ROLE_USER";

    @JsonIgnore
    private String password;

    // Constructors
    public User() {
    }

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public User(String username, String password, String emailAddress, String firstName, String lastName) {
        this(username, password, emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userId, String username, String password, String emailAddress, String firstName, String lastName) {
        this(username, password, emailAddress, firstName, lastName);
        this.userId = userId;
    }

    public User(int userId, String username, String password, String emailAddress, String firstName, String lastName,
            String role, String avatar, Date lastLoginAt) {
        this(userId, username, password, emailAddress, firstName, lastName);
        this.role = role;
        this.avatar = avatar;
        this.lastLoginAt = lastLoginAt;
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

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public String getAvatar() {
        return avatar;
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

    public void setRole(String role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    // Debugging and mapping related implementations
    public Map<String, Object> toMap() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("userId", this.userId);
        parameters.put("username", this.username);
        parameters.put("password", this.password);
        parameters.put("emailAddress", this.emailAddress);
        parameters.put("role", this.role);
        parameters.put("firstName", this.firstName);
        parameters.put("lastName", this.lastName);
        parameters.put("lastLoginAt", this.lastLoginAt);
        parameters.put("avatar", this.avatar);

        return parameters;
    }

    @Override
    public String toString() {
        return "User {\n" +
                "  userId = " + this.userId + ",\n" +
                "  username = '" + this.username + "\',\n" +
                "  emailAddress = '" + this.emailAddress + ",\'\n" +
                "  role = '" + this.role + "\',\n" +
                "  firstName = '" + this.firstName + "\',\n" +
                "  lastName = '" + this.lastName + "\',\n" +
                "  avatar = '" + this.avatar + "\n" +
                "}";
    }
}