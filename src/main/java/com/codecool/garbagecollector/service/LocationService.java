package com.codecool.garbagecollector.service;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Location;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationService {

    public void getLocationByParameters(EntityManager em, Map<String, String[]> queryParams) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> q = cb.createQuery(Location.class);
        Root<Location> locationRoot = q.from(Location.class);
        Join<Location, Address> addressJoin = locationRoot.join("address", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        List<Predicate> idPredicates = new ArrayList<>();
        if (queryParams.containsKey("id")) {
            for (String idParam : queryParams.get("id")) {
                idPredicates.add(cb.equal(locationRoot.get("id"), idParam));
            }
            predicates.add(cb.or(idPredicates.toArray(new Predicate[]{})));
        }

        List<Predicate> namePredicates = new ArrayList<>();
        if (queryParams.containsKey("name")) {
            for (String nameParam : queryParams.get("name")) {
                namePredicates.add(cb.equal(locationRoot.get("name"), nameParam));
            }
            predicates.add(cb.or(namePredicates.toArray(new Predicate[]{})));
        }

        List<Predicate> cityPredicates = new ArrayList<>();
        if (queryParams.containsKey("city")) {
            for (String cityParam : queryParams.get("city")) {
                cityPredicates.add(cb.equal(addressJoin.get("city"), cityParam));
            }
            predicates.add(cb.or(cityPredicates.toArray(new Predicate[]{})));
        }

        q.where(predicates.toArray(new Predicate[]{}));

        CriteriaQuery<Location> select = q.select(locationRoot);

        TypedQuery<Location> query = em.createQuery(select);

        List<Location> results = query.getResultList();

        for (Location location :
                results) {
            System.out.println(location.getName());
        }
    }
}
