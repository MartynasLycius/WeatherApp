package com.example.application.data;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Hourly {
      @JsonProperty("time")
      private List<String> time;
      @JsonProperty("temperature_2m")
      private List<Double> temperature_2m;
      @JsonProperty("wind_speed_10m")
      private List<Double> wind_speed_10m;
      @JsonProperty("rain")
      private List<Double> rain;

      public List<String> getDayHours() {
        return time;
      }
      public List<Double> getTemperatures() {
        return temperature_2m;
      }
      public List<Double> getWindSpeed() {
        return wind_speed_10m;
      }
      public List<Double> getRain() {
        return rain;
      }
}