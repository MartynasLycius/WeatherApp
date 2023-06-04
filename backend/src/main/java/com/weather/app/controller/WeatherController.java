package com.weather.app.controller;

import com.weather.app.constants.Endpoints;
import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.HourlyWeatherResponseModel;
import com.weather.app.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WeatherController for weather api
 *
 * @author raihan
 */
@RestController
@RequestMapping(Endpoints.API_WEATHER)
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherService weatherService;

    /**
     * getDailyWeather for daily weather forecast
     *
     * @param timezone  required param for daily weather forecast
     * @param latitude  required param for daily weather forecast
     * @param longitude required param for daily weather forecast
     * @return DailyWeatherResponse
     * @author raihan
     */
    @GetMapping(Endpoints.API_DAILY_FORECAST)
    public ResponseEntity<DailyWeatherResponseModel> getDailyWeather(@RequestParam String timezone,
                                                                     @RequestParam String latitude,
                                                                     @RequestParam String longitude) {
        log.info("getDailyWeather api calling by: {}, {},{}",timezone,latitude,longitude);
        return weatherService.getDailyWeather(timezone, latitude, longitude);
    }

    /**
     * api for getting hourly weather forecast
     *
     * @param latitude  type String
     * @param longitude type String
     * @return HourlyWeatherResponseModel
     * @author raihan
     */
    @GetMapping(Endpoints.API_HOURLY_FORECAST)
    public ResponseEntity<HourlyWeatherResponseModel> getHourlyWeather(
            @RequestParam String latitude,
            @RequestParam String longitude
    ) {
        return weatherService.getHourlyWeather(latitude, longitude);
    }
}
