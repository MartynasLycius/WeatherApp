package com.waheduzzaman.MeteoWeather.service.network.impl;

import com.waheduzzaman.MeteoWeather.config.AppConfig;
import com.waheduzzaman.MeteoWeather.data.dto.weather.Weather;
import com.waheduzzaman.MeteoWeather.service.network.AbstractAPIWrapper;
import com.waheduzzaman.MeteoWeather.utility.DateTimeUtility;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class WeatherAPIWrapperImpl extends AbstractAPIWrapper {
    private final DateTimeUtility dateTimeUtility;

    public WeatherAPIWrapperImpl(AppConfig appConfig) {
        super(appConfig, Weather.class);
        dateTimeUtility = new DateTimeUtility();
    }

    @Override
    public <T> T callNetworkAPI(Object... params) {
        Double longitude = Double.parseDouble(params[0].toString());
        Double latitude = Double.parseDouble(params[1].toString());
        networkService.setUrl(appConfig.getWEATHER_API());
        networkService.setPath(appConfig.getWEATHER_FORECAST_PATH());
        networkService.setParams(getRequestParameters(longitude, latitude));

        return (T) networkService.get();
    }

    @Override
    public MultiValueMap<String, String> getRequestParameters(Object... params) {
        Double longitude = Double.parseDouble(params[0].toString());
        Double latitude = Double.parseDouble(params[1].toString());
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("latitude", latitude.toString());
        parameters.add("longitude", longitude.toString());
        parameters.add("hourly", "temperature_2m,relativehumidity_2m,apparent_temperature,precipitation_probability,rain,weathercode,windspeed_10m,visibility,uv_index");
        parameters.add("daily", "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,uv_index_max,rain_sum,precipitation_probability_max,windspeed_10m_max");
        parameters.add("current_weather", "true");
        parameters.add("timezone", dateTimeUtility.getCurrentTimeZoneString());
        return parameters;
    }
}
