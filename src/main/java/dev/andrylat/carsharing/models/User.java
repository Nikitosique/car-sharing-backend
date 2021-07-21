package dev.andrylat.carsharing.models;

import java.util.Objects;

public class User {
    private long id;
    private long discountCardId;
    private String email;
    private String password;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDiscountCardId() {
        return discountCardId;
    }

    public void setDiscountCardId(long discountCardId) {
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

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (discountCardId ^ (discountCardId >>> 32));
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());
        result = 31 * result + (type == null ? 0 : type.hashCode());

        return result;
    }

}
