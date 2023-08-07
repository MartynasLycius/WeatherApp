package com.example.application.http.client;

import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.time.Duration;

@Component
public class HttpClientTimeout {
    static HttpClient getHttpClientWithTimeout(int seconds) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(seconds))
                .build();
    }
}
