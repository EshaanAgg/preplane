package com.preplane.dev.repositories.Tag;

import java.util.List;

import com.preplane.dev.assets.SQLResult;
import com.preplane.dev.models.Tag;

public interface TagRepository {
    SQLResult<List<Tag>> findAll(int limit, int offset);

}
