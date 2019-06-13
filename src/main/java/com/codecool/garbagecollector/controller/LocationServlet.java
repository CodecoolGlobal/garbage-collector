package com.codecool.garbagecollector.controller;

import com.codecool.garbagecollector.InvalidParametersException;
import com.codecool.garbagecollector.model.Location;
import com.codecool.garbagecollector.service.LocationService;

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

    public LocationServlet() {
        this.locationService = new LocationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);

        List<Location> result = locationService.getLocationByParameters(queryParams);

        String json = ServletUtility.getFormattedJSON(result);
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);

        List<Location> deletedLocation = null;

        try {
            deletedLocation = locationService.deleteLocation(queryParams);
        } catch (InvalidParametersException e) {
            ServletUtility.redirectInvalidParameters(req, resp, e);
        }

        resp.getWriter().write("Deleted following object:");
        resp.getWriter().write(ServletUtility.getFormattedJSON(deletedLocation));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);

        List<Location> newLocation = null;
        try {
            newLocation = locationService.createLocation(queryParams);
        } catch (InvalidParametersException e) {
            ServletUtility.redirectInvalidParameters(req, resp, e);
        }

        resp.getWriter().write("Created following object:");
        resp.getWriter().write(ServletUtility.getFormattedJSON(newLocation));
    }
}
