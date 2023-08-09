package com.example.application.views.list;

import com.example.application.data.service.WaService;
import com.example.application.dto.DailyForecast;
import com.example.application.dto.GeoCode;
import com.example.application.dto.HourlyForecast;
import com.example.application.views.DailyForecastView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Forecast extends VerticalLayout {

    Icon favoriteIcon = VaadinIcon.HEART.create();
    Button save = new Button(favoriteIcon);

    private GeoCode geoCode;

    private HourlyForecast hourlyForecast;

//    Span forecastHead;

    Span longLag;

    Span foreCastData;
    DailyForecastView dailyForecastView;

    public Forecast(){
        addClassName("forecast-view");

        dailyForecastView = new DailyForecastView();

        add(
           createButtonLayout(),
           dailyForecastView
        );

    }

    public void setGeoCode(GeoCode geoCode, DailyForecast dailyForecast, WaService waService) {
        this.geoCode = geoCode;
        dailyForecastView.setDailyForecastData(geoCode, dailyForecast, waService);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(save);
    }


}
