package com.example.application.services;

import com.example.application.dto.CityGeoCoding;

import java.util.List;

public interface LocationService {
    void loadLocationData(String searchCity);

    List<CityGeoCoding> getLocationByCityNamePage(Integer page);

    Integer getTotal();
}
