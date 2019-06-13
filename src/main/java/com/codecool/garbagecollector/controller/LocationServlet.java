package com.codecool.garbagecollector.controller;

import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.service.LocationService;
import com.codecool.garbagecollector.service.UtilityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet(urlPatterns = {"/api/location/*"})
public class LocationServlet extends HttpServlet {

    private LocationService locationService;
    private Gson gson;

    public LocationServlet() {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.locationService = new LocationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = UtilityService.getQueryParameters(req);

        List<Location> result = locationService.getLocationByParameters(queryParams);

        resp.getWriter().write(gson.toJson(result));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = UtilityService.getQueryParameters(req);

        List<Location> deletedLocation = locationService.deleteLocation(queryParams);

        resp.getWriter().write("Deleted following object:");
        resp.getWriter().write(gson.toJson(deletedLocation));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("DUMMY RESPONSE");
    }
}
