package dev.andrylat.carsharing.models;

public class DiscountCard {
    private int id;
    private int discountValue;
    private String cardNumber;

    public DiscountCard() {
    }

    public DiscountCard(int discountValue, String cardNumber) {
        this.discountValue = discountValue;
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
