package com.eastnetic.application.locations.serviceImpl;


import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.locations.response.OpenMeteoLocationApiResponse;
import com.eastnetic.application.locations.service.LocationProviderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@CacheConfig(cacheNames = "locationDetailsCache")
public class OpenMeteoLocationProviderServiceImpl implements LocationProviderService {

    private static final Logger LOGGER = LogManager.getLogger(OpenMeteoLocationProviderServiceImpl.class);

    private static final String API_BASE_URL = "https://geocoding-api.open-meteo.com/v1/search?name=";

    private final RestTemplate restTemplate;

    public OpenMeteoLocationProviderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(key = "#cityName", unless = "#result == null")
    public List<LocationDetails> getLocationDetails(String cityName, int count) {

        LOGGER.info("Fetching location details from open-meteo api: City Name={}: START", cityName);

        String apiUrl = API_BASE_URL + cityName + "&count=" + count;

        try {
            OpenMeteoLocationApiResponse response = restTemplate.getForObject(apiUrl, OpenMeteoLocationApiResponse.class);

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {

                LOGGER.info("Fetching location details from open-meteo api: City Name={}: SUCCESS", cityName);

                return response.getResults();
            }

        } catch (Exception ex) {

            LOGGER.error("Fetching location details from open-meteo api: City Name={}: Error", cityName, ex);
        }

        return null;
    }
}
