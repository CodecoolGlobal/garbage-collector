package com.codecool.garbagecollector.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.codecool.garbagecollector.InvalidParametersException;
import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Garbage;
import com.codecool.garbagecollector.model.Location;

public class GarbageService {

    private EntityManager entityManager;
    private CriteriaBuilder builder;

    public GarbageService() {
        entityManager = EMFactory.getEntityManager();
        builder = entityManager.getCriteriaBuilder();
    }

    public List<Garbage> getStockBy(Map<String, String[]> inputs) {
        CriteriaQuery<Garbage> query = builder.createQuery(Garbage.class);
        Root<Garbage> garbageRoot = query.from(Garbage.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Location, Address> addressRoot = garbageRoot.join("location", JoinType.LEFT)
                .join("address", JoinType.LEFT);

        UtilityService.addParameterToQuery(inputs, garbageRoot, "id", predicates, builder);
        UtilityService.addParameterToQuery(inputs, garbageRoot, "quantity", predicates, builder);
        UtilityService.addNestedParameterToQuery(inputs, garbageRoot, "status", predicates, builder);
        UtilityService.addNestedParameterToQuery(inputs, garbageRoot, "type", predicates, builder);
        UtilityService.addNestedParameterToQuery(inputs, garbageRoot, "location", predicates, builder);
        UtilityService.addParameterToQuery(inputs, addressRoot, "city", predicates, builder);
        UtilityService.addParameterToQuery(inputs, addressRoot, "country", predicates, builder);

        query.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<Garbage> select = query.select(garbageRoot);
        query.orderBy(builder.asc(garbageRoot.get("id")));
        TypedQuery<Garbage> typedQuery = entityManager.createQuery(select);
        return typedQuery.getResultList();
    }

    public void deleteGarbageById(Map<String, String[]> inputs) throws InvalidParametersException {
        Long id = getValidId(inputs);
        deleteGarbageBy(id);
    }

    private void deleteGarbageBy(long id) {
        entityManager.getTransaction().begin();
        CriteriaDelete<Garbage> criteriaDelete = builder.createCriteriaDelete(Garbage.class);
        Root<Garbage> garbageRoot = criteriaDelete.from(Garbage.class);
        criteriaDelete.where(builder.equal(garbageRoot.get("id"), id));
        entityManager.createQuery(criteriaDelete).executeUpdate();
        entityManager.getTransaction().commit();
    }

    private Long getValidId(Map<String, String[]> inputs) throws InvalidParametersException {
        if (inputs.containsKey("id") && inputs.size() == 1) {
            return Long.valueOf(inputs.get("id")[0]);
        } else {
            throw new InvalidParametersException("Unable to handle DELETE by given parameters.");
        }
    }
}