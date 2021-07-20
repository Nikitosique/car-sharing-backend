package dev.andrylat.carsharing.models;

import java.util.Objects;

public class User {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private int discountCardId;
    private String email;
    private String password;
    private String type;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        User user = (User) other;

        return id == user.id
                && discountCardId == user.discountCardId
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(type, user.type);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + discountCardId;
        result = PRIME_ODD_NUMBER * result + (email == null ? 0 : email.hashCode());
        result = PRIME_ODD_NUMBER * result + (password == null ? 0 : password.hashCode());
        result = PRIME_ODD_NUMBER * result + (type == null ? 0 : type.hashCode());

        return result;
    }

}
