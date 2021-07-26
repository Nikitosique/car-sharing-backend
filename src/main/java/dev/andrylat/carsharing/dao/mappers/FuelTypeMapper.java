package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.FuelType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuelTypeMapper implements RowMapper<FuelType> {

    @Override
    public FuelType mapRow(ResultSet resultSet, int i) throws SQLException {
        FuelType fuelType = new FuelType();

        fuelType.setId(resultSet.getLong("id"));
        fuelType.setName(resultSet.getString("name"));

        return fuelType;
    }

}
