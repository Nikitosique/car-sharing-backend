package dev.andrylat.carsharing.models;

import java.util.Objects;

public class CarBrand {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private String name;

    public CarBrand() {
    }

    public CarBrand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + (name == null ? 0 : name.hashCode());

        return result;
    }

}
