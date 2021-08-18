package dev.andrylat.carsharing.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class DiscountCard {
    private long id;

    @Min(value = 0, message = "Minimal discount value is 0%")
    @Max(value = 15, message = "Maximal discount value is 15%")
    private int discountValue;

    @NotEmpty(message = "Discount card number shouldn't be empty")
    private String cardNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        DiscountCard card = (DiscountCard) other;

        return id == card.id
                && discountValue == card.discountValue
                && Objects.equals(cardNumber, card.cardNumber);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + discountValue;
        result = 31 * result + (cardNumber == null ? 0 : cardNumber.hashCode());

        return result;
    }

}
