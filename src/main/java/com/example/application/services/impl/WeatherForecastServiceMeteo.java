package com.example.application.services.impl;

import com.example.application.dto.DailyForecast;
import com.example.application.dto.ForecastResponse;
import com.example.application.dto.HourlyForecast;
import com.example.application.services.WeatherForecastService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WeatherForecastServiceMeteo implements WeatherForecastService {
    private final RestTemplate restTemplate;

    public WeatherForecastServiceMeteo(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ForecastResponse getWeatherForecastForLocation(String latitude, String longitude, String tz) {
        String url
                = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude + "&" +
                "hourly=temperature_2m,rain,windspeed_10m&" +
                "daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&" +
                "current_weather=true&timezone=" + tz + "";
        ResponseEntity<ForecastResponse> response = this.restTemplate.getForEntity(url, ForecastResponse.class);

        ForecastResponse forecastResponse = response.getBody();

        return forecastResponse;
    }

    @Override
    public List<DailyForecast> getDailyForeCast(ForecastResponse forecastResponse) {
        ArrayList<String> time = forecastResponse.getDaily().getTime();
        ArrayList<Double> temperature_2m_min = forecastResponse.getDaily().getTemperature_2m_min();
        ArrayList<Double> temperature_2m_max = forecastResponse.getDaily().getTemperature_2m_max();
        ArrayList<Double> rain_sum = forecastResponse.getDaily().getRain_sum();
        ArrayList<Double> windspeed_10m_max = forecastResponse.getDaily().getWindspeed_10m_max();

        List<DailyForecast> dailyForecasts = new ArrayList<>();

        for (int i = 0; i < time.size(); i++) {
            dailyForecasts.add(new DailyForecast(
                            time.get(i),
                            temperature_2m_min.get(i),
                            temperature_2m_max.get(i),
                            rain_sum.get(i),
                            windspeed_10m_max.get(i)
                    )
            );
        }

        return dailyForecasts;
    }

    @Override
    public List<HourlyForecast> getHourlyForeCast(ForecastResponse forecastResponse, String date) {
        ArrayList<String> hourlyTimes = forecastResponse.getHourly().getTime();
        ArrayList<Double> temperature_2m = forecastResponse.getHourly().getTemperature_2m();
        ArrayList<Double> windspeed_10m = forecastResponse.getHourly().getWindspeed_10m();
        ArrayList<Double> rain = forecastResponse.getHourly().getRain();

        List<Integer> timeIndices = IntStream.range(0, hourlyTimes.size())
                .filter(i -> hourlyTimes.get(i).startsWith(date))
                .mapToObj(i -> i)
                .collect(Collectors.toList());

        List<HourlyForecast> hourlyForecasts = new ArrayList<>();
        for (Integer idx : timeIndices) {
            hourlyForecasts.add(
                    new HourlyForecast(
                            hourlyTimes.get(idx),
                            temperature_2m.get(idx),
                            rain.get(idx),
                            windspeed_10m.get(idx)
                    )
            );
        }

        return hourlyForecasts;
    }

}
