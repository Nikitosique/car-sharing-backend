package dev.andrylat.carsharing.models;

import org.postgresql.util.PGInterval;

import javax.validation.constraints.*;
import java.util.Objects;

public class RentSession {
    private long id;

    @Positive(message = "Customer id should be positive integer")
    private long customerId;

    @Positive(message = "Car id should be positive integer")
    private long carId;

    @Positive(message = "Car id should be positive integer")
    private int rentSessionCost;

    @NotNull(message = "Rent time interval shouldn't be null")
    private PGInterval rentTimeInterval;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
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
                && Objects.equals(rentTimeInterval, rentSession.rentTimeInterval);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + (int) (carId ^ (carId >>> 32));
        result = 31 * result + rentSessionCost;
        result = 31 * result + (rentTimeInterval == null ? 0 : rentTimeInterval.hashCode());

        return result;
    }

}
