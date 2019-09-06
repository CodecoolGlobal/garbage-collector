package com.codecool.garbagecollector.main;

import com.codecool.garbagecollector.model.*;
import com.codecool.garbagecollector.service.EMFactory;
import com.codecool.garbagecollector.service.LocationService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {


        EntityManager entityManager = EMFactory.getEntityManager();

        populateDB(entityManager);

        entityManager.clear();

        LocationService ls = new LocationService();
        entityManager.close();
    }

    private static void populateDB(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();

        Garbage garbage = new Garbage();
        garbage.setDescription("Description");

        Type type = new Type();
        type.setName("plastic");
        type.setUnit("kg");
        garbage.setType(type);

        Location location = new Location();
        location.setName("Codecool");

        Status status = new Status();
        status.setName("Available");

        Address address = new Address();
        address.setCity("Krakow");

        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(7382.293029);
        coordinate.setLongitude(384.2930);

        address.setCoordinate(coordinate);
        location.setAddress(address);
        location.addGarbage(garbage);
        status.addGarbage(garbage);

        transaction.begin();
        entityManager.persist(status);
        entityManager.persist(location);
        entityManager.persist(garbage);
        transaction.commit();

        Garbage garbage2 = new Garbage();
        garbage2.setDescription("Another");

        Type type2 = new Type();
        type2.setName("bio");
        type2.setUnit("kg");
        garbage2.setType(type2);

        Location location2 = new Location();
        location2.setName("Sklepik");

        Address address2 = new Address();
        address2.setCity("Poznan");

        Coordinate coordinate2 = new Coordinate();
        coordinate2.setLatitude(5454.5454);
        coordinate2.setLongitude(33.4343);

        address2.setCoordinate(coordinate2);
        location2.setAddress(address2);
        location2.addGarbage(garbage2);
        status.addGarbage(garbage2);

        transaction.begin();
        entityManager.persist(location2);
        entityManager.persist(garbage2);
        transaction.commit();
    }
}