package dev.andrylat.carsharing.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CarBrand {
    private long id;

    @NotEmpty(message = "Car brand name should not be empty")
    @Size(min = 2, max = 20, message = "Car brand name should be between 2 and 20 characters long")
    private String name;

    public CarBrand() {
    }

    public CarBrand(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        CarBrand carBrand = (CarBrand) other;

        return id == carBrand.id
                && Objects.equals(name, carBrand.name);
    }

    @Override
    public int hashCode() {

        int result = 17;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name == null ? 0 : name.hashCode());

        return result;
    }

}
