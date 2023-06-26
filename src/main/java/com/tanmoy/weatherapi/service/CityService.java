package com.tanmoy.weatherapi.service;

import com.tanmoy.weatherapi.entity.City;
import com.tanmoy.weatherapi.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getCityList() {
        return cityRepository.findAll();
    }

    public City findByCityNameAndCountryName(String cityName, String countryName) {
        return cityRepository.findByCityNameAndCountryName(cityName, countryName);
    }

}
