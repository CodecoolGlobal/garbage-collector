package com.codecool.garbagecollector.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class UtilityService {

    static void addParameterToQuery(Map<String, String[]> queryParams, Path root, String paramName, List<Predicate> predicates, CriteriaBuilder cb) {
        List<Predicate> orPredicates = new ArrayList<>();
        if (queryParams.containsKey(paramName)) {
            for (String param : queryParams.get(paramName)) {
                if (paramName.equals("quantity")) {
                    orPredicates.add(cb.lessThanOrEqualTo(root.get(paramName), param));
                } else {
                    orPredicates.add(cb.equal(root.get(paramName), param));
                }
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[]{})));
        }
    }

    static void addNestedParameterToQuery(Map<String, String[]> queryParams, Path root, String paramName, List<Predicate> predicates, CriteriaBuilder cb) {
        List<Predicate> orPredicates = new ArrayList<>();
        if (queryParams.containsKey(paramName)) {
            for (String param : queryParams.get(paramName)) {
                orPredicates.add(cb.equal(root.get(paramName).get("name"), param));
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[]{})));
        }
    }

}