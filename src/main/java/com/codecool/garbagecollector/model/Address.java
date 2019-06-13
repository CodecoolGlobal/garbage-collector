package com.codecool.garbagecollector.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    public Address() {
        this.coordinate = new Coordinate();
    }

    @Embedded
    @Expose
    private Coordinate coordinate;

    @Expose
    private String city;

    @Expose
    private String country;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}