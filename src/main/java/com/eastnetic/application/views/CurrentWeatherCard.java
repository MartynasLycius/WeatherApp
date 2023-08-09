package com.eastnetic.application.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CurrentWeatherCard extends Div {

    public CurrentWeatherCard(double temperature, double windSpeed, String location, String dateTime) {

        addClassName("weather-card");

        FlexLayout infoContainer = new FlexLayout();
        infoContainer.addClassName("info-container");

        TextField locationField = new TextField("Location");
        locationField.setValue(location);
        locationField.setReadOnly(true);
        locationField.addClassName("location");

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
        dateTimeField.setValue(dateTime);
        dateTimeField.setReadOnly(true);
        dateTimeField.addClassName("date-time");

        dataContainer.add(temperatureField, windSpeedField, dateTimeField);

        infoContainer.add(locationField, dataContainer);
        add(infoContainer);
    }
}
