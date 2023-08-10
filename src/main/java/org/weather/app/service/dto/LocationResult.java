package org.weather.app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class LocationResult {
  @JsonProperty("results")
  private List<Location> locations;

  public List<Location> getLocations() {
    return Objects.isNull(locations) ? List.of() : locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  @Override
  public String toString() {
    return "LocationResult{" + "locations=" + locations + '}';
  }
}
