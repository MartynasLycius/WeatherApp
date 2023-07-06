package com.waheduzzaman.MeteoWeather.service.network.impl;

import com.waheduzzaman.MeteoWeather.config.AppConfig;
import com.waheduzzaman.MeteoWeather.data.dto.location.LocationResult;
import com.waheduzzaman.MeteoWeather.service.network.AbstractAPIWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class LocationAPIWrapperImpl extends AbstractAPIWrapper {
    private final int NUMBER_OF_RESULT_TO_REQUEST = 100;
    private final String REQUEST_LANGUAGE_CODE = "en";
    private final String REQUEST_FORMAT = "json";

    public LocationAPIWrapperImpl(AppConfig appConfig) {
        super(appConfig, LocationResult.class);
    }

    @Override
    public <T> T callNetworkAPI(Object... params) {
        networkService.setUrl(appConfig.getLOCATION_API());
        networkService.setPath(appConfig.getLOCATION_SEARCH_PATH());
        networkService.setParams(getRequestParameters(params[0]));

        return (T) networkService.get();
    }

    @Override
    public MultiValueMap<String, String> getRequestParameters(Object... params) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("name", params[0].toString());
        parameters.add("count", String.valueOf(NUMBER_OF_RESULT_TO_REQUEST));
        parameters.add("language", REQUEST_LANGUAGE_CODE);
        parameters.add("format", REQUEST_FORMAT);
        return parameters;
    }
}
