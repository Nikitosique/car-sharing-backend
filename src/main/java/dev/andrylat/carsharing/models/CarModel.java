package dev.andrylat.carsharing.models;

public class CarModel {
    private int id;
    private int bodyId;
    private int brandId;
    private int fuelId;
    private int productionYear;
    private double engineDisplacement;
    private String name;
    private String gearboxType;

    public CarModel() {
    }

    public CarModel(int bodyId, int brandId, int fuelId, String name, double engineDisplacement, String gearboxType, int productionYear) {
        this.bodyId = bodyId;
        this.brandId = brandId;
        this.fuelId = fuelId;
        this.name = name;
        this.engineDisplacement = engineDisplacement;
        this.gearboxType = gearboxType;
        this.productionYear = productionYear;
    }

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
}
