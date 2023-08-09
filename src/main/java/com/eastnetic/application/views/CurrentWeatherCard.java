package com.eastnetic.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentWeatherCard extends Div {

    public CurrentWeatherCard(double temperature, double windSpeed, String location, String dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);

        addClassName("weather-card");

        FlexLayout infoContainer = new FlexLayout();
        infoContainer.addClassName("info-container");

        H3 locationText = new H3(location);
        locationText.addClassName("location");

        H5 currentWeatherText = new H5("Current Weather");
        locationText.addClassName("current-weather");

        FlexLayout dataContainer = new FlexLayout();
        dataContainer.addClassName("data-container");

        TextField temperatureField = new TextField("Temperature");
        temperatureField.setValue(temperature + " °C");
        temperatureField.setReadOnly(true);
        temperatureField.addClassName("temperature");

        TextField windSpeedField = new TextField("Wind Speed");
        windSpeedField.setValue(windSpeed + " m/s");
        windSpeedField.setReadOnly(true);
        windSpeedField.addClassName("wind-speed");

        TextField dateTimeField = new TextField("Date and Time");
        dateTimeField.setValue(localDateTime.format(formatter));
        dateTimeField.setReadOnly(true);
        dateTimeField.addClassName("date-time");

        dataContainer.add(temperatureField, windSpeedField, dateTimeField);

        infoContainer.add(locationText, currentWeatherText, dataContainer);
        add(infoContainer);
    }
}
