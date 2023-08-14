package com.weather.application.data.service;

import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
import com.weather.application.data.dto.HourlyForecast;
import com.weather.application.http.client.HttpDailyForecastRequest;
import com.weather.application.http.client.HttpGeoDataRequest;
import com.weather.application.http.client.HttpHourlyForecastRequest;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WaService {

    private static final Logger LOGGER = Logger.getLogger(WaService.class.getName());

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
        LOGGER.log(Level.INFO, "getGeoCodeResult request start with cityName {0}", new Object[]{cityName});
        GeoCode[] result;
        if(cityName == null || cityName.isEmpty()){
            result = new GeoCode[0];
            LOGGER.log(Level.INFO, "getGeoCodeResult request end with result {0}", new Object[]{result});
            return result;
        } else {
            result = httpGeoDataRequest.getGeoCodeResult(cityName).getResults();
            if(result == null){
                result = new GeoCode[0];
            }
            LOGGER.log(Level.INFO, "getGeoCodeResult request end with result {0}", new Object[]{result});
            return result;
        }
    }

    public HourlyForecast getHourlyForecast(Double latitude, Double longitude, String startDate, String endDate){
        LOGGER.log(Level.INFO, "getHourlyForecast request start with " +
                "latitude {0}, longitude {1}, startDate {2}, String endDate {3}", new Object[]{latitude, longitude, startDate, endDate});
        if(latitude == null || longitude == null){
            LOGGER.log(Level.INFO, "getHourlyForecast request end with null");
            return new HourlyForecast();
        } else {
            HourlyForecast hourlyForecast = httpHourlyForecastRequest.getHourlyForecast(latitude, longitude, startDate, endDate);
            LOGGER.log(Level.INFO, "getHourlyForecast request end with {0}", new Object[]{hourlyForecast});
            return  httpHourlyForecastRequest.getHourlyForecast(latitude, longitude, startDate, endDate);
        }
    }

    public DailyForecast getDailyForecast(Double latitude, Double longitude){
        LOGGER.log(Level.INFO, "getDailyForecast request start with " +
                "latitude {0}, longitude {1}", new Object[]{latitude, longitude});
        if(latitude == null || longitude == null){
            LOGGER.log(Level.INFO, "getDailyForecast request end with null");
            return new DailyForecast();
        } else {
            DailyForecast dailyForecast = httpDailyForecastRequest.getDailyForecast(latitude, longitude);
            LOGGER.log(Level.INFO, "getDailyForecast request end with {0}", new Object[]{dailyForecast});
            return  dailyForecast;
        }
    }

}
