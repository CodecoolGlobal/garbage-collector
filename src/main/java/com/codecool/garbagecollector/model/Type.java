package com.codecool.garbagecollector.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Embeddable;

@Embeddable
public class Type {

    @Expose
    private String name;

    @Expose
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
