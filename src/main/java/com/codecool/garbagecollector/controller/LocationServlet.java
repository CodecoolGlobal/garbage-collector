package com.codecool.garbagecollector.controller;

import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.service.LocationService;
import com.codecool.garbagecollector.service.UtilityService;

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

        String json = UtilityService.getFormattedJSON(result);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("DUMMY RESPONSE");
    }
}
