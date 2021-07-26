package dev.andrylat.carsharing.models;

import java.util.Objects;

public class FuelType {
    private long id;
    private String name;

    public FuelType() {
    }

    public FuelType(long id, String name) {
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

        FuelType fuelType = (FuelType) other;

        return id == fuelType.id
                && Objects.equals(name, fuelType.name);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name == null ? 0 : name.hashCode());

        return result;
    }

}