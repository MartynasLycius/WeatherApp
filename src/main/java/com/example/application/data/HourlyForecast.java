package com.example.application.data;

import com.example.application.data.Hourly;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HourlyForecast {
  @JsonProperty("latitude")
  private Double latitude;
  @JsonProperty("longitude")
  private Double longitude;
  @JsonProperty("hourly")
  private Hourly hourly;

  public Hourly getDaily() {
    return hourly;
  }
}
