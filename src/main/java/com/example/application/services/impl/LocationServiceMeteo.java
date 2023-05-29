package com.example.application.services.impl;

import com.example.application.dto.CityGeoCoding;
import com.example.application.dto.GeoCodingResponse;
import com.example.application.services.LocationService;
import com.example.application.utility.WeatherApiEndPoints;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceMeteo implements LocationService {
    private List<CityGeoCoding> cityGeoCodingList;
    private Integer cityGeoCodingListCount;

    private final RestTemplate restTemplate;

    public LocationServiceMeteo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.cityGeoCodingList = new ArrayList<>();
        this.cityGeoCodingListCount = 0;
    }

    @Override
    public void loadLocationData(String searchCity) {
        try {
            String url = WeatherApiEndPoints.METEO_GEOCODING_API_URL.formatted(searchCity);

            ResponseEntity<GeoCodingResponse> response = this.restTemplate.getForEntity(url, GeoCodingResponse.class);

            GeoCodingResponse geoCodingResponse = response.getBody();

            if (geoCodingResponse == null || geoCodingResponse.getResults() == null) {
                this.cityGeoCodingList.clear();
            }
            else {
                this.cityGeoCodingList = geoCodingResponse.getResults();
            }
            this.cityGeoCodingListCount = this.cityGeoCodingList.size();
        }
        catch (Throwable throwable) {
            Notification.show("Network Error", 5000, Notification.Position.TOP_CENTER);
            this.cityGeoCodingList.clear();
            this.cityGeoCodingListCount = 0;
        }
    }

    @Override
    public List<CityGeoCoding> getLocationByCityNamePage(Integer page) {
        int offset = (page - 1) * 10;
        int limit = page * 10;

        if (limit >= this.cityGeoCodingList.size()) {
            limit = this.cityGeoCodingList.size() - 1;
        }

        return this.cityGeoCodingListCount == 0 ? this.cityGeoCodingList : this.cityGeoCodingList.subList(offset, limit);
    }

    @Override
    public Integer getTotal() {
        return this.cityGeoCodingListCount;
    }
}
