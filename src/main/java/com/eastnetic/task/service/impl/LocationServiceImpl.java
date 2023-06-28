package com.eastnetic.task.service.impl;

import com.eastnetic.task.model.dto.LocationDTO;
import com.eastnetic.task.service.LocationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Autowired
    WebClient webClient;

    @Value("${location.search.api.url}")
    private String locationUrl;

    @Override
    public LocationDTO getLocations(String cityName) {
        String url = this.locationUrl + cityName;
        LocationDTO locationList = this.webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LocationDTO.class)
                .block();
        log.info(locationList.toString());
        return locationList;
    }
}
