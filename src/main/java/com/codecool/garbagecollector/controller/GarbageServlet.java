package com.codecool.garbagecollector.controller;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/garbage/*"})
public class GarbageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = getQueryParameters(req);

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(queryParams));
    }

    private Map<String, String[]> getQueryParameters(HttpServletRequest req) {
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
