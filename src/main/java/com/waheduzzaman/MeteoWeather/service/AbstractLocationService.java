package com.waheduzzaman.MeteoWeather.service;

import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.service.network.impl.LocationAPIWrapperImpl;

import java.util.List;

public abstract class AbstractLocationService {
    protected final LocationAPIWrapperImpl weatherLocationAPIWrapper;

    public AbstractLocationService(LocationAPIWrapperImpl weatherLocationAPIWrapper) {
        this.weatherLocationAPIWrapper = weatherLocationAPIWrapper;
    }

    public abstract List<Location> getLocation(String cityName);
}
