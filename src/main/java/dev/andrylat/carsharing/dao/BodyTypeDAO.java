package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.BodyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class BodyTypeDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BodyTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BodyType> getAll() {
        return jdbcTemplate.query("SELECT * FROM body_types", new BodyTypeMapper());
    }

    public BodyType getById(int id) {
        return jdbcTemplate.query("SELECT * FROM body_types WHERE id=?", new BodyTypeMapper(), id)
                .stream()
                .findAny()
                .orElseThrow(() -> new EmptyResultDataAccessException("Could not find record with id " + id, 1));
    }

    public BodyType add(BodyType bodyType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement("INSERT INTO body_types(name) VALUES(?)", new String[]{"id"});
                    statement.setString(1, bodyType.getName());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int insertedRecordId = (int) insertedRecordIdOptional
                .orElseThrow(() -> new NullPointerException("Data insertion has failed! Couldn't get inserted record!"));

        return getById(insertedRecordId);
    }

    public BodyType updateById(BodyType updatedBodyType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement =
                            connection.prepareStatement("UPDATE body_types SET name=? WHERE id=?", new String[]{"id"});
                    statement.setString(1, updatedBodyType.getName());
                    statement.setInt(2, updatedBodyType.getId());
                    return statement;
                },
                keyHolder);

        Optional<Number> updatedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int updatedRecordId = (int) updatedRecordIdOptional
                .orElseThrow(() -> new NullPointerException("Data update has failed! Couldn't get inserted record!"));

        return getById(updatedRecordId);
    }

    public boolean deleteById(int id) {
        int deletedRowsNumber = jdbcTemplate.update("DELETE FROM body_types WHERE id=?", id);

        return deletedRowsNumber > 0;
    }

    public boolean deleteAll() {
        List<BodyType> bodyTypeList = getAll();
        int deletedRowsNumber = jdbcTemplate.update("DELETE FROM body_types");

        return deletedRowsNumber == bodyTypeList.size();
    }

}
