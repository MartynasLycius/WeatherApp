package com.waheduzzaman.MeteoWeather.service.impl;

import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.dto.location.LocationResult;
import com.waheduzzaman.MeteoWeather.service.AbstractLocationService;
import com.waheduzzaman.MeteoWeather.service.network.impl.LocationAPIWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl extends AbstractLocationService {

    public LocationServiceImpl(LocationAPIWrapperImpl weatherLocationAPIWrapper) {
        super(weatherLocationAPIWrapper);
    }

    @Override
    public List<Location> getLocation(String cityName) {
        LocationResult locationResult = weatherLocationAPIWrapper.callNetworkAPI(cityName);
        return locationResult.getLocations();
    }
}
