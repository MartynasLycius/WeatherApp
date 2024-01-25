package com.example.application.data;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Daily {
      @JsonProperty("time")
      private List<String> time;
      @JsonProperty("temperature_2m_max")
      private List<Double> temperature_2m_max;

      public List<String> getDays() {
        return time;
      }
      public List<Double> getTemperatures() {
        return temperature_2m_max;
      }
}
