package com.codecool.garbagecollector.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {

    @Expose
    private double longitude;

    @Expose
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}