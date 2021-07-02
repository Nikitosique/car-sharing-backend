package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.RentSession;
import org.postgresql.util.PGInterval;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RentSessionMapper implements RowMapper<RentSession> {
    @Override
    public RentSession mapRow(ResultSet resultSet, int i) throws SQLException {
        RentSession rentSession = new RentSession();

        rentSession.setId(resultSet.getInt("id"));
        rentSession.setCustomerId(resultSet.getInt("customer_id"));
        rentSession.setCarId(resultSet.getInt("car_id"));
        rentSession.setRentSessionCost(resultSet.getInt("rent_session_cost"));
        rentSession.setRentTimeInterval((PGInterval) resultSet.getObject("rent_time_interval"));

        return rentSession;
    }

}
