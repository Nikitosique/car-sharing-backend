package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.CustomerManagerMapper;
import dev.andrylat.carsharing.dao.mappers.UserMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private static final String GET_ALL_USERS_SQL_QUERY = "SELECT * FROM users";
    private static final String GET_USER_BY_ID_SQL_QUERY = "SELECT * FROM users WHERE id=?";
    private static final String DELETE_USER_BY_ID_SQL_QUERY = "DELETE FROM users WHERE id=?";
    private static final String ADD_USER_SQL_QUERY = "INSERT INTO users (email, password, discount_card_id, type) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_USER_BY_ID_SQL_QUERY = "UPDATE users SET email = ?, password = ?, " +
            "discount_card_id = ?, type = ? WHERE id = ?";

    private static final String GET_CUSTOMERS_BY_MANAGER_ID_SQL_QUERY = "SELECT customer_id FROM customers_managers " +
            "WHERE manager_id = ?";
    private static final String ASSIGN_CUSTOMER_TO_MANAGER_SQL_QUERY = "INSERT INTO customers_managers VALUES (?, ?)";
    private static final String UNASSIGN_CUSTOMER_FROM_MANAGER_SQL_QUERY = "DELETE FROM customers_managers " +
            "WHERE customer_id = ? AND manager_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_USERS_SQL_QUERY, new UserMapper());
    }

    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL_QUERY, new UserMapper(), id);
    }

    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_USER_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, user.getEmail());
                    statement.setString(2, user.getPassword());
                    statement.setInt(3, user.getDiscountCardId());
                    statement.setString(4, user.getType());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int insertedRecordId = (int) insertedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        user.setId(insertedRecordId);
        return user;
    }

    public User updateById(User updatedUser) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, updatedUser.getEmail());
                    statement.setString(2, updatedUser.getPassword());
                    statement.setInt(3, updatedUser.getDiscountCardId());
                    statement.setString(4, updatedUser.getType());
                    statement.setInt(5, updatedUser.getId());
                    return statement;
                },
                keyHolder);

        Optional<Number> updatedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        int updatedRecordId = (int) updatedRecordIdOptional
                .orElseThrow(() -> new RecordNotFoundException("Data update has failed! Couldn't get updated record!"));

        updatedUser.setId(updatedRecordId);
        return updatedUser;
    }

    public boolean deleteById(int id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_USER_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

    public List<Integer> getCustomersIdByManagerId(int managerId) {
        return jdbcTemplate.query(GET_CUSTOMERS_BY_MANAGER_ID_SQL_QUERY, new CustomerManagerMapper(), managerId);
    }

    public boolean assignCustomerToManager(int customerId, int managerId) {
        int insertedRowsNumber = jdbcTemplate.update(ASSIGN_CUSTOMER_TO_MANAGER_SQL_QUERY, customerId, managerId);
        return insertedRowsNumber > 0;
    }

    public boolean unassignCustomerFromManager(int customerId, int managerId) {
        int deletedRowsNumber = jdbcTemplate.update(UNASSIGN_CUSTOMER_FROM_MANAGER_SQL_QUERY, customerId, managerId);
        return deletedRowsNumber > 0;
    }

}
