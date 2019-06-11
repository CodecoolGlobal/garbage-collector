package com.codecool.garbagecollector.model;

import javax.persistence.Embeddable;

@Embeddable
public class Type {

    private String name;

    private String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
