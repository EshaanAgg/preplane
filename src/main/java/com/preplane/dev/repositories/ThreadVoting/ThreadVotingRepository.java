package com.preplane.dev.repositories.ThreadVoting;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.VoteThread;

public interface ThreadVotingRepository {
    SQLResult<Integer> save(VoteThread voteThread);
    SQLResult<Boolean> check(int threadId, int userId);
}
