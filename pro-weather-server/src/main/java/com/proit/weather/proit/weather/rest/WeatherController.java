package com.proit.weather.proit.weather.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proit.weather.proit.weather.service.WeatherService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public String getWeather(@RequestParam Float latitude, @RequestParam Float longitude, @RequestParam boolean isHour, @RequestParam String day) {
    	System.out.println("success call weather=============================+"+isHour);
        return weatherService.getWeatherData(latitude, longitude, isHour, day);
    }
}
