package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.CarModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarModelMapper implements RowMapper<CarModel> {

    @Override
    public CarModel mapRow(ResultSet resultSet, int i) throws SQLException {
        CarModel carModel = new CarModel();

        carModel.setId(resultSet.getLong("id"));
        carModel.setBodyId(resultSet.getLong("body_id"));
        carModel.setBrandId(resultSet.getLong("brand_id"));
        carModel.setFuelId(resultSet.getLong("fuel_id"));
        carModel.setName(resultSet.getString("model_name"));
        carModel.setEngineDisplacement(resultSet.getDouble("engine_displacement"));
        carModel.setGearboxType(resultSet.getString("gearbox_type"));
        carModel.setProductionYear(resultSet.getInt("production_year"));

        return carModel;
    }

}