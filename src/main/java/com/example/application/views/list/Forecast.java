package com.example.application.views.list;

import com.example.application.data.service.FavouritesService;
import com.example.application.data.service.WaService;
import com.example.application.dto.DailyForecast;
import com.example.application.dto.GeoCode;
import com.example.application.dto.HourlyForecast;
import com.example.application.views.DailyForecastView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Forecast extends VerticalLayout {


    private GeoCode geoCode;

    private HourlyForecast hourlyForecast;
    DailyForecastView dailyForecastView;
    private FavouritesService favouritesService;


    public Forecast(FavouritesService favouritesService){
        this.favouritesService = favouritesService;
        addClassName("forecast-view");

        dailyForecastView = new DailyForecastView(favouritesService);

        add(
           dailyForecastView
        );

    }

    public void setGeoCode(GeoCode geoCode, DailyForecast dailyForecast, WaService waService) {
        this.geoCode = geoCode;
        dailyForecastView.setDailyForecastData(geoCode, dailyForecast, waService);
    }


}
