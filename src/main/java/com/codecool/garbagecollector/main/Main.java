package com.codecool.garbagecollector.main;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Coordinate;
import com.codecool.garbagecollector.model.Garbage;
import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.model.Status;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GarbageCollector");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Garbage garbage = new Garbage();
        garbage.setDescription("Description");

        Location location = new Location();
        location.setName("Codecool");

        Status status = new Status();
        status.setName("Available");

        Address address = new Address();
        address.setCity("Cracow");

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

        entityManager.clear();

        Location location1 = entityManager.find(Location.class, 1L);
        System.out.println("======================================");
        System.out.println(location1.getName());
        for(Garbage garbage1 : location1.getStock()) {
            System.out.println(garbage.getDescription());
        }
        System.out.println("======================================");
        entityManager.close();
        entityManagerFactory.close();
    }
}