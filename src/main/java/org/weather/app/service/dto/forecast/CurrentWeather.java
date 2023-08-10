package org.weather.app.service.dto.forecast;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"temperature", "windspeed", "winddirection", "weathercode", "is_day", "time"})
public class CurrentWeather {

  @JsonProperty("temperature")
  private Double temperature;

  @JsonProperty("windspeed")
  private Double windSpeed;

  @JsonProperty("winddirection")
  private Integer windDirection;

  @JsonProperty("weathercode")
  private Integer weatherCode;

  @JsonProperty("is_day")
  private Integer isDay;

  @JsonProperty("time")
  private String time;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

  @JsonProperty("temperature")
  public Double getTemperature() {
    return temperature;
  }

  @JsonProperty("temperature")
  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  @JsonProperty("windspeed")
  public Double getWindSpeed() {
    return windSpeed;
  }

  @JsonProperty("windspeed")
  public void setWindSpeed(Double windSpeed) {
    this.windSpeed = windSpeed;
  }

  @JsonProperty("winddirection")
  public Integer getWindDirection() {
    return windDirection;
  }

  @JsonProperty("winddirection")
  public void setWindDirection(Integer windDirection) {
    this.windDirection = windDirection;
  }

  @JsonProperty("weathercode")
  public Integer getWeatherCode() {
    return weatherCode;
  }

  @JsonProperty("weathercode")
  public void setWeatherCode(Integer weatherCode) {
    this.weatherCode = weatherCode;
  }

  @JsonProperty("is_day")
  public Integer getIsDay() {
    return isDay;
  }

  @JsonProperty("is_day")
  public void setIsDay(Integer isDay) {
    this.isDay = isDay;
  }

  @JsonProperty("time")
  public String getTime() {
    return time;
  }

  @JsonProperty("time")
  public void setTime(String time) {
    this.time = time;
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
    return "CurrentWeather{"
        + "temperature="
        + temperature
        + ", windSpeed="
        + windSpeed
        + ", windDirection="
        + windDirection
        + ", weatherCode="
        + weatherCode
        + ", isDay="
        + isDay
        + ", time='"
        + time
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
