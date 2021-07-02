package dev.andrylat.carsharing.models;

import org.postgresql.util.PGInterval;

public class RentSession {
    private static final int PRIME_ODD_NUMBER = 31;

    private int id;
    private int customerId;
    private int carId;
    private int rentSessionCost;
    private PGInterval rentTimeInterval;

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

    public PGInterval getRentTimeInterval() {
        return rentTimeInterval;
    }

    public void setRentTimeInterval(PGInterval rentTimeInterval) {
        this.rentTimeInterval = rentTimeInterval;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        RentSession rentSession = (RentSession) other;

        return id == rentSession.id
                && customerId == rentSession.customerId
                && carId == rentSession.carId
                && rentSessionCost == rentSession.rentSessionCost
                && rentTimeInterval.equals(rentSession.rentTimeInterval);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + customerId;
        result = PRIME_ODD_NUMBER * result + carId;
        result = PRIME_ODD_NUMBER * result + rentSessionCost;
        result = PRIME_ODD_NUMBER * result + (rentTimeInterval == null ? 0 : rentTimeInterval.hashCode());

        return result;
    }

}
