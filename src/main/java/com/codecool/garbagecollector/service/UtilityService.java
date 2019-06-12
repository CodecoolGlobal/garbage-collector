package com.codecool.garbagecollector.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import javax.servlet.http.HttpServletRequest;

public class UtilityService {

    public static Map<String, String[]> getQueryParameters(HttpServletRequest req) {
        Map<String, String[]> queryParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            queryParams.put(entry.getKey(), entry.getValue());
        }

        String uri = req.getRequestURI();
        String[] uriSplit = uri.split("/");

        if (uriSplit.length > 2) {
            queryParams.put("id", new String[]{uriSplit[2]});
        }
        return queryParams;
    }

    static void addParameterToQuery(Map<String, String[]> queryParams, Path root, String paramName, List<Predicate> predicates, CriteriaBuilder cb) {
        List<Predicate> orPredicates = new ArrayList<>();
        if (queryParams.containsKey(paramName)) {
            for (String param : queryParams.get(paramName)) {
                orPredicates.add(cb.equal(root.get(paramName), param));
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[]{})));
        }
    }
}
