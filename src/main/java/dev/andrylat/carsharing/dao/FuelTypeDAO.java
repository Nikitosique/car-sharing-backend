package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.FuelTypeMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.FuelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FuelTypeDAO {
    private static final String GET_FUEL_TYPES_NUMBER_SQL_QUERY = "SELECT COUNT(*) FROM fuel_types";
    private static final String GET_ALL_FUEL_TYPES_SQL_QUERY = "SELECT * FROM fuel_types ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_FUEL_TYPE_BY_ID_SQL_QUERY = "SELECT * FROM fuel_types WHERE id=?";
    private static final String ADD_FUEL_TYPE_SQL_QUERY = "INSERT INTO fuel_types(name) VALUES(?)";
    private static final String UPDATE_FUEL_TYPE_BY_ID_SQL_QUERY = "UPDATE fuel_types SET name=? WHERE id=?";
    private static final String DELETE_FUEL_TYPE_BY_ID_SQL_QUERY = "DELETE FROM fuel_types WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FuelTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getRecordsNumber() {
        return jdbcTemplate.queryForObject(GET_FUEL_TYPES_NUMBER_SQL_QUERY, Long.class);
    }

    public List<FuelType> getAll(int pageNumber, int pageSize) {
        int omittedRecordsNumber = pageNumber * pageSize;

        return jdbcTemplate.query(GET_ALL_FUEL_TYPES_SQL_QUERY, new FuelTypeMapper(), pageSize, omittedRecordsNumber);
    }

    public FuelType getById(long id) {
        return jdbcTemplate.queryForObject(GET_FUEL_TYPE_BY_ID_SQL_QUERY, new FuelTypeMapper(), id);
    }

    public FuelType add(FuelType fuelType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement(ADD_FUEL_TYPE_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, fuelType.getName());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        long insertedRecordId = insertedRecordIdOptional.map(Number::longValue)
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        fuelType.setId(insertedRecordId);
        return fuelType;
    }

    public boolean updateById(FuelType updatedFuelType) {
        int updatedRowsNumber = jdbcTemplate.update(UPDATE_FUEL_TYPE_BY_ID_SQL_QUERY, updatedFuelType.getName(), updatedFuelType.getId());

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_FUEL_TYPE_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
