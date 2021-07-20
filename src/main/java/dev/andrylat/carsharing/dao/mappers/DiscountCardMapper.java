package dev.andrylat.carsharing.dao.mappers;

import dev.andrylat.carsharing.models.DiscountCard;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardMapper implements RowMapper<DiscountCard> {

    @Override
    public DiscountCard mapRow(ResultSet resultSet, int i) throws SQLException {
        DiscountCard discountCard = new DiscountCard();

        discountCard.setId(resultSet.getInt("id"));
        discountCard.setDiscountValue(resultSet.getInt("discount_value"));
        discountCard.setCardNumber(resultSet.getString("card_number"));

        return discountCard;
    }

}
