package com.preplane.dev.rowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("count");
    }
}
