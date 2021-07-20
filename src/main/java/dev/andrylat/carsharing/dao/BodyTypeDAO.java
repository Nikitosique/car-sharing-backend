package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.BodyTypeMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.BodyType;
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
public class BodyTypeDAO {
    private static final String GET_ALL_BODY_TYPES_SQL_QUERY = "SELECT * from body_types ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_BODY_TYPE_BY_ID_SQL_QUERY = "SELECT * FROM body_types WHERE id=?";
    private static final String ADD_BODY_TYPE_SQL_QUERY = "INSERT INTO body_types(name) VALUES(?)";
    private static final String UPDATE_BODY_TYPE_BY_ID_SQL_QUERY = "UPDATE body_types SET name=? WHERE id=?";
    private static final String DELETE_BODY_TYPE_BY_ID_SQL_QUERY = "DELETE FROM body_types WHERE id=?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BodyTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BodyType> getAll(int pageNumber, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        return jdbcTemplate.query(GET_ALL_BODY_TYPES_SQL_QUERY, new BodyTypeMapper(), pageable.getPageSize(), pageable.getPageNumber());
    }

    public BodyType getById(long id) {
        return jdbcTemplate.queryForObject(GET_BODY_TYPE_BY_ID_SQL_QUERY, new BodyTypeMapper(), id);
    }

    public BodyType add(BodyType bodyType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_BODY_TYPE_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, bodyType.getName());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int insertedRecordId = (int) insertedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        bodyType.setId(insertedRecordId);
        return bodyType;
    }

    public boolean updateById(BodyType updatedBodyType) {
        int updatedRowsNumber = jdbcTemplate.update(UPDATE_BODY_TYPE_BY_ID_SQL_QUERY, updatedBodyType.getName(), updatedBodyType.getId());

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_BODY_TYPE_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
