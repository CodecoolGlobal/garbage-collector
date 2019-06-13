package com.codecool.garbagecollector.service;

import com.codecool.garbagecollector.model.Address;
import com.codecool.garbagecollector.model.Location;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Location> deleteLocation(Map<String, String[]> queryParams) {
        if (deleteParametersValid(queryParams)) {
            List<Location> result = getLocationByParameters(queryParams);
            entityManager.getTransaction().begin();
            entityManager.remove(result.get(0));
            entityManager.getTransaction().commit();
            return result;
        }
        return new ArrayList<>();

    }

    private boolean deleteParametersValid(Map<String, String[]> queryParams) {
        return queryParams.containsKey("id") && queryParams.get("id").length == 1 && queryParams.size() == 1;
    }

    public List<Location> createLocation(Map<String, String[]> queryParams) {
        if (createParametersValid(queryParams)) {
            Location newLocation = new Location();
            if (queryParams.containsKey("name")) {
                newLocation.setName(queryParams.get("name")[0]);
            }
            if (queryParams.containsKey("phone")) {
                newLocation.setPhoneNumber(queryParams.get("phone")[0]);
            }
            if (queryParams.containsKey("city")) {
                newLocation.getAddress().setCity(queryParams.get("city")[0]);
            }
            if (queryParams.containsKey("country")) {
                newLocation.getAddress().setCity(queryParams.get("country")[0]);
            }
            if (queryParams.containsKey("longitude")) {
                newLocation.getAddress().getCoordinate().setLongitude(Double.parseDouble(queryParams.get("longitude")[0]));
            }
            if (queryParams.containsKey("latitude")) {
                newLocation.getAddress().getCoordinate().setLatitude(Double.parseDouble(queryParams.get("latitude")[0]));
            }

            entityManager.getTransaction().begin();
            entityManager.persist(newLocation);
            entityManager.getTransaction().commit();

            return Arrays.asList(newLocation);

        }
        return new ArrayList<>();
    }

    private boolean createParametersValid(Map<String, String[]> queryParams) {
        for (String[] values : queryParams.values()) {
            if (values.length != 1){
                return false;
            }
        }
        return true;
    }
}

