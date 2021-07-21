package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.CarBrandMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.CarBrand;
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
public class CarBrandDAO {
    private static final String GET_ALL_CAR_BRANDS_SQL_QUERY = "SELECT * FROM car_brands ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_CAR_BRAND_BY_ID_SQL_QUERY = "SELECT * FROM car_brands WHERE id=?";
    private static final String ADD_CAR_BRAND_SQL_QUERY = "INSERT INTO car_brands(name) VALUES(?)";
    private static final String UPDATE_CAR_BRAND_BY_ID_SQL_QUERY = "UPDATE car_brands SET name=? WHERE id=?";
    private static final String DELETE_CAR_BRAND_BY_ID_SQL_QUERY = "DELETE FROM car_brands WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarBrandDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CarBrand> getAll(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        return jdbcTemplate.query(GET_ALL_CAR_BRANDS_SQL_QUERY, new CarBrandMapper(), pageable.getPageSize(), pageable.getPageNumber());
    }

    public CarBrand getById(long id) {
        return jdbcTemplate.queryForObject(GET_CAR_BRAND_BY_ID_SQL_QUERY, new CarBrandMapper(), id);
    }

    public CarBrand add(CarBrand carBrand) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement(ADD_CAR_BRAND_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, carBrand.getName());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        long insertedRecordId = insertedRecordIdOptional.map(Number::longValue)
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        carBrand.setId(insertedRecordId);
        return carBrand;
    }

    public boolean updateById(CarBrand updatedCarBrand) {
        int updatedRowsNumber = jdbcTemplate.update(UPDATE_CAR_BRAND_BY_ID_SQL_QUERY, updatedCarBrand.getName(), updatedCarBrand.getId());

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_CAR_BRAND_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
