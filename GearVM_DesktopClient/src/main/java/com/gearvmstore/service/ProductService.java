package com.gearvmstore.service;

import com.gearvmstore.model.Product;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProductService extends ApiService {
    private static final String url = "http://localhost:8080/api/products/";

    public static boolean postRequest(Product p) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        JSONObject json = new JSONObject();
        json.put("name", p.getName());
        json.put("brand", p.getBrand());
        json.put("type", p.getType());
        json.put("price", p.getPrice());
        json.put("quantity", p.getQuantity());
        StringEntity se = new StringEntity(json.toString(), StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        System.out.println(response);
        return !response.toString().isEmpty();
    }

    public static boolean putRequest(Product p) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPut request = new HttpPut(url + p.getId());
        JSONObject json = new JSONObject();
        json.put("name", p.getName());
        json.put("brand", p.getBrand());
        json.put("type", p.getType());
        json.put("price", p.getPrice());
        json.put("quantity", p.getQuantity());
        StringEntity se = new StringEntity(json.toString(), StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return !response.toString().isEmpty();
    }

    public static boolean patchDescriptionRequest(String description, String id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPatch request = new HttpPatch(url + id);
        StringEntity se = new StringEntity(description, StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return !response.toString().isEmpty();
    }

    public static boolean patchImageUriRequest(String uri, String id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPatch request = new HttpPatch(url + "image/" + id);
        StringEntity se = new StringEntity(uri, StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return !response.toString().isEmpty();
    }
}
