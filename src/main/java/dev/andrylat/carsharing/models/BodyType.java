package dev.andrylat.carsharing.models;

public class BodyType {
    private int id;
    private String name;
    private static final int PRIME_ODD_NUMBER = 31;

    public BodyType() {
    }

    public BodyType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        BodyType bodyType = (BodyType) other;

        return id == bodyType.id &&
                name.equals(bodyType.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = PRIME_ODD_NUMBER * result + id;
        result = PRIME_ODD_NUMBER * result + (name == null ? 0 : name.hashCode());
        return result;
    }

}