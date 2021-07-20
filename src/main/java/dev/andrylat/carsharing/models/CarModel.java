package dev.andrylat.carsharing.models;

import java.util.Objects;

public class CarModel {
    private static final int PRIME_ODD_NUMBER = 31;
    private static final int BITS_NUMBER = 32;

    private int id;
    private int bodyId;
    private int brandId;
    private int fuelId;
    private int productionYear;
    private double engineDisplacement;
    private String name;
    private String gearboxType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(double engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        CarModel carModel = (CarModel) other;

        return id == carModel.id
                && bodyId == carModel.bodyId
                && brandId == carModel.brandId
                && fuelId == carModel.fuelId
                && productionYear == carModel.productionYear
                && Double.compare(engineDisplacement, carModel.engineDisplacement) == 0
                && Objects.equals(name, carModel.name)
                && Objects.equals(gearboxType, carModel.gearboxType);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + bodyId;
        result = PRIME_ODD_NUMBER * result + brandId;
        result = PRIME_ODD_NUMBER * result + fuelId;
        result = PRIME_ODD_NUMBER * result + productionYear;

        long engineDisplacementBits = Double.doubleToLongBits(engineDisplacement);
        result = PRIME_ODD_NUMBER * result + (int) (engineDisplacementBits ^ (engineDisplacementBits >>> BITS_NUMBER));

        result = PRIME_ODD_NUMBER * result + (name == null ? 0 : name.hashCode());
        result = PRIME_ODD_NUMBER * result + (gearboxType == null ? 0 : gearboxType.hashCode());

        return result;
    }

}
