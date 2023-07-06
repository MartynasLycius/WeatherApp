package com.waheduzzaman.MeteoWeather.data.dto.weather;

import java.util.LinkedHashMap;
import java.util.List;
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
@Generated("jsonschema2pojo")
public class Hourly {

    @JsonProperty("time")
    private List<String> time;
    @JsonProperty("temperature_2m")
    private List<Double> temperature2m;
    @JsonProperty("relativehumidity_2m")
    private List<Double> relativehumidity2m;
    @JsonProperty("apparent_temperature")
    private List<Double> apparentTemperature;
    @JsonProperty("precipitation_probability")
    private List<Double> precipitationProbability;
    @JsonProperty("rain")
    private List<Double> rain;
    @JsonProperty("weathercode")
    private List<Integer> weathercode;
    @JsonProperty("visibility")
    private List<Integer> visibility;
    @JsonProperty("windspeed_10m")
    private List<Double> windspeed10m;
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
    public List<Double> getRelativehumidity2m() {
        return relativehumidity2m;
    }

    @JsonProperty("relativehumidity_2m")
    public void setRelativehumidity2m(List<Double> relativehumidity2m) {
        this.relativehumidity2m = relativehumidity2m;
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
    public List<Integer> getWeathercode() {
        return weathercode;
    }

    @JsonProperty("weathercode")
    public void setWeathercode(List<Integer> weathercode) {
        this.weathercode = weathercode;
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
    public List<Double> getWindspeed10m() {
        return windspeed10m;
    }

    @JsonProperty("windspeed_10m")
    public void setWindspeed10m(List<Double> windspeed10m) {
        this.windspeed10m = windspeed10m;
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

}