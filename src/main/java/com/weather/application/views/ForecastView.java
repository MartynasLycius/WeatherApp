package com.weather.application.views;

import com.weather.application.service.FavouritesService;
import com.weather.application.service.WaService;
import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.logging.Logger;

public class ForecastView extends VerticalLayout {

    private static final Logger LOGGER = Logger.getLogger(ForecastView.class.getName());
    DailyForecastView dailyForecastView;


    public ForecastView(FavouritesService favouritesService){
        LOGGER.info("Initiate Forecast View");
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
