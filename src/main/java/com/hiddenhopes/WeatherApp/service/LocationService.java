package com.hiddenhopes.WeatherApp.service;

import com.hiddenhopes.WeatherApp.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> searchLocations(String name);
}
