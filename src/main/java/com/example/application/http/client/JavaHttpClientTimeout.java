package com.example.application.http.client;

import java.net.http.HttpClient;
import java.time.Duration;

public class JavaHttpClientTimeout {
    static HttpClient getHttpClientWithTimeout(int seconds) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(seconds))
                .build();
    }
}
