package dev.andrylat.carsharing.models;

import java.util.Objects;

public class CarModel {
    private long id;
    private long bodyId;
    private long brandId;
    private long fuelId;
    private int productionYear;
    private double engineDisplacement;
    private String name;
    private String gearboxType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBodyId() {
        return bodyId;
    }

    public void setBodyId(long bodyId) {
        this.bodyId = bodyId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public long getFuelId() {
        return fuelId;
    }

    public void setFuelId(long fuelId) {
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

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (bodyId ^ (bodyId >>> 32));
        result = 31 * result + (int) (brandId ^ (brandId >>> 32));
        result = 31 * result + (int) (fuelId ^ (fuelId >>> 32));
        result = 31 * result + productionYear;

        long engineDisplacementBits = Double.doubleToLongBits(engineDisplacement);
        result = 31 * result + (int) (engineDisplacementBits ^ (engineDisplacementBits >>> 32));

        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (gearboxType == null ? 0 : gearboxType.hashCode());

        return result;
    }

}
