package com.example.application.service.impl;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.dto.meteo.WeatherForecastResponse;
import com.example.application.service.OpenMeteoService;
import com.example.application.service.UserFavLocationService;
import com.example.application.service.UserService;
import com.example.application.service.WeatherDataService;
import com.vaadin.flow.server.VaadinSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherDataServiceImpl implements WeatherDataService {
    private final OpenMeteoService openMeteoService;
    private final UserService userService;
    private final UserFavLocationService userFavLocationService;

    public List<LocationDto> getLocations(String query) {
        log.debug("getLocations: query={}", query);

        List<LocationDto> list = openMeteoService.getLocations(query);
        if (userService.isUserLoggedIn()) {
            markUserFavoriteLocations(list);
        }

        return list;
    }

    public String getTimeZone(){
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        ZoneId userTimeZone = vaadinSession.getAttribute(ZoneId.class);
        return userTimeZone.toString();
    }

    public WeatherForecastResponse getWeatherData(double lat, double lang) {
        log.debug("getWeatherData: lat={}, lang={}", lat, lang);

        return openMeteoService.getWeatherData(lat, lang, getTimeZone());
    }

    private void markUserFavoriteLocations(List<LocationDto> list) {
        Set<Long> favLocationsLocationIdsCurrentUser = userFavLocationService.getFavLocationsLocationIdsCurrentUser();

        list.forEach(locationDto -> {
            if (favLocationsLocationIdsCurrentUser.contains(locationDto.getId())) {
                locationDto.setFavourite(true);
            }
        });
    }
}
