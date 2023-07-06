package com.waheduzzaman.MeteoWeather.data.dto.weather;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
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
@Generated("jsonschema2pojo")
public class Daily {

    @JsonProperty("time")
    private List<String> time;
    @JsonProperty("weathercode")
    private List<Integer> weathercode;
    @JsonProperty("temperature_2m_max")
    private List<Double> temperature2mMax;
    @JsonProperty("temperature_2m_min")
    private List<Double> temperature2mMin;
    @JsonProperty("sunrise")
    private List<String> sunrise;
    @JsonProperty("sunset")
    private List<String> sunset;
    @JsonProperty("uv_index_max")
    private List<Double> uvIndexMax;
    @JsonProperty("rain_sum")
    private List<Double> rainSum;
    @JsonProperty("precipitation_probability_max")
    private List<Double> precipitationProbabilityMax;
    @JsonProperty("windspeed_10m_max")
    private List<Double> windspeed10mMax;
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

    @JsonProperty("weathercode")
    public List<Integer> getWeathercode() {
        return weathercode;
    }

    @JsonProperty("weathercode")
    public void setWeathercode(List<Integer> weathercode) {
        this.weathercode = weathercode;
    }

    @JsonProperty("temperature_2m_max")
    public List<Double> getTemperature2mMax() {
        return temperature2mMax;
    }

    @JsonProperty("temperature_2m_max")
    public void setTemperature2mMax(List<Double> temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    @JsonProperty("temperature_2m_min")
    public List<Double> getTemperature2mMin() {
        return temperature2mMin;
    }

    @JsonProperty("temperature_2m_min")
    public void setTemperature2mMin(List<Double> temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    @JsonProperty("sunrise")
    public List<String> getSunrise() {
        return sunrise;
    }

    @JsonProperty("sunrise")
    public void setSunrise(List<String> sunrise) {
        this.sunrise = sunrise;
    }

    @JsonProperty("sunset")
    public List<String> getSunset() {
        return sunset;
    }

    @JsonProperty("sunset")
    public void setSunset(List<String> sunset) {
        this.sunset = sunset;
    }

    @JsonProperty("uv_index_max")
    public List<Double> getUvIndexMax() {
        return uvIndexMax;
    }

    @JsonProperty("uv_index_max")
    public void setUvIndexMax(List<Double> uvIndexMax) {
        this.uvIndexMax = uvIndexMax;
    }

    @JsonProperty("rain_sum")
    public List<Double> getRainSum() {
        return rainSum;
    }

    @JsonProperty("rain_sum")
    public void setRainSum(List<Double> rainSum) {
        this.rainSum = rainSum;
    }

    @JsonProperty("precipitation_probability_max")
    public List<Double> getPrecipitationProbabilityMax() {
        return precipitationProbabilityMax;
    }

    @JsonProperty("precipitation_probability_max")
    public void setPrecipitationProbabilityMax(List<Double> precipitationProbabilityMax) {
        this.precipitationProbabilityMax = precipitationProbabilityMax;
    }

    @JsonProperty("windspeed_10m_max")
    public List<Double> getWindspeed10mMax() {
        return windspeed10mMax;
    }

    @JsonProperty("windspeed_10m_max")
    public void setWindspeed10mMax(List<Double> windspeed10mMax) {
        this.windspeed10mMax = windspeed10mMax;
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