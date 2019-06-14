package com.codecool.garbagecollector.controller;

import java.util.List;
import java.util.Map;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.garbagecollector.model.Garbage;
import com.codecool.garbagecollector.service.GarbageService;
import com.codecool.garbagecollector.InvalidParametersException;

@WebServlet(urlPatterns = {"/api/garbage/*"})
public class GarbageServlet extends HttpServlet {

    private GarbageService garbageService;

    public GarbageServlet() {
        garbageService = new GarbageService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);
        List<Garbage> stock = garbageService.getStockBy(queryParams);
        String json = ServletUtility.getFormattedJSON(stock);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);
        try {
            garbageService.createGarbageFrom(queryParams);
            resp.sendRedirect("/api/garbage/");
        } catch (InvalidParametersException e) {
            ServletUtility.redirectInvalidParameters(req, resp, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);
        try {
            garbageService.updateGarbageFrom(queryParams);
            resp.sendRedirect("/api/garbage/");
        } catch (InvalidParametersException e) {
            ServletUtility.redirectInvalidParameters(req, resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> queryParams = ServletUtility.getQueryParameters(req);
        try {
            garbageService.deleteGarbageById(queryParams);
            resp.sendRedirect("/api/garbage/");
        } catch (InvalidParametersException e) {
            ServletUtility.redirectInvalidParameters(req, resp, e);
        }
    }
}