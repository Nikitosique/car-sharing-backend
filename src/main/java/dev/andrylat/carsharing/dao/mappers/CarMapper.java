package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet resultSet, int i) throws SQLException {
        Car car = new Car();

        car.setId(resultSet.getLong("id"));
        car.setModelId(resultSet.getLong("model_id"));
        car.setRentCostPerMin(resultSet.getInt("rent_cost_per_min"));
        car.setRegistrationPlate(resultSet.getString("reg_plate"));
        car.setColor(resultSet.getString("color"));
        car.setPhoto(resultSet.getString("photo"));

        return car;
    }

}
