package com.hiddenhopes.WeatherApp.serviceImpl;

import com.hiddenhopes.WeatherApp.Constant;
import com.hiddenhopes.WeatherApp.dto.GeocodingApiResponse;
import com.hiddenhopes.WeatherApp.dto.Location;
import com.hiddenhopes.WeatherApp.service.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
    public List<Location> searchLocations(String name) {
        String apiUrl = Constant.GEOCODING_API_URL + "?name=" + name;

        RestTemplate restTemplate = new RestTemplate();
        GeocodingApiResponse response = restTemplate.getForObject(apiUrl, GeocodingApiResponse.class);

        if (response != null && response.getResults() != null) {
            return Arrays.asList(response.getResults());
        }

        return Collections.emptyList();
    }
}
