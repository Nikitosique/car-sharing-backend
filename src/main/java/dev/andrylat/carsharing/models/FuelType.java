package dev.andrylat.carsharing.models;

import java.util.Objects;

public class FuelType {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private String name;

    public FuelType() {
    }

    public FuelType(int id, String name) {
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

        FuelType fuelType = (FuelType) other;

        return id == fuelType.id
                && Objects.equals(name, fuelType.name);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + (name == null ? 0 : name.hashCode());

        return result;
    }

}