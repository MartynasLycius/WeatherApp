package com.proit.weather.proit.weather.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proit.weather.proit.weather.service.LocationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public String getLocations(@RequestParam String cityName) {
        return locationService.getLocationData(cityName);
    }
}

