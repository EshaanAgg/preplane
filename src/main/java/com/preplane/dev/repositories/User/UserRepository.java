package com.preplane.dev.repositories.User;

import java.util.List;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.User;

public interface UserRepository {
    SQLResult<Integer> save(User user);

    // SQLResult<Integer> update(User user);

    // SQLResult<User> findById(int userId);

    // SQLResult<Integer> deleteById(int userId);

    SQLResult<List<User>> findAll(int limit, int offset);
}
