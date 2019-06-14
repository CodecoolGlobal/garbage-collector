package com.codecool.garbagecollector.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DocumentationService {

    private Map<String, String> getPopulatedDocumentation() {
        Map<String, String> documentation = new HashMap<>();
        String garbageRoot = "http://localhost:8080/api/garbage/";
        String locationRoot = "http://localhost:8080/api/location/";
        documentation.put("stock", garbageRoot);
        documentation.put("search_garbage_by_id", garbageRoot + "id || {?id=value}");
        documentation.put("search_garbage_by_type", garbageRoot + "{?type=type}");
        documentation.put("search_garbage_by_quantity", garbageRoot + "{?quantity=value}");
        documentation.put("search_garbage_by_location", garbageRoot + "{?location=name}");
        documentation.put("search_garbage_by_status", garbageRoot + "{?status=value}");
        documentation.put("search_garbage_by_city", garbageRoot + "{?city=value}");
        documentation.put("search_garbage_by_country", garbageRoot + "{?country=value}");
        documentation.put("search_garbage_by_multiple_parameters", garbageRoot + "{?type=value&location=name&status=value&city=value}");
        documentation.put("create_garbage", garbageRoot + "{?type=value&quantity=value&location=location_id&status=value&description=text}");
        documentation.put("update_garbage", garbageRoot + "{?id=value&type=value&quantity=value&location=location_id&status=value&description=text}");
        documentation.put("delete_garbage_by_id", garbageRoot + "id || {?id=value}");
        documentation.put("search_location_by_id", locationRoot + "id || {?id=value}");
        documentation.put("search_location_by_name", locationRoot + "{?name=value}");
        documentation.put("search_location_by_city", locationRoot + "{?city=value}");
        documentation.put("search_location_by_country", locationRoot + "{?country=value}");
        documentation.put("search_location_by_multiple_parameters", garbageRoot + "{?name=value&country=value&city=value}");
        documentation.put("create_location", locationRoot + "{?name=value&phone=number&city=value&country=value&longitude=value&latitude=value}");
        documentation.put("update_location", locationRoot + "{?id=value&name=value&phone=number&city=value&country=value&longitude=value&latitude=value}");
        documentation.put("delete_location", locationRoot + "id || {?id=value}");
        return documentation;
    }

    public String getJSONDocumentation() {
        Map<String, String> documentation = getPopulatedDocumentation();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.toJson(documentation);
    }
}