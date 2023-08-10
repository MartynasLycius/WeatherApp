package org.weather.app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.weather.app.service.dto.pojo.WmoCode;

@Configuration
public class WeatherConfig {
  @Value("${weather.name}")
  private String appName;

  @Value("${weather.api.location-search}")
  private String locationApiBaseUrl;

  @Value("${weather.api.location-search-path}")
  private String locationApiSearchPath;

  @Value("${weather.api.daily-forecast}")
  private String forecastApiBaseUrl;

  @Value("${weather.api.daily-forecast-path}")
  private String forecastApiPath;

  @Value("${weather.wmo-codes-file-path}")
  private String wmoCodesFilePath;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getLocationApiBaseUrl() {
    return locationApiBaseUrl;
  }

  public void setLocationApiBaseUrl(String locationApiBaseUrl) {
    this.locationApiBaseUrl = locationApiBaseUrl;
  }

  public String getLocationApiSearchPath() {
    return locationApiSearchPath;
  }

  public void setLocationApiSearchPath(String locationApiSearchPath) {
    this.locationApiSearchPath = locationApiSearchPath;
  }

  public String getForecastApiBaseUrl() {
    return forecastApiBaseUrl;
  }

  public void setForecastApiBaseUrl(String forecastApiBaseUrl) {
    this.forecastApiBaseUrl = forecastApiBaseUrl;
  }

  public String getForecastApiPath() {
    return forecastApiPath;
  }

  public void setForecastApiPath(String forecastApiPath) {
    this.forecastApiPath = forecastApiPath;
  }

  public String getWmoCodesFilePath() {
    return wmoCodesFilePath;
  }

  public void setWmoCodesFilePath(String wmoCodesFilePath) {
    this.wmoCodesFilePath = wmoCodesFilePath;
  }

  @Bean("wmoCodeMap")
  public Map<Integer, WmoCode> wmoCodeMap() {
    try {
      return new ObjectMapper()
          .readValue(
              Utils.readFileToString(wmoCodesFilePath),
              new TypeReference<Map<Integer, WmoCode>>() {});
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
