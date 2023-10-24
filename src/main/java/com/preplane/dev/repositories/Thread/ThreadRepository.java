package com.preplane.dev.repositories.Thread;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Thread;

import java.util.List;

public interface ThreadRepository {
    SQLResult<Integer> save(Thread thread);

    SQLResult<Thread> findById(int threadId);

    SQLResult<Integer> deleteById(int threadId);

    SQLResult<List<Thread>> findAll(int limit, int offset);

    SQLResult<List<Thread>> findByUserId(int userId);

    void upvoteThread(int threadId);
}
