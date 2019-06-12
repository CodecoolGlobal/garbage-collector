package com.codecool.garbagecollector.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {

    private static EntityManagerFactory emf;

    private EMFactory(){}

    public static EntityManager getEntityManager(){
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("GarbageCollector");
            return emf.createEntityManager();
        } else {
            return emf.createEntityManager();
        }
    }

}
