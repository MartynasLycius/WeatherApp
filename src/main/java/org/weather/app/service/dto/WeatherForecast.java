package org.weather.app.service.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.weather.app.service.dto.forecast.CurrentWeather;
import org.weather.app.service.dto.forecast.Daily;
import org.weather.app.service.dto.forecast.DailyUnit;
import org.weather.app.service.dto.forecast.Hourly;
import org.weather.app.service.dto.forecast.HourlyUnit;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "latitude",
  "longitude",
  "generationtime_ms",
  "utc_offset_seconds",
  "timezone",
  "timezone_abbreviation",
  "elevation",
  "current_weather",
  "hourly_units",
  "hourly",
  "daily_units",
  "daily"
})
public class WeatherForecast {

  /*  "latitude": 52.52,
  "longitude": 13.419998,
  "generationtime_ms": 0.1690387725830078,
  "utc_offset_seconds": 0,
  "timezone": "GMT",
  "timezone_abbreviation": "GMT",
  "elevation": 38.0,*/

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Integer longitude;

  @JsonProperty("generationtime_ms")
  private Double generationtimeMs;

  @JsonProperty("utc_offset_seconds")
  private Integer utcOffsetSeconds;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("timezone_abbreviation")
  private String timezoneAbbreviation;

  @JsonProperty("elevation")
  private Integer elevation;

  @JsonProperty("current_weather")
  private CurrentWeather currentWeather;

  @JsonProperty("hourly_units")
  private HourlyUnit hourlyUnits;

  @JsonProperty("hourly")
  private Hourly hourly;

  @JsonProperty("daily_units")
  private DailyUnit dailyUnits;

  @JsonProperty("daily")
  private Daily daily;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

  @JsonProperty("latitude")
  public Double getLatitude() {
    return latitude;
  }

  @JsonProperty("latitude")
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  @JsonProperty("longitude")
  public Integer getLongitude() {
    return longitude;
  }

  @JsonProperty("longitude")
  public void setLongitude(Integer longitude) {
    this.longitude = longitude;
  }

  @JsonProperty("generationtime_ms")
  public Double getGenerationtimeMs() {
    return generationtimeMs;
  }

  @JsonProperty("generationtime_ms")
  public void setGenerationtimeMs(Double generationtimeMs) {
    this.generationtimeMs = generationtimeMs;
  }

  @JsonProperty("utc_offset_seconds")
  public Integer getUtcOffsetSeconds() {
    return utcOffsetSeconds;
  }

  @JsonProperty("utc_offset_seconds")
  public void setUtcOffsetSeconds(Integer utcOffsetSeconds) {
    this.utcOffsetSeconds = utcOffsetSeconds;
  }

  @JsonProperty("timezone")
  public String getTimezone() {
    return timezone;
  }

  @JsonProperty("timezone")
  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  @JsonProperty("timezone_abbreviation")
  public String getTimezoneAbbreviation() {
    return timezoneAbbreviation;
  }

  @JsonProperty("timezone_abbreviation")
  public void setTimezoneAbbreviation(String timezoneAbbreviation) {
    this.timezoneAbbreviation = timezoneAbbreviation;
  }

  @JsonProperty("elevation")
  public Integer getElevation() {
    return elevation;
  }

  @JsonProperty("elevation")
  public void setElevation(Integer elevation) {
    this.elevation = elevation;
  }

  @JsonProperty("current_weather")
  public CurrentWeather getCurrentWeather() {
    return currentWeather;
  }

  @JsonProperty("current_weather")
  public void setCurrentWeather(CurrentWeather currentWeather) {
    this.currentWeather = currentWeather;
  }

  @JsonProperty("hourly_units")
  public HourlyUnit getHourlyUnits() {
    return hourlyUnits;
  }

  @JsonProperty("hourly_units")
  public void setHourlyUnits(HourlyUnit hourlyUnits) {
    this.hourlyUnits = hourlyUnits;
  }

  @JsonProperty("hourly")
  public Hourly getHourly() {
    return hourly;
  }

  @JsonProperty("hourly")
  public void setHourly(Hourly hourly) {
    this.hourly = hourly;
  }

  @JsonProperty("daily_units")
  public DailyUnit getDailyUnits() {
    return dailyUnits;
  }

  @JsonProperty("daily_units")
  public void setDailyUnits(DailyUnit dailyUnits) {
    this.dailyUnits = dailyUnits;
  }

  @JsonProperty("daily")
  public Daily getDaily() {
    return daily;
  }

  @JsonProperty("daily")
  public void setDaily(Daily daily) {
    this.daily = daily;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "WeatherForecast{"
        + "latitude="
        + latitude
        + ", longitude="
        + longitude
        + ", generationtimeMs="
        + generationtimeMs
        + ", utcOffsetSeconds="
        + utcOffsetSeconds
        + ", timezone='"
        + timezone
        + '\''
        + ", timezoneAbbreviation='"
        + timezoneAbbreviation
        + '\''
        + ", elevation="
        + elevation
        + ", currentWeather="
        + currentWeather
        + ", hourlyUnits="
        + hourlyUnits
        + ", hourly="
        + hourly
        + ", dailyUnits="
        + dailyUnits
        + ", daily="
        + daily
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
