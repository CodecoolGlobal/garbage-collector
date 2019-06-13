package com.codecool.garbagecollector.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.codecool.garbagecollector.InvalidParametersException;
import com.codecool.garbagecollector.model.Status;

public class StatusService {

    private EntityManager entityManager;
    private CriteriaBuilder builder;

    public StatusService() {
        entityManager = EMFactory.getEntityManager();
        builder = entityManager.getCriteriaBuilder();
    }

    public long getValidStatusFrom(Map<String, String[]> inputs) throws InvalidParametersException {
        List<Status> statuses = getStatuses();
        Map<String, Long> statusesMap = getStatusesMap(statuses);
        if (inputs.containsKey("status") && statusesMap.containsKey(inputs.get("status")[0])) {
            return statusesMap.get(inputs.get("status")[0]);
        } else {
            throw new InvalidParametersException("Unable to proceed because of invalid status parameter.");
        }
    }

    private List<Status> getStatuses() {
        CriteriaQuery<Status> query = builder.createQuery(Status.class);
        Root<Status> statusRoot = query.from(Status.class);
        query.select(statusRoot);
        TypedQuery<Status> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    private Map<String, Long> getStatusesMap(List<Status> statuses) {
        Map<String, Long> statusesMap = new HashMap<>();
        for (Status status : statuses) {
            statusesMap.put(status.getName(), status.getId());
        }
        return statusesMap;
    }
}