package dev.andrylat.carsharing.models;

public class Car {
    private int id;
    private int modelId;
    private int rentCostPerMin;
    private String regPlate;
    private String color;
    private String photo;

    public Car() {
    }

    public Car(int modelId, int rentCostPerMin, String regPlate, String color, String photo) {
        this.modelId = modelId;
        this.rentCostPerMin = rentCostPerMin;
        this.regPlate = regPlate;
        this.color = color;
        this.photo = photo;
    }

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

    public String getRegPlate() {
        return regPlate;
    }

    public void setRegPlate(String regPlate) {
        this.regPlate = regPlate;
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
