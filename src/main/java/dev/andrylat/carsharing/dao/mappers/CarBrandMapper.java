package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.CarBrand;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarBrandMapper implements RowMapper<CarBrand> {

    @Override
    public CarBrand mapRow(ResultSet resultSet, int i) throws SQLException {
        CarBrand carBrand = new CarBrand();

        carBrand.setId(resultSet.getLong("id"));
        carBrand.setName(resultSet.getString("name"));

        return carBrand;
    }

}
