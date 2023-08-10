package org.weather.app.service.http;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.weather.app.config.Constants;
import org.weather.app.config.WeatherConfig;
import org.weather.app.service.dto.LocationResult;

@Service
public class LocationApi {

  private final int COUNT_VALUE = 25;
  private final String LANGUAGE_VALUE = "en";
  private final String FORMAT_VALUE = "json";

  private final WeatherConfig weatherConfig;
  private ApiService apiService;

  public LocationApi(WeatherConfig weatherConfig) {
    this.weatherConfig = weatherConfig;
    this.apiService = new ApiService(LocationResult.class);
  }

  public <TYPE> TYPE get(String name) {
    apiService.setBaseUrl(weatherConfig.getLocationApiBaseUrl());
    apiService.setApiPath(weatherConfig.getLocationApiSearchPath());
    apiService.setParams(populateParams(name));
    return (TYPE) apiService.get();
  }

  private MultiValueMap<String, String> populateParams(String name) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add(Constants.NAME, name);
    params.add(Constants.COUNT, String.valueOf(COUNT_VALUE));
    params.add(Constants.LANGUAGE, LANGUAGE_VALUE);
    params.add(Constants.FORMAT, FORMAT_VALUE);
    return params;
  }
}
