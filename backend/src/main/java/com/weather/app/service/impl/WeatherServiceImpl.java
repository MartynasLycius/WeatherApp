package com.weather.app.service.impl;

import com.weather.app.constants.Constants;
import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.HourlyWeatherResponseModel;
import com.weather.app.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WeatherServiceImpl is implementation of WeatherService
 *
 * @author raihan
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    /**
     * getDailyWeather method for daily weather forecast
     *
     * @param timezone  required params for daily weather
     * @param latitude  required params for daily weather
     * @param longitude required params for daily weather
     * @return DailyWeatherResponseModel
     * @author raihan
     */
    @Override
    public ResponseEntity<DailyWeatherResponseModel> getDailyWeather(String timezone, String latitude, String longitude) {

        WebClient webClient = WebClient.create(Constants.WEATHER_BASE_URL);
        String uri = Constants.WEATHER_URI_DAILY.replaceAll(Constants.LATITUDE_PARAM, String.valueOf(latitude))
                .replaceAll(Constants.LONGITUDE_PARAM, longitude)
                .replaceAll(Constants.TIMEZONE_PARAM, timezone);
        DailyWeatherResponseModel dailyWeatherResponseModel = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DailyWeatherResponseModel.class))
                .block();
        return new ResponseEntity<>(dailyWeatherResponseModel, HttpStatus.OK);
    }

    /**
     * method for getting hourly weather forecast
     *
     * @param latitude  required
     * @param longitude type String
     * @return HourlyWeatherResponseModel
     * @author raihan
     */
    @Override
    public ResponseEntity<HourlyWeatherResponseModel> getHourlyWeather(String latitude, String longitude) {
        WebClient webClient = WebClient.create(Constants.WEATHER_BASE_URL);
        String uri = Constants.WEATHER_URI_HOURLY.replaceAll(Constants.LATITUDE_PARAM, latitude)
                .replaceAll(Constants.LONGITUDE_PARAM, longitude);
        HourlyWeatherResponseModel hourlyWeatherResponseModel = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(HourlyWeatherResponseModel.class))
                .block();
        return new ResponseEntity<>(hourlyWeatherResponseModel, HttpStatus.OK);
    }
}
