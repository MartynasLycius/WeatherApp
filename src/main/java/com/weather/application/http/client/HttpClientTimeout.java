package com.weather.application.http.client;

import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.time.Duration;

@Component
public class HttpClientTimeout {

    private HttpClientTimeout() {
    }

    static HttpClient getHttpClientWithTimeout(int seconds) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(seconds))
                .build();
    }
}
