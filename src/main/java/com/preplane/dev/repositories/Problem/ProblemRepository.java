package com.preplane.dev.repositories.Problem;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Problem;

import java.util.List;

public interface ProblemRepository {
    // Result functions that are directly consumed by the controllers to send
    // responses
    SQLResult<Integer> save(Problem problem);

    SQLResult<Problem> findById(int problemId);

    SQLResult<Integer> deleteById(int problemId);

    SQLResult<List<Problem>> findAll(int limit, int offset);

    SQLResult<List<Problem>> findByTag(String tag);
}
