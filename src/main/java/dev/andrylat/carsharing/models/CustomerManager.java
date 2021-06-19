package dev.andrylat.carsharing.models;

public class CustomerManager {
    private int customerId;
    private int managerId;

    public CustomerManager() {
    }

    public CustomerManager(int customerId, int managerId) {
        this.customerId = customerId;
        this.managerId = managerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

}
