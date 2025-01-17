package com.gearvmdesktop.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class ApiService {
    public static final String staticUrl = "http://localhost:8080/api";

    public static BufferedReader getAllRequest(String tableName) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/api/" + tableName);
        HttpResponse response = client.execute(request);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    public static BufferedReader getAllByFilterRequest(String tableName, String filter) throws IOException {
        HttpClient client = new DefaultHttpClient();

        String filterEncoded = URLEncoder.encode(filter, "UTF-8").replaceAll("\\+", "%20");

        HttpGet request = new HttpGet("http://localhost:8080/api/" + tableName + "get-all-filter?filter=" + filterEncoded);
        HttpResponse response = client.execute(request);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }

    public static BufferedReader getRequest(String tableName, String id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/api/" + tableName + "/" + id);
        HttpResponse response = client.execute(request);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}
