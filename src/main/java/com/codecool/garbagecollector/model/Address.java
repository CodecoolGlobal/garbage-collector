package com.codecool.garbagecollector.model;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Embedded
    private Coordinate coordinate;

    private String city;

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