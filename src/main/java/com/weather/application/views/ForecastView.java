package com.weather.application.views;

import com.weather.application.data.service.FavouritesService;
import com.weather.application.data.service.WaService;
import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ForecastView extends VerticalLayout {
    DailyForecastView dailyForecastView;


    public ForecastView(FavouritesService favouritesService){
        addClassName("forecast-view");
        dailyForecastView = new DailyForecastView(favouritesService);

        add(
           dailyForecastView
        );
    }

    public void setGeoCode(GeoCode geoCode, DailyForecast dailyForecast, WaService waService) {
        dailyForecastView.setDailyForecastData(geoCode, dailyForecast, waService);
    }


}
