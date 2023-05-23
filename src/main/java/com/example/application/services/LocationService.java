package com.example.application.services;

import com.example.application.dto.CityGeoCoding;
import com.example.application.dto.GeoCodingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private List<CityGeoCoding> cityGeoCodingList;
    private Integer cityGeoCodingListCount;

    private final RestTemplate restTemplate;

    public LocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.cityGeoCodingList = new ArrayList<>();
        this.cityGeoCodingListCount = 0;
    }

    public void loadLocationData(String searchCity) {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                + searchCity + "&count=100&language=en&format=json";

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

    public List<CityGeoCoding> getLocationByCityNamePage(Integer page) {
        int offset = (page - 1) * 10;
        int limit = page * 10;

        if (limit >= this.cityGeoCodingList.size()) {
            limit = this.cityGeoCodingList.size() - 1;
        }

        return this.cityGeoCodingListCount == 0 ? this.cityGeoCodingList : this.cityGeoCodingList.subList(offset, limit);
    }

    public Integer getTotal() {
        return this.cityGeoCodingListCount;
    }
}
