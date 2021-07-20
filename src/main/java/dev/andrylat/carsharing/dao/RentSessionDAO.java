package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.RentSessionMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.RentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class RentSessionDAO {
    private static final String GET_ALL_RENT_SESSIONS_SQL_QUERY = "SELECT * FROM rent_sessions";
    private static final String GET_RENT_SESSION_BY_ID_SQL_QUERY = "SELECT * FROM rent_sessions WHERE id=?";
    private static final String DELETE_RENT_SESSION_BY_ID_SQL_QUERY = "DELETE FROM rent_sessions WHERE id=?";

    private static final String ADD_RENT_SESSION_SQL_QUERY = "INSERT INTO rent_sessions (customer_id, car_id, " +
            "rent_time_interval, rent_session_cost) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_RENT_SESSION_BY_ID_SQL_QUERY = "UPDATE rent_sessions SET customer_id = ?, " +
            "car_id = ?, rent_time_interval = ?, rent_session_cost = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RentSessionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RentSession> getAll() {
        return jdbcTemplate.query(GET_ALL_RENT_SESSIONS_SQL_QUERY, new RentSessionMapper());
    }

    public RentSession getById(int id) {
        return jdbcTemplate.queryForObject(GET_RENT_SESSION_BY_ID_SQL_QUERY, new RentSessionMapper(), id);
    }

    public RentSession add(RentSession rentSession) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_RENT_SESSION_SQL_QUERY, new String[]{"id"});
                    statement.setInt(1, rentSession.getCustomerId());
                    statement.setInt(2, rentSession.getCarId());
                    statement.setObject(3, rentSession.getRentTimeInterval());
                    statement.setInt(4, rentSession.getRentSessionCost());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int insertedRecordId = (int) insertedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        rentSession.setId(insertedRecordId);
        return rentSession;
    }

    public RentSession updateById(RentSession updatedRentSession) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(UPDATE_RENT_SESSION_BY_ID_SQL_QUERY, new String[]{"id"});
                    statement.setInt(1, updatedRentSession.getCustomerId());
                    statement.setInt(2, updatedRentSession.getCarId());
                    statement.setObject(3, updatedRentSession.getRentTimeInterval());
                    statement.setInt(4, updatedRentSession.getRentSessionCost());
                    statement.setInt(5, updatedRentSession.getId());
                    return statement;
                },
                keyHolder);

        Optional<Number> updatedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int updatedRecordId = (int) updatedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data update has failed! Couldn't get updated record!"));

        updatedRentSession.setId(updatedRecordId);
        return updatedRentSession;
    }

    public boolean deleteById(int id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_RENT_SESSION_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
