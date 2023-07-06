package com.waheduzzaman.MeteoWeather.data.dto.weather;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "temperature",
        "windspeed",
        "winddirection",
        "weathercode",
        "is_day",
        "time"
})
@Generated("jsonschema2pojo")
public class CurrentWeather {

    @JsonProperty("temperature")
    private Double temperature;
    @JsonProperty("windspeed")
    private Double windspeed;
    @JsonProperty("winddirection")
    private Integer winddirection;
    @JsonProperty("weathercode")
    private Integer weathercode;
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
    public Double getWindspeed() {
        return windspeed;
    }

    @JsonProperty("windspeed")
    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }

    @JsonProperty("winddirection")
    public Integer getWinddirection() {
        return winddirection;
    }

    @JsonProperty("winddirection")
    public void setWinddirection(Integer winddirection) {
        this.winddirection = winddirection;
    }

    @JsonProperty("weathercode")
    public Integer getWeathercode() {
        return weathercode;
    }

    @JsonProperty("weathercode")
    public void setWeathercode(Integer weathercode) {
        this.weathercode = weathercode;
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

}