package dev.andrylat.carsharing.models;

import javax.validation.constraints.NotEmpty;

public class BodyType {
    private int id;
    private String name;

    public BodyType() {
    }

    public BodyType(String name) {
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

}
