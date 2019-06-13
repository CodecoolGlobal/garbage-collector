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
import com.codecool.garbagecollector.service.UtilityService;

@WebServlet(urlPatterns = {"/garbage/*"})
public class GarbageServlet extends HttpServlet {

    private GarbageService garbageService;

    public GarbageServlet() {
        garbageService = new GarbageService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> queryParams = UtilityService.getQueryParameters(req);
        List<Garbage> stock = garbageService.getStockBy(queryParams);
        String json = UtilityService.getFormattedJSON(stock);
        resp.getWriter().write(json);
    }
}



