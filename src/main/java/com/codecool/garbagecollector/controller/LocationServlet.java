package com.codecool.garbagecollector.controller;

import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.service.LocationService;
import com.codecool.garbagecollector.service.UtilityService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = {"/location/*"})
public class LocationServlet extends HttpServlet {

    LocationService locationService;

    public LocationServlet() {
        this.locationService = new LocationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = UtilityService.getQueryParameters(req);

        List<Location> result = locationService.getLocationByParameters(queryParams);

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("DUMMY RESPONSE");
    }
}
