package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.CarModelMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.CarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class CarModelDAO {
    private static final String GET_ALL_CAR_MODELS_SQL_QUERY = "SELECT * FROM car_models ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_CAR_MODEL_BY_ID_SQL_QUERY = "SELECT * FROM car_models WHERE id=?";
    private static final String DELETE_CAR_MODEL_BY_ID_SQL_QUERY = "DELETE FROM car_models WHERE id=?";

    private static final String ADD_CAR_MODEL_SQL_QUERY = "INSERT INTO car_models (body_id, brand_id, model_name, " +
            "engine_displacement, fuel_id, gearbox_type, production_year) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CAR_MODEL_BY_ID_SQL_QUERY = "UPDATE car_models SET body_id=?, brand_id=?, model_name=?, " +
            "engine_displacement=?, fuel_id=?, gearbox_type=?, production_year=? WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarModelDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CarModel> getAll(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        return jdbcTemplate.query(GET_ALL_CAR_MODELS_SQL_QUERY, new CarModelMapper(), pageable.getPageSize(), pageable.getPageNumber());
    }

    public CarModel getById(long id) {
        return jdbcTemplate.queryForObject(GET_CAR_MODEL_BY_ID_SQL_QUERY, new CarModelMapper(), id);
    }

    public CarModel add(CarModel carModel) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_CAR_MODEL_SQL_QUERY, new String[]{"id"});
                    statement.setLong(1, carModel.getBodyId());
                    statement.setLong(2, carModel.getBrandId());
                    statement.setString(3, carModel.getName());
                    statement.setDouble(4, carModel.getEngineDisplacement());
                    statement.setLong(5, carModel.getFuelId());
                    statement.setString(6, carModel.getGearboxType());
                    statement.setInt(7, carModel.getProductionYear());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        long insertedRecordId = insertedRecordIdOptional.map(Number::longValue)
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        carModel.setId(insertedRecordId);
        return carModel;
    }

    public boolean updateById(CarModel updatedCarModel) {
        int updatedRowsNumber = jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_MODEL_BY_ID_SQL_QUERY);
                    statement.setLong(1, updatedCarModel.getBodyId());
                    statement.setLong(2, updatedCarModel.getBrandId());
                    statement.setString(3, updatedCarModel.getName());
                    statement.setDouble(4, updatedCarModel.getEngineDisplacement());
                    statement.setLong(5, updatedCarModel.getFuelId());
                    statement.setString(6, updatedCarModel.getGearboxType());
                    statement.setInt(7, updatedCarModel.getProductionYear());
                    statement.setLong(8, updatedCarModel.getId());
                    return statement;
                }
        );

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_CAR_MODEL_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
