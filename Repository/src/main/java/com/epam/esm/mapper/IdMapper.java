package com.epam.esm.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class IdMapper implements RowMapper<Long> {
    private final static String NEW_ID = "new_id";

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  rs.getLong(NEW_ID);
    }
}
