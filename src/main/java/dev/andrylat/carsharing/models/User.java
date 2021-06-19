package dev.andrylat.carsharing.models;

public class User {
    private int id;
    private int discountCardId;
    private String email;
    private String password;
    private String type;

    public User() {
    }

    public User(int discountCardId, String email, String password, String type) {
        this.discountCardId = discountCardId;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscountCardId() {
        return discountCardId;
    }

    public void setDiscountCardId(int discountCardId) {
        this.discountCardId = discountCardId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
