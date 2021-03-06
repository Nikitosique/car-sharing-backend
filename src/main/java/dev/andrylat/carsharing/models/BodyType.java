package dev.andrylat.carsharing.models;

import javax.validation.constraints.*;
import java.util.Objects;

public class BodyType {
    private long id;

    @NotEmpty(message = "Body type name shouldn't be empty")
    private String name;

    public BodyType() {
    }

    public BodyType(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        BodyType bodyType = (BodyType) other;

        return id == bodyType.id
                && Objects.equals(name, bodyType.name);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name == null ? 0 : name.hashCode());

        return result;
    }

}