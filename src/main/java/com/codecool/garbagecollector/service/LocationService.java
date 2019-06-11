package com.codecool.garbagecollector.service;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Garbage;
import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.model.Type;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

public class LocationService {

    public void getLocationByParameters(EntityManager em){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> q = cb.createQuery(Location.class);
        Root<Location> locationRoot = q.from(Location.class);

        Join<Garbage, Address> addressJoin = locationRoot.join("address", JoinType.LEFT);

        q.where(
                cb.or(
                        cb.equal(addressJoin.get("city"), "Krakow"),
                        cb.equal(addressJoin.get("city"), "Poznan")
                ));

        CriteriaQuery<Location> select = q.select(locationRoot);

        TypedQuery<Location> query = em.createQuery(select);

        List<Location> results = query.getResultList();

        for (Location location:
             results) {
            System.out.println(location.getName());
        }
    }
}
