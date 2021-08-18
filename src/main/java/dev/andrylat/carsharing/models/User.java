package dev.andrylat.carsharing.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class User {
    private long id;

    @NotNull(message = "Discount Card Id shouldn't be empty")
    private Long discountCardId;

    @Email(message = "Email is incorrect")
    @NotEmpty(message = "Email shouldn't be empty")
    private String email;

    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

    @NotEmpty(message = "User type shouldn't be empty")
    @Pattern(regexp = "(is|customer|manager)", message = "User type should be customer or manager")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getDiscountCardId() {
        return discountCardId;
    }

    public void setDiscountCardId(Long discountCardId) {
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
                && Objects.equals(discountCardId, user.discountCardId)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(type, user.type);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (discountCardId == null ? 0 : discountCardId.hashCode());
        result = 31 * result + (email == null ? 0 : email.hashCode());
        result = 31 * result + (password == null ? 0 : password.hashCode());
        result = 31 * result + (type == null ? 0 : type.hashCode());

        return result;
    }

}
