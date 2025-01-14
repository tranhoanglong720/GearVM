package com.gearvmdesktop.service;

import com.gearvmstore.GearVM.model.Employee;
import com.gearvmstore.GearVM.model.dto.user.LoginDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EmployeeService extends ApiService {
    private static final String url = staticUrl + "/employees/";

    public static boolean postRequest(Employee e) throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        JSONObject json = new JSONObject();
        json.put("name", e.getName());
        json.put("gender", e.getGender());
        json.put("phoneNumber", e.getPhoneNumber());
        json.put("salary", e.getSalary());

        String dateFormat = String.format("%02d", e.getDateOfBirth().getDayOfMonth())
                + "-" + String.format("%02d", e.getDateOfBirth().getMonthValue())
                + "-" + e.getDateOfBirth().getYear();
        json.put("dateOfBirth", dateFormat);

        json.put("address", e.getAddress());
        json.put("email", e.getEmail());
        json.put("role", e.getRole());
        json.put("nationalId", e.getNationalId());
        json.put("workStatus", "true");
        StringEntity se = new StringEntity(json.toString(), StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return response.getStatusLine().getStatusCode() == 200;
    }

    public static boolean putRequest(Employee e) throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpPut request = new HttpPut(url + e.getId());
        JSONObject json = new JSONObject();
        json.put("name", e.getName());
        json.put("gender", e.getGender());
        json.put("phoneNumber", e.getPhoneNumber());
        json.put("salary", e.getSalary());

        String dateFormat = String.format("%02d", e.getDateOfBirth().getDayOfMonth())
                + "-" + String.format("%02d", e.getDateOfBirth().getMonthValue())
                + "-" + e.getDateOfBirth().getYear();
        json.put("dateOfBirth", dateFormat);

        json.put("address", e.getAddress());
        json.put("email", e.getEmail());
        json.put("role", e.getRole());
        json.put("nationalId", e.getNationalId());
        StringEntity se = new StringEntity(json.toString(), StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return response.getStatusLine().getStatusCode() == 200;
    }


    public static boolean patchWorkStatusRequest(Employee e) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPatch request = new HttpPatch(url + "work-status/" + e.getId());
        String status;
        if (e.isWorkStatus()) status = "false";
        else status = "true";
        StringEntity se = new StringEntity(status, StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return response.getStatusLine().getStatusCode() == 200;
    }

    public static BufferedReader login(LoginDto loginDTO) throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url + "login");
        JSONObject json = new JSONObject();
        json.put("username", loginDTO.getUsername());
        json.put("password", loginDTO.getPassword());
        StringEntity se = new StringEntity(json.toString(), StandardCharsets.UTF_8);
        se.setContentType("application/json;charset=UTF-8");
        request.setEntity(se);
        HttpResponse response = client.execute(request);
        return new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    }
}
