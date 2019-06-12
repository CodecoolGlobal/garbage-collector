package com.codecool.garbagecollector.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
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
    private List<Predicate> predicates;

    public GarbageService() {
        entityManager = Persistence.createEntityManagerFactory("GarbageCollector").createEntityManager();
        builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(Garbage.class);
        garbageRoot = query.from(Garbage.class);
        query.select(garbageRoot);
    }

    private void addInputToQuery(Map<String, String[]> inputs, Path root, String column) {
        List<Predicate> columnPredicates = new ArrayList<>();
        if (inputs.containsKey(column)) {
            for (String row : inputs.get(column)) {
                columnPredicates.add(builder.equal(root.get(column), row));
            }
            predicates.add(builder.or(columnPredicates.toArray(new Predicate[]{})));
        }
    }

    public List<Garbage> getStockBy(Map<String, String[]> inputs) {
        predicates = new ArrayList<>();
        Join<Garbage, Status> statusRoot = garbageRoot.join("status", JoinType.LEFT);
        Join<Garbage, Type> typeRoot = garbageRoot.join("type", JoinType.LEFT);
        Join<Garbage, Location> locationRoot = garbageRoot.join("location", JoinType.LEFT);
        Join<Location, Address> addressRoot = locationRoot.join("address", JoinType.LEFT);

        addInputToQuery(inputs, garbageRoot, "id");
        addInputToQuery(inputs, garbageRoot, "quantity");
        addInputToQuery(inputs, typeRoot, "name");
        addInputToQuery(inputs, statusRoot, "name");
        addInputToQuery(inputs, locationRoot, "name");
        addInputToQuery(inputs, addressRoot, "city");
        addInputToQuery(inputs, addressRoot, "country");

        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Garbage> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}