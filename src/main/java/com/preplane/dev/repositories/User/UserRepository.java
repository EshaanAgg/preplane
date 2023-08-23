package com.preplane.dev.repositories.User;

import java.util.Optional;
import java.util.stream.Stream;

import com.preplane.dev.models.User;

public interface UserRepository {
    // Returns the newly created User object
    User save(User user);

    // Returns the updated user
    User update(User user);

    // Returns an optional type which resolves to User if the same was found
    Optional<User> findById(int userId);

    // Returns a boolean showing if the deletion was successful or not
    boolean deleteById(int userId);

    // Returns a list of all the users
    Stream<User> findAll(int limit, int offset);
}
