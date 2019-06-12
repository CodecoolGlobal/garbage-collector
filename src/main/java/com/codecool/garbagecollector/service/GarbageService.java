package com.codecool.garbagecollector.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Garbage;
import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.model.Status;
import com.codecool.garbagecollector.model.Type;

public class GarbageService {

    private EntityManager entityManager;
    private CriteriaBuilder builder;
    private CriteriaQuery<Garbage> query;
    private Root<Garbage> garbageRoot;

    public GarbageService() {
        entityManager = EMFactory.getEntityManager();
        builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(Garbage.class);
        garbageRoot = query.from(Garbage.class);
        query.select(garbageRoot);
    }

    public List<Garbage> getStockBy(Map<String, String[]> inputs) {
        List<Predicate> predicates = new ArrayList<>();
        Join<Garbage, Status> statusRoot = garbageRoot.join("status", JoinType.LEFT);
        Join<Garbage, Type> typeRoot = garbageRoot.join("type", JoinType.LEFT);
        Join<Garbage, Location> locationRoot = garbageRoot.join("location", JoinType.LEFT);
        Join<Location, Address> addressRoot = locationRoot.join("address", JoinType.LEFT);

        UtilityService.addParameterToQuery(inputs, garbageRoot, "id", predicates, builder);
        UtilityService.addParameterToQuery(inputs, garbageRoot, "quantity", predicates, builder);
        UtilityService.addParameterToQuery(inputs, typeRoot, "name", predicates, builder);
        UtilityService.addParameterToQuery(inputs, statusRoot, "name", predicates, builder);
        UtilityService.addParameterToQuery(inputs, locationRoot, "name", predicates, builder);
        UtilityService.addParameterToQuery(inputs, addressRoot, "city", predicates, builder);
        UtilityService.addParameterToQuery(inputs, addressRoot, "country", predicates, builder);

        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Garbage> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}