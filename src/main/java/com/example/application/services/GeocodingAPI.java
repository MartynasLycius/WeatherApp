package com.example.application.services;
import com.example.application.data.Location;
import com.example.application.data.LocationResults;

import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import com.vaadin.flow.component.details.Details;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

public class GeocodingAPI {
    private WebClient webClient;

    public GeocodingAPI() {
        this.webClient = WebClient.builder()
          .baseUrl("https://geocoding-api.open-meteo.com/v1/search")
          .build();
    }
    public LocationResults getLocations(String name) {
		Mono<LocationResults> response = this.webClient.get().uri("?name={name}&count=100&language=en&format=json", name)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<LocationResults>() {});
        LocationResults readers = response.block();
        return readers;
	}
}
