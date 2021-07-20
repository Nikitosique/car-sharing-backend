package dev.andrylat.carsharing.models;

import java.util.Objects;

public class DiscountCard {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private int discountValue;
    private String cardNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + discountValue;
        result = PRIME_ODD_NUMBER * result + (cardNumber == null ? 0 : cardNumber.hashCode());

        return result;
    }

}
