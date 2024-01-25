package com.example.application.data;

import com.example.application.data.Daily;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class DailyForecast {
  @JsonProperty("latitude")
  private Double latitude;
  @JsonProperty("longitude")
  private Double longitude;
  @JsonProperty("daily")
  private Daily daily;

  public Daily getDaily() {
    return daily;
  }
}
