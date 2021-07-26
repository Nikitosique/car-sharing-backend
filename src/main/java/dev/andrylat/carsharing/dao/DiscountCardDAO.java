package dev.andrylat.carsharing.dao;

import dev.andrylat.carsharing.dao.mappers.DiscountCardMapper;
import dev.andrylat.carsharing.exceptions.RecordNotFoundException;
import dev.andrylat.carsharing.models.DiscountCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class DiscountCardDAO {
    private static final String GET_ALL_DISCOUNT_CARDS_SQL_QUERY = "SELECT * FROM discount_cards ORDER BY id LIMIT ? OFFSET ?";
    private static final String GET_DISCOUNT_CARD_BY_ID_SQL_QUERY = "SELECT * FROM discount_cards WHERE id=?";
    private static final String DELETE_DISCOUNT_CARD_BY_ID_SQL_QUERY = "DELETE FROM discount_cards WHERE id=?";

    private static final String ADD_DISCOUNT_CARD_SQL_QUERY = "INSERT INTO discount_cards (card_number, discount_value)" +
            "VALUES (?,?)";

    private static final String UPDATE_DISCOUNT_CARD_BY_ID_SQL_QUERY = "UPDATE discount_cards SET card_number=?, " +
            "discount_value = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiscountCardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DiscountCard> getAll(int pageNumber, int pageSize) {
        int omittedRecordsNumber = pageNumber * pageSize;

        return jdbcTemplate.query(GET_ALL_DISCOUNT_CARDS_SQL_QUERY, new DiscountCardMapper(), pageSize, omittedRecordsNumber);
    }

    public DiscountCard getById(long id) {
        return jdbcTemplate.queryForObject(GET_DISCOUNT_CARD_BY_ID_SQL_QUERY, new DiscountCardMapper(), id);
    }

    public DiscountCard add(DiscountCard card) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(ADD_DISCOUNT_CARD_SQL_QUERY, new String[]{"id"});
                    statement.setString(1, card.getCardNumber());
                    statement.setInt(2, card.getDiscountValue());
                    return statement;
                },
                keyHolder);

        Optional<Number> insertedRecordIdOptional = Optional.ofNullable(keyHolder.getKey());
        long insertedRecordId = insertedRecordIdOptional.map(Number::longValue)
                .orElseThrow(() -> new RecordNotFoundException("Data insertion has failed! Couldn't get inserted record!"));

        card.setId(insertedRecordId);
        return card;
    }

    public boolean updateById(DiscountCard updatedDiscountCard) {
        int updatedRowsNumber = jdbcTemplate.update(UPDATE_DISCOUNT_CARD_BY_ID_SQL_QUERY, updatedDiscountCard.getCardNumber(),
                updatedDiscountCard.getDiscountValue(), updatedDiscountCard.getId());

        return updatedRowsNumber > 0;
    }

    public boolean deleteById(long id) {
        int deletedRowsNumber = jdbcTemplate.update(DELETE_DISCOUNT_CARD_BY_ID_SQL_QUERY, id);

        return deletedRowsNumber > 0;
    }

}
