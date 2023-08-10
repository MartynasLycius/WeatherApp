package org.weather.app.service.dto.forecast;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.List;
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
public class Hourly {

  @JsonProperty("time")
  private List<String> time;

  @JsonProperty("temperature_2m")
  private List<Double> temperature2m;

  @JsonProperty("relativehumidity_2m")
  private List<Double> relativeHumidity2m;

  @JsonProperty("apparent_temperature")
  private List<Double> apparentTemperature;

  @JsonProperty("precipitation_probability")
  private List<Double> precipitationProbability;

  @JsonProperty("rain")
  private List<Double> rain;

  @JsonProperty("weathercode")
  private List<Integer> weatherCode;

  @JsonProperty("visibility")
  private List<Integer> visibility;

  @JsonProperty("windspeed_10m")
  private List<Double> windSpeed10m;

  @JsonProperty("uv_index")
  private List<Double> uvIndex;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

  @JsonProperty("time")
  public List<String> getTime() {
    return time;
  }

  @JsonProperty("time")
  public void setTime(List<String> time) {
    this.time = time;
  }

  @JsonProperty("temperature_2m")
  public List<Double> getTemperature2m() {
    return temperature2m;
  }

  @JsonProperty("temperature_2m")
  public void setTemperature2m(List<Double> temperature2m) {
    this.temperature2m = temperature2m;
  }

  @JsonProperty("relativehumidity_2m")
  public List<Double> getRelativeHumidity2m() {
    return relativeHumidity2m;
  }

  @JsonProperty("relativehumidity_2m")
  public void setRelativeHumidity2m(List<Double> relativeHumidity2m) {
    this.relativeHumidity2m = relativeHumidity2m;
  }

  @JsonProperty("apparent_temperature")
  public List<Double> getApparentTemperature() {
    return apparentTemperature;
  }

  @JsonProperty("apparent_temperature")
  public void setApparentTemperature(List<Double> apparentTemperature) {
    this.apparentTemperature = apparentTemperature;
  }

  @JsonProperty("precipitation_probability")
  public List<Double> getPrecipitationProbability() {
    return precipitationProbability;
  }

  @JsonProperty("precipitation_probability")
  public void setPrecipitationProbability(List<Double> precipitationProbability) {
    this.precipitationProbability = precipitationProbability;
  }

  @JsonProperty("rain")
  public List<Double> getRain() {
    return rain;
  }

  @JsonProperty("rain")
  public void setRain(List<Double> rain) {
    this.rain = rain;
  }

  @JsonProperty("weathercode")
  public List<Integer> getWeatherCode() {
    return weatherCode;
  }

  @JsonProperty("weathercode")
  public void setWeatherCode(List<Integer> weatherCode) {
    this.weatherCode = weatherCode;
  }

  @JsonProperty("visibility")
  public List<Integer> getVisibility() {
    return visibility;
  }

  @JsonProperty("visibility")
  public void setVisibility(List<Integer> visibility) {
    this.visibility = visibility;
  }

  @JsonProperty("windspeed_10m")
  public List<Double> getWindSpeed10m() {
    return windSpeed10m;
  }

  @JsonProperty("windspeed_10m")
  public void setWindSpeed10m(List<Double> windSpeed10m) {
    this.windSpeed10m = windSpeed10m;
  }

  @JsonProperty("uv_index")
  public List<Double> getUvIndex() {
    return uvIndex;
  }

  @JsonProperty("uv_index")
  public void setUvIndex(List<Double> uvIndex) {
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
    return "Hourly{"
        + "time="
        + time
        + ", temperature2m="
        + temperature2m
        + ", relativeHumidity2m="
        + relativeHumidity2m
        + ", apparentTemperature="
        + apparentTemperature
        + ", precipitationProbability="
        + precipitationProbability
        + ", rain="
        + rain
        + ", weatherCode="
        + weatherCode
        + ", visibility="
        + visibility
        + ", windSpeed10m="
        + windSpeed10m
        + ", uvIndex="
        + uvIndex
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
