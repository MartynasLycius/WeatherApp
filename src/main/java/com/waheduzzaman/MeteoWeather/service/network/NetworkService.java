package com.waheduzzaman.MeteoWeather.service.network;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Data
@NoArgsConstructor
public class NetworkService<T> {
    private String url;
    private String path;
    private Map<String, String> headers;
    private MultiValueMap<String, String> params;
    private Class<T> target;

    private WebClient webClient;

    public NetworkService(Class<T> target) {
        this.target = target;
    }

    public T get() {
        webClient = WebClient.builder().baseUrl(url).build();
        return sendRequest();
    }

    private T sendRequest() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParams(params)
                        .build())
                .retrieve()
                .bodyToMono(target)
                .block();
    }

}
