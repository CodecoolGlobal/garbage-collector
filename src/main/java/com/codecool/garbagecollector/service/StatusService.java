package com.codecool.garbagecollector.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import com.codecool.garbagecollector.InvalidParametersException;
import com.codecool.garbagecollector.model.Status;

class StatusService {

    private EntityManager entityManager;
    private CriteriaBuilder builder;

    StatusService() {
        entityManager = EMFactory.getEntityManager();
        builder = entityManager.getCriteriaBuilder();
    }

    Status getValidStatusFrom(Map<String, String[]> inputs) throws InvalidParametersException {
        List<Status> statuses = getStatuses();
        Map<String, Long> statusesMap = getStatusesMap(statuses);
        if (inputs.containsKey("status") && statusesMap.containsKey(inputs.get("status")[0])) {
            long id = statusesMap.get(inputs.get("status")[0]);
            return getStatusById(id);
        } else {
            throw new InvalidParametersException("Unable to proceed because of invalid status parameter.");
        }
    }

    private Status getStatusById(long id) {
        CriteriaQuery<Status> criteriaQuery = builder.createQuery(Status.class);
        Root<Status> locationRoot = criteriaQuery.from(Status.class);
        ParameterExpression<Long> idParameter = builder.parameter(Long.class);
        criteriaQuery.select(locationRoot).where(builder.equal(locationRoot.get("id"), idParameter));
        TypedQuery<Status> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter(idParameter, id);
        return typedQuery.getSingleResult();
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