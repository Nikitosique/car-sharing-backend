package dev.andrylat.carsharing.models;

import java.time.Duration;

public class RentSession {
    private int id;
    private int customerId;
    private int carId;
    private int rentSessionCost;
    private Duration rentTimeInterval;

    public RentSession() {
    }

    public RentSession(int customerId, int carId, int rentSessionCost, Duration rentTimeInterval) {
        this.customerId = customerId;
        this.carId = carId;
        this.rentSessionCost = rentSessionCost;
        this.rentTimeInterval = rentTimeInterval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getRentSessionCost() {
        return rentSessionCost;
    }

    public void setRentSessionCost(int rentSessionCost) {
        this.rentSessionCost = rentSessionCost;
    }

    public Duration getRentTimeInterval() {
        return rentTimeInterval;
    }

    public void setRentTimeInterval(Duration rentTimeInterval) {
        this.rentTimeInterval = rentTimeInterval;
    }

}
