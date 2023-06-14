package com.example.application.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

public interface UtilityService {

    String getCurl(HttpEntity<?> httpEntity, String url, HttpMethod httpMethod);
}
