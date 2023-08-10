package org.weather.app.service.http;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;
import org.weather.app.config.WeatherConfig;
import org.weather.app.service.dto.WeatherForecast;

@Service
public class WeatherForecastApi {

  private final String DAILY_PARAMS =
      "temperature_2m_max,weathercode,temperature_2m_min,sunrise,sunset,uv_index_max,rain_sum,precipitation_probability_max,windspeed_10m_max";
  private final String HOURLY_PARAMS =
      "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,windspeed_10m,visibility,uv_index";
  private final WeatherConfig weatherConfig;
  private ApiService apiService;

  public WeatherForecastApi(WeatherConfig weatherConfig) {
    this.weatherConfig = weatherConfig;
    this.apiService = new ApiService(WeatherForecast.class);
  }

  public <TYPE> TYPE get(Double longitude, Double latitude) {
    apiService.setBaseUrl(weatherConfig.getForecastApiBaseUrl());
    apiService.setApiPath(weatherConfig.getForecastApiPath());
    apiService.setParams(populateParams(longitude, latitude));
    return (TYPE) apiService.get();
  }

  private MultiValueMap<String, String> populateParams(Double longitude, Double latitude) {
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add(Constants.LATITUDE, latitude.toString());
    parameters.add(Constants.LONGITUDE, longitude.toString());
    parameters.add(Constants.CURRENT_WEATHER, Constants.TRUE_STRING);
    parameters.add(Constants.HOURLY, HOURLY_PARAMS);
    parameters.add(Constants.DAILY, DAILY_PARAMS);
    parameters.add(Constants.TIMEZONE, Utils.getCurrentTimeZoneId());
    return parameters;
  }
}
