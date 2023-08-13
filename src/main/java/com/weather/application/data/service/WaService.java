package com.weather.application.data.service;

import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
import com.weather.application.data.dto.HourlyForecast;
import com.weather.application.http.client.HttpDailyForecastRequest;
import com.weather.application.http.client.HttpGeoDataRequest;
import com.weather.application.http.client.HttpHourlyForecastRequest;
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
        GeoCode[] result;
        if(cityName == null || cityName.isEmpty()){
            result = new GeoCode[0];
            return result;
        } else {
            result = httpGeoDataRequest.getGeoCodeResult(cityName).getResults();
            if(result == null){
                result = new GeoCode[0];
            }
            return result;
        }
    }

    public HourlyForecast getHourlyForecast(Double latitude, Double longitude, String startDate, String endDate){
        if(latitude == null || longitude == null){
            return new HourlyForecast();
        } else {
            return  httpHourlyForecastRequest.getHourlyForecast(latitude, longitude, startDate, endDate);
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
