package com.eastnetic.task.service;

import com.eastnetic.task.model.dto.ForecastDTO;

public interface ForecastService {
    ForecastDTO getWeatherForecasts(double latitude, double longitude, String timezone);
}
