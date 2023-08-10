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
  "weathercode",
  "temperature_2m_max",
  "temperature_2m_min",
  "sunrise",
  "sunset",
  "uv_index_max",
  "rain_sum",
  "precipitation_probability_max",
  "windspeed_10m_max"
})
public class DailyUnit {

  @JsonProperty("time")
  private String time;

  @JsonProperty("weathercode")
  private String weatherCode;

  @JsonProperty("temperature_2m_max")
  private String temperature2mMax;

  @JsonProperty("temperature_2m_min")
  private String temperature2mMin;

  @JsonProperty("sunrise")
  private String sunrise;

  @JsonProperty("sunset")
  private String sunset;

  @JsonProperty("uv_index_max")
  private String uvIndexMax;

  @JsonProperty("rain_sum")
  private String rainSum;

  @JsonProperty("precipitation_probability_max")
  private String precipitationProbabilityMax;

  @JsonProperty("windspeed_10m_max")
  private String windSpeed10mMax;

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

  @JsonProperty("weathercode")
  public String getWeatherCode() {
    return weatherCode;
  }

  @JsonProperty("weathercode")
  public void setWeatherCode(String weatherCode) {
    this.weatherCode = weatherCode;
  }

  @JsonProperty("temperature_2m_max")
  public String getTemperature2mMax() {
    return temperature2mMax;
  }

  @JsonProperty("temperature_2m_max")
  public void setTemperature2mMax(String temperature2mMax) {
    this.temperature2mMax = temperature2mMax;
  }

  @JsonProperty("temperature_2m_min")
  public String getTemperature2mMin() {
    return temperature2mMin;
  }

  @JsonProperty("temperature_2m_min")
  public void setTemperature2mMin(String temperature2mMin) {
    this.temperature2mMin = temperature2mMin;
  }

  @JsonProperty("sunrise")
  public String getSunrise() {
    return sunrise;
  }

  @JsonProperty("sunrise")
  public void setSunrise(String sunrise) {
    this.sunrise = sunrise;
  }

  @JsonProperty("sunset")
  public String getSunset() {
    return sunset;
  }

  @JsonProperty("sunset")
  public void setSunset(String sunset) {
    this.sunset = sunset;
  }

  @JsonProperty("uv_index_max")
  public String getUvIndexMax() {
    return uvIndexMax;
  }

  @JsonProperty("uv_index_max")
  public void setUvIndexMax(String uvIndexMax) {
    this.uvIndexMax = uvIndexMax;
  }

  @JsonProperty("rain_sum")
  public String getRainSum() {
    return rainSum;
  }

  @JsonProperty("rain_sum")
  public void setRainSum(String rainSum) {
    this.rainSum = rainSum;
  }

  @JsonProperty("precipitation_probability_max")
  public String getPrecipitationProbabilityMax() {
    return precipitationProbabilityMax;
  }

  @JsonProperty("precipitation_probability_max")
  public void setPrecipitationProbabilityMax(String precipitationProbabilityMax) {
    this.precipitationProbabilityMax = precipitationProbabilityMax;
  }

  @JsonProperty("windspeed_10m_max")
  public String getWindSpeed10mMax() {
    return windSpeed10mMax;
  }

  @JsonProperty("windspeed_10m_max")
  public void setWindSpeed10mMax(String windSpeed10mMax) {
    this.windSpeed10mMax = windSpeed10mMax;
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
    return "DailyUnit{"
        + "time='"
        + time
        + '\''
        + ", weatherCode='"
        + weatherCode
        + '\''
        + ", temperature2mMax='"
        + temperature2mMax
        + '\''
        + ", temperature2mMin='"
        + temperature2mMin
        + '\''
        + ", sunrise='"
        + sunrise
        + '\''
        + ", sunset='"
        + sunset
        + '\''
        + ", uvIndexMax='"
        + uvIndexMax
        + '\''
        + ", rainSum='"
        + rainSum
        + '\''
        + ", precipitationProbabilityMax='"
        + precipitationProbabilityMax
        + '\''
        + ", windSpeed10mMax='"
        + windSpeed10mMax
        + '\''
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }
}
