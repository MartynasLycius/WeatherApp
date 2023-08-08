package com.example.application.data.service;

import com.example.application.dto.DailyForecast;
import com.example.application.dto.GeoCode;
import com.example.application.dto.HourlyForecast;
import com.example.application.http.client.HttpDailyForecastRequest;
import com.example.application.http.client.HttpGeoDataRequest;
import com.example.application.http.client.HttpHourlyForecastRequest;
import org.springframework.stereotype.Service;

@Service
public class WaService {

    private HttpGeoDataRequest httpGeoDataRequest;

    private HttpHourlyForecastRequest httpHourlyForecastRequest;
    private HttpDailyForecastRequest httpDailyForecastRequest;

    public WaService(HttpGeoDataRequest httpGeoDataRequest
            , HttpHourlyForecastRequest httpHourlyForecastRequest
            , HttpDailyForecastRequest httpDailyForecastRequest) {
        this.httpGeoDataRequest = httpGeoDataRequest;
        this.httpHourlyForecastRequest = httpHourlyForecastRequest;
        this.httpDailyForecastRequest = httpDailyForecastRequest;
    }

    public GeoCode[] getGeoCodeResult(String cityName){
        if(cityName == null || cityName.isEmpty()){
            GeoCode[] result = new GeoCode[0];
            return result;
        } else {
            return httpGeoDataRequest.getGeoCodeResult(cityName).getResults();
        }
    }

    public HourlyForecast getHourlyForecast(Double latitude, Double longitude){
        if(latitude == null || longitude == null){
            return new HourlyForecast();
        } else {
            return  httpHourlyForecastRequest.getHourlyForecast(latitude, longitude);
        }
    }

    public DailyForecast getDailyForecast(Double latitude, Double longitude){
        if(latitude == null || longitude == null){
            return new DailyForecast();
        } else {
            return  httpDailyForecastRequest.getDailyForecast(latitude, longitude);
        }
    }

}
