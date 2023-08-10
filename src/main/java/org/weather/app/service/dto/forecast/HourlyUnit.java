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
@JsonPropertyOrder({
  "time",
  "temperature_2m",
  "relativehumidity_2m",
  "apparent_temperature",
  "precipitation_probability",
  "rain",
  "weathercode",
  "visibility",
  "windspeed_10m",
  "uv_index"
})
public class HourlyUnit {

  @JsonProperty("time")
  private String time;

  @JsonProperty("temperature_2m")
  private String temperature2m;

  @JsonProperty("relativehumidity_2m")
  private String relativeHumidity2m;

  @JsonProperty("apparent_temperature")
  private String apparentTemperature;

  @JsonProperty("precipitation_probability")
  private String precipitationProbability;

  @JsonProperty("rain")
  private String rain;

  @JsonProperty("weathercode")
  private String weatherCode;

  @JsonProperty("visibility")
  private String visibility;

  @JsonProperty("windspeed_10m")
  private String windSpeed10m;

  @JsonProperty("uv_index")
  private String uvIndex;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

  @JsonProperty("time")
  public String getTime() {
    return time;
  }

  @JsonProperty("time")
  public void setTime(String time) {
    this.time = time;
  }

  @JsonProperty("temperature_2m")
  public String getTemperature2m() {
    return temperature2m;
  }

  @JsonProperty("temperature_2m")
  public void setTemperature2m(String temperature2m) {
    this.temperature2m = temperature2m;
  }

  @JsonProperty("relativehumidity_2m")
  public String getRelativeHumidity2m() {
    return relativeHumidity2m;
  }

  @JsonProperty("relativehumidity_2m")
  public void setRelativeHumidity2m(String relativeHumidity2m) {
    this.relativeHumidity2m = relativeHumidity2m;
  }

  @JsonProperty("apparent_temperature")
  public String getApparentTemperature() {
    return apparentTemperature;
  }

  @JsonProperty("apparent_temperature")
  public void setApparentTemperature(String apparentTemperature) {
    this.apparentTemperature = apparentTemperature;
  }

  @JsonProperty("precipitation_probability")
  public String getPrecipitationProbability() {
    return precipitationProbability;
  }

  @JsonProperty("precipitation_probability")
  public void setPrecipitationProbability(String precipitationProbability) {
    this.precipitationProbability = precipitationProbability;
  }

  @JsonProperty("rain")
  public String getRain() {
    return rain;
  }

  @JsonProperty("rain")
  public void setRain(String rain) {
    this.rain = rain;
  }

  @JsonProperty("weathercode")
  public String getWeatherCode() {
    return weatherCode;
  }

  @JsonProperty("weathercode")
  public void setWeatherCode(String weatherCode) {
    this.weatherCode = weatherCode;
  }

  @JsonProperty("visibility")
  public String getVisibility() {
    return visibility;
  }

  @JsonProperty("visibility")
  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  @JsonProperty("windspeed_10m")
  public String getWindSpeed10m() {
    return windSpeed10m;
  }

  @JsonProperty("windspeed_10m")
  public void setWindSpeed10m(String windSpeed10m) {
    this.windSpeed10m = windSpeed10m;
  }

  @JsonProperty("uv_index")
  public String getUvIndex() {
    return uvIndex;
  }

  @JsonProperty("uv_index")
  public void setUvIndex(String uvIndex) {
    this.uvIndex = uvIndex;
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
    return "HourlyUnit{"
        + "time='"
        + time
        + '\''
        + ", temperature2m='"
        + temperature2m
        + '\''
        + ", relativeHumidity2m='"
        + relativeHumidity2m
        + '\''
        + ", apparentTemperature='"
        + apparentTemperature
        + '\''
        + ", precipitationProbability='"
        + precipitationProbability
        + '\''
        + ", rain='"
        + rain
        + '\''
        + ", weatherCode='"
        + weatherCode
        + '\''
        + ", visibility='"
        + visibility
        + '\''
        + ", windSpeed10m='"
        + windSpeed10m
        + '\''
        + ", uvIndex='"
        + uvIndex
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
