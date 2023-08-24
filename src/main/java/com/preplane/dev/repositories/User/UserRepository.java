package com.preplane.dev.repositories.User;

import java.util.List;

import com.preplane.dev.models.User;

public interface UserRepository {
    // Returns 1 if the user was saved successfully
    int save(User user);

    // Returns 1 if the user was found and updated successfully
    int update(User user);

    // Returns the user object if found, else null
    User findById(int userId);

    // Returns 1 if the user with the provided userId was found
    int deleteById(int userId);

    // Returns the list of users in the database
    List<User> findAll(int limit, int offset);
}
