package com.codecool.garbagecollector.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.codecool.garbagecollector.InvalidParametersException;

class ServletUtility {

    static Map<String, String[]> getQueryParameters(HttpServletRequest req) {
        Map<String, String[]> queryParams = new HashMap<>();

        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            queryParams.put(entry.getKey(), entry.getValue());
        }

        String uri = req.getRequestURI();
        String[] uriSplit = uri.split("/");

        if (uriSplit.length > 3) {
            queryParams.put("id", new String[]{uriSplit[3]});
        }
        return queryParams;
    }

    static void redirectInvalidParameters(HttpServletRequest req, HttpServletResponse resp, InvalidParametersException e) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("exceptionMessage", e.getMessage());
        RequestDispatcher rd = req.getRequestDispatcher("/api/invalid");
        rd.forward(req, resp);
    }

    static <T> String getFormattedJSON(List<T> objects) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        return gson.toJson(objects);
    }
}