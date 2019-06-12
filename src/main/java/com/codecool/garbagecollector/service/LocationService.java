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

    public LocationService() {
        this.entityManager = EMFactory.getEntityManager();
        this.cb = entityManager.getCriteriaBuilder();
    }

    public List<Location> getLocationByParameters(Map<String, String[]> queryParams) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<Location> q = cb.createQuery(Location.class);
        Root<Location> locationRoot = q.from(Location.class);
        Join<Location, Address> addressJoin = locationRoot.join("address", JoinType.LEFT);

        UtilityService.addParameterToQuery(queryParams, locationRoot, "id", predicates, cb);
        UtilityService.addParameterToQuery(queryParams, locationRoot, "name", predicates, cb);
        UtilityService.addParameterToQuery(queryParams, addressJoin, "city", predicates, cb);
        UtilityService.addParameterToQuery(queryParams, addressJoin, "country", predicates, cb);

        q.where(predicates.toArray(new Predicate[]{}));
        CriteriaQuery<Location> select = q.select(locationRoot);
        TypedQuery<Location> query = entityManager.createQuery(select);

        return query.getResultList();
    }
}
