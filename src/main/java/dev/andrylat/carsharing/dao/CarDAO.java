package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.CarMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.Car;
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
public class CarDAO {
    private static final String GET_ALL_CARS_SQL_QUERY = "SELECT * FROM cars ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_CAR_BY_ID_SQL_QUERY = "SELECT * FROM cars WHERE id=?";
    private static final String DELETE_CAR_BY_ID_SQL_QUERY = "DELETE FROM cars WHERE id=?";

    private static final String ADD_CAR_SQL_QUERY = "INSERT INTO cars (model_id, reg_plate, rent_cost_per_min, color, " +
            "photo) VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_CAR_BY_ID_SQL_QUERY = "UPDATE cars SET model_id = ?, reg_plate = ?, " +
            "rent_cost_per_min = ?, color = ?, photo = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> getAll(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        return jdbcTemplate.query(GET_ALL_CARS_SQL_QUERY, new CarMapper(), pageable.getPageSize(), pageable.getPageNumber());
    }

    public Car getById(long id) {
        return jdbcTemplate.queryForObject(GET_CAR_BY_ID_SQL_QUERY, new CarMapper(), id);
    }

    public Car add(Car car) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_CAR_SQL_QUERY, new String[]{"id"});
                    statement.setLong(1, car.getModelId());
                    statement.setString(2, car.getRegistrationPlate());
                    statement.setInt(3, car.getRentCostPerMin());
                    statement.setString(4, car.getColor());
                    statement.setString(5, car.getPhoto());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        long insertedRecordId = insertedRecordIdOptional.map(Number::longValue)
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        car.setId(insertedRecordId);
        return car;
    }

    public boolean updateById(Car updatedCar) {
        int updatedRowsNumber = jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_BY_ID_SQL_QUERY);
                    statement.setLong(1, updatedCar.getModelId());
                    statement.setString(2, updatedCar.getRegistrationPlate());
                    statement.setInt(3, updatedCar.getRentCostPerMin());
                    statement.setString(4, updatedCar.getColor());
                    statement.setString(5, updatedCar.getColor());
                    statement.setLong(6, updatedCar.getId());
                    return statement;
                }
        );

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_CAR_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
