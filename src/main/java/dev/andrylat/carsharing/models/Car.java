package dev.andrylat.carsharing.models;

import java.util.Objects;

public class Car {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private int modelId;
    private int rentCostPerMin;
    private String registrationPlate;
    private String color;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getRentCostPerMin() {
        return rentCostPerMin;
    }

    public void setRentCostPerMin(int rentCostPerMin) {
        this.rentCostPerMin = rentCostPerMin;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        Car car = (Car) other;

        return id == car.id
                && modelId == car.modelId
                && rentCostPerMin == car.rentCostPerMin
                && Objects.equals(registrationPlate, car.registrationPlate)
                && Objects.equals(color, car.color)
                && Objects.equals(photo, car.photo);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + modelId;
        result = PRIME_ODD_NUMBER * result + rentCostPerMin;
        result = PRIME_ODD_NUMBER * result + (registrationPlate == null ? 0 : registrationPlate.hashCode());
        result = PRIME_ODD_NUMBER * result + (color == null ? 0 : color.hashCode());
        result = PRIME_ODD_NUMBER * result + (photo == null ? 0 : photo.hashCode());

        return result;
    }

}
