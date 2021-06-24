package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.BodyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class BodyTypeDAO {
    private static final String getAllQuery = "SELECT * FROM body_types";
    private static final String getByIdQuery = "SELECT * FROM body_types WHERE id=?";
    private static final String addQuery = "INSERT INTO body_types(name) VALUES(?)";
    private static final String updateByIdQuery = "UPDATE body_types SET name=? WHERE id=?";
    private static final String deleteByIdQuery = "DELETE FROM body_types WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BodyTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BodyType> getAll() {
        return jdbcTemplate.query(getAllQuery, new BodyTypeMapper());
    }

    public BodyType getById(int id) {
        return jdbcTemplate.queryForObject(getByIdQuery, new BodyTypeMapper(), id);
    }

    public BodyType add(BodyType bodyType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement(addQuery, new String[]{"id"});
                    statement.setString(1, bodyType.getName());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int insertedRecordId = (int) insertedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        return getById(insertedRecordId);
    }

    public BodyType updateById(BodyType updatedBodyType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement(updateByIdQuery, new String[]{"id"});
                    statement.setString(1, updatedBodyType.getName());
                    statement.setInt(2, updatedBodyType.getId());
                    return statement;
                },
                keyHolder);

        Optional<Number> updatedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int updatedRecordId = (int) updatedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data update has failed! Couldn't get inserted record!"));

        return getById(updatedRecordId);
    }

    public boolean deleteById(int id) {
        int deletedRowsNumber = jdbcTemplate.update(deleteByIdQuery, id);

        return deletedRowsNumber > 0;
    }

}
