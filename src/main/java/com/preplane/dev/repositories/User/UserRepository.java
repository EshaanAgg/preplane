package com.preplane.dev.repositories.User;

import java.util.List;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.User;

public interface UserRepository {
    // Result functions that are directly consumed by the controllers to send
    // responses
    SQLResult<Integer> save(User user);

    SQLResult<Integer> update(User user);

    SQLResult<User> findById(int userId);

    SQLResult<User> findByUsername(String username);

    SQLResult<Integer> deleteById(int userId);

    SQLResult<List<User>> findAll(int limit, int offset);

    // Utility functions leading with data integrity

    // This function must always be called with a userId that is assured to be in
    // the database as it does not perform the check for the same
    void updateLoginTime(int userId);

    boolean usernameExists(String username);

    boolean emailExists(String emailAddress);

}
