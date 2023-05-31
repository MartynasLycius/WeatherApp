package com.proit.application.service;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.dto.WeatherDataDto;
import com.proit.application.restclient.WeatherRestClient;
import com.proit.application.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherDataService {
    private final WeatherRestClient weatherRestClient;
    private final UserService userService;
    private final UserFavLocationService userFavLocationService;

    public List<LocationDto> getLocations(String query) {
        log.debug("getLocations: query={}", query);

        List<LocationDto> list = weatherRestClient.getLocations(query);
        if (userService.isUserLoggedIn()) {
            markUserFavoriteLocations(list);
        }

        return list;
    }

    public WeatherDataDto getWeatherData(double lat, double lang) {
        log.debug("getWeatherData: lat={}, lang={}", lat, lang);

        return weatherRestClient.getWeatherData(lat, lang, UserUtil.getUserTimezone());
    }

    private void markUserFavoriteLocations(List<LocationDto> list) {
        Set<Long> favLocationsExternalIdsCurrentUser = userFavLocationService.getFavLocationsExternalIdsCurrentUser();

        list.forEach(locationDto -> {
            if (favLocationsExternalIdsCurrentUser.contains(locationDto.getId())) {
                locationDto.setFavourite(true);
            }
        });
    }
}
