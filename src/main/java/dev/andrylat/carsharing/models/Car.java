package dev.andrylat.carsharing.models;

public class Car {
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

}
