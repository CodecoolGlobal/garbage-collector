package com.codecool.garbagecollector.service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
}
