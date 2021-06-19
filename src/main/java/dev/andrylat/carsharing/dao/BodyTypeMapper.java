package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.BodyType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BodyTypeMapper implements RowMapper<BodyType> {

    @Override
    public BodyType mapRow(ResultSet resultSet, int i) throws SQLException {
        BodyType bodyType = new BodyType();

        bodyType.setId(resultSet.getInt("id"));
        bodyType.setName(resultSet.getString("name"));

        return bodyType;
    }
}
