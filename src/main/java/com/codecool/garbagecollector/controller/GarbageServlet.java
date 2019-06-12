package com.codecool.garbagecollector.controller;

import com.codecool.garbagecollector.service.UtilityService;
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
        Map<String, String[]> queryParams = UtilityService.getQueryParameters(req);

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(queryParams));
    }


    }



