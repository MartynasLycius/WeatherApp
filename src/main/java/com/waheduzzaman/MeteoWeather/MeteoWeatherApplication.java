package com.waheduzzaman.MeteoWeather;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Meteo Weather", shortName = "Meteo Weather", offlinePath = "offline.html", offlineResources = {"images/offline.png"})
@Theme(value = "meteoweather")
public class MeteoWeatherApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MeteoWeatherApplication.class, args);
    }
}
