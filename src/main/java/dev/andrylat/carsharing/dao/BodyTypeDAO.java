package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.models.BodyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BodyTypeDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BodyTypeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BodyType> showAll() {
        return jdbcTemplate.query("SELECT * FROM body_types", new BodyTypeMapper());
    }

    public BodyType showById(int id) {
        return jdbcTemplate.query("SELECT * FROM body_types WHERE id=?", new BodyTypeMapper(), id)
                .stream().findAny().orElse(null);
    }

    public void add(BodyType bodyType) {
        jdbcTemplate.update("INSERT INTO body_types VALUES(?)", bodyType.getName());
    }

    public void updateById(int id, BodyType updatedBodyType) {
        jdbcTemplate.update("UPDATE body_types SET name=? WHERE id=?", updatedBodyType.getName(), id);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM body_types WHERE id=?", id);
    }

}
