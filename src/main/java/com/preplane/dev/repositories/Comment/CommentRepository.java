package com.preplane.dev.repositories.Comment;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Comment;

import java.util.List;

public interface CommentRepository {
    SQLResult<Integer> save(Comment comment);
    SQLResult<Comment> findById(int commentId);
    SQLResult<Integer> deleteById(int commentId);
    SQLResult<List<Comment>> findCommentsForThread(int threadId);
}
