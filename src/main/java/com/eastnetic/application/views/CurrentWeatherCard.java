package com.eastnetic.application.views;

import com.eastnetic.application.weathers.entity.CurrentWeather;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@UIScope
public class CurrentWeatherCard extends Div {

    private final H5 currentWeatherText = new H5("Current Weather");

    private final TextField windSpeedField = new TextField("Wind Speed");

    private final TextField temperatureField = new TextField("Temperature");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

    public CurrentWeatherCard() {

        windSpeedField.setReadOnly(true);
        temperatureField.setReadOnly(true);

        HorizontalLayout dataContainer = new HorizontalLayout();
        dataContainer.add(temperatureField, windSpeedField);

        VerticalLayout infoContainer = new VerticalLayout();
        addClassName("info-container");
        infoContainer.add(currentWeatherText, dataContainer);

        add(infoContainer);
    }

    public void showCurrentWeather(CurrentWeather currentWeather) {

        LocalDateTime localDateTime = LocalDateTime.parse(currentWeather.getTime(), DateTimeFormatter.ISO_DATE_TIME);

        windSpeedField.setValue(currentWeather.getWindSpeed() + " m/s");
        temperatureField.setValue(currentWeather.getTemperature() + " °C");
        currentWeatherText.setText("Current Weather at " + localDateTime.format(formatter));
    }
}
