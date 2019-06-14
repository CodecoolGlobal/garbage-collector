package com.codecool.garbagecollector.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codecool.garbagecollector.service.DocumentationService;

@WebServlet(urlPatterns = {"/api", "/api/", "/api/help/"})
public class DocumentationServlet extends HttpServlet {

    private DocumentationService documentationService;

    public DocumentationServlet() {
        documentationService = new DocumentationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonDoc = documentationService.getJSONDocumentation();
        resp.getWriter().write(jsonDoc);
    }
}