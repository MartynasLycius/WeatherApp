package org.weather.app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.weather.app.service.dto.Location;
import org.weather.app.service.dto.LocationResult;
import org.weather.app.service.http.LocationApi;

@Service
public class LocationService {

  private final LocationApi locationApi;

  public LocationService(LocationApi locationApi) {
    this.locationApi = locationApi;
  }

  public List<Location> findByLocationName(String name) {
    LocationResult result = locationApi.get(name);
    return result.getLocations();
  }
}
