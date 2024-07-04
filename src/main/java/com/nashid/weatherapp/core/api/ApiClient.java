package com.nashid.weatherapp.core.api;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ApiClient {
    public static String callUrl(String apiUrl, HttpMethod httpMethod) {
        return callUrl(apiUrl, httpMethod, null);
    }
    public static String callUrl(String apiUrl, HttpMethod httpMethod, String data) {
        String responseData = "";
        HttpResponse<String> response = null;
        try {
            if (httpMethod == HttpMethod.GET) {
                response = Unirest.get(apiUrl)
                        .header("content-type", "application/json")
                        .header("cache-control", "no-cache")
                        .asString();
            }
            else if (httpMethod == HttpMethod.POST) {
                Unirest.setTimeouts(60000, 60000);
                response = Unirest.post(apiUrl)
                        .header("content-type", "application/json")
                        .header("cache-control", "no-cache")
                        .body(data)
                        .asString();
            }
            responseData = response.getBody();
        }
        catch (Exception exception) {
            System.out.println("Error Occurred");
            exception.printStackTrace();
        }
        return responseData;
    }
}
