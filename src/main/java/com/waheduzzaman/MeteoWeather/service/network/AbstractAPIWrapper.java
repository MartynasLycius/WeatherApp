package com.waheduzzaman.MeteoWeather.service.network;

import com.waheduzzaman.MeteoWeather.config.AppConfig;
import org.springframework.util.MultiValueMap;

public abstract class AbstractAPIWrapper {
    protected final AppConfig appConfig;
    protected final NetworkService networkService;

    public <T> AbstractAPIWrapper(AppConfig appConfig, Class<T> target){
        this.appConfig = appConfig;
        this.networkService = new NetworkService<>(target);
    }
    public abstract <T> T callNetworkAPI(Object... params);

    public abstract MultiValueMap<String, String> getRequestParameters(Object... params);
}
