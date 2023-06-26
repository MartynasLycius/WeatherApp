package com.tanmoy.weatherapi.view;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.textfield.TextField;

public class WeatherForm extends FormLayout {

    NativeLabel currentWeatherInfo = new NativeLabel("Current Weather Information");
    NativeLabel locationInfo = new NativeLabel("Location Information");
    TextField latitude = new TextField("Latitude");
    TextField longitude = new TextField("Longitude");
    TextField timeZone = new TextField("TimeZone");
    NativeLabel temperatureInfo = new NativeLabel("Temperature Information");
    TextField temperature = new TextField("Current Temperature");
    TextField windspeed = new TextField("Current Wind Speed");
    TextField winddireciton = new TextField("Current Wind Direction");
    TextField weatherCode = new TextField("Current Weather Code");

    public WeatherForm() {
        addClassName("contact-form");
        add(currentWeatherInfo, locationInfo, latitude, longitude, timeZone, temperatureInfo, temperature, windspeed, winddireciton, weatherCode);
        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("200px", 4)
        );
        // Stretch the currentWeatherInfo, locationInfo, temperatureInfo field over 4 columns
        setColspan(currentWeatherInfo, 4);
        setColspan(locationInfo, 4);
        setColspan(temperatureInfo, 4);
    }

}
