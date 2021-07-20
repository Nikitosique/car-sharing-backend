package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.CarModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarModelMapper implements RowMapper<CarModel> {

    @Override
    public CarModel mapRow(ResultSet resultSet, int i) throws SQLException {
        CarModel carModel = new CarModel();

        carModel.setId(resultSet.getInt("id"));
        carModel.setBodyId(resultSet.getInt("body_id"));
        carModel.setBrandId(resultSet.getInt("brand_id"));
        carModel.setFuelId(resultSet.getInt("fuel_id"));
        carModel.setName(resultSet.getString("model_name"));
        carModel.setEngineDisplacement(resultSet.getDouble("engine_displacement"));
        carModel.setGearboxType(resultSet.getString("gearbox_type"));
        carModel.setProductionYear(resultSet.getInt("production_year"));

        return carModel;
    }

}