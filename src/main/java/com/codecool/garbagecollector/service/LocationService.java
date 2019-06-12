package com.codecool.garbagecollector.service;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Location;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationService {
    private EntityManager entityManager;
    private CriteriaBuilder cb;
    private List<Predicate> predicates;

    public LocationService() {
        this.entityManager = EMFactory.getEntityManager();
        this.cb = entityManager.getCriteriaBuilder();
    }

    public List<Location> getLocationByParameters(Map<String, String[]> queryParams) {
        this.predicates = new ArrayList<>();

        CriteriaQuery<Location> q = cb.createQuery(Location.class);
        Root<Location> locationRoot = q.from(Location.class);
        Join<Location, Address> addressJoin = locationRoot.join("address", JoinType.LEFT);

        addParameterToQuery(queryParams, locationRoot, "id");
        addParameterToQuery(queryParams, locationRoot, "name");
        addParameterToQuery(queryParams, addressJoin, "city");
        addParameterToQuery(queryParams, addressJoin, "country");

        q.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<Location> select = q.select(locationRoot);
        TypedQuery<Location> query = entityManager.createQuery(select);

        return query.getResultList();
    }

    private void addParameterToQuery(Map<String, String[]> queryParams, Path root, String paramName) {
        List<Predicate> orPredicates = new ArrayList<>();
        if (queryParams.containsKey(paramName)) {
            for (String param : queryParams.get(paramName)) {
                orPredicates.add(cb.equal(root.get(paramName), param));
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[]{})));
        }
    }
}
