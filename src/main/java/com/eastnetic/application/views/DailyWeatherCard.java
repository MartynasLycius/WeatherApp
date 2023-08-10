package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DailyWeatherCard extends Div {

    public DailyWeatherCard(String day,
                            double maxTemperature,
                            double minTemperature,
                            String sunrise,
                            String sunset,
                            double rainSum,
                            double maxWindSpeed,
                            LocationDetails location,
                            WeatherProviderService weatherProviderService) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalDateTime sunriseDateTime = LocalDateTime.parse(sunrise, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime sunsetDateTime = LocalDateTime.parse(sunset, DateTimeFormatter.ISO_DATE_TIME);

        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.addClassName("info-layout");

        TextField dayField = new TextField("Day");
        dayField.setValue(day);
        dayField.setReadOnly(true);
        dayField.addClassName("day");

        TextField maxTemperatureField = new TextField("Max Temperature");
        maxTemperatureField.setValue(maxTemperature + " °C");
        maxTemperatureField.setReadOnly(true);
        maxTemperatureField.addClassName("max-temperature");

        TextField minTemperatureField = new TextField("Min Temperature");
        minTemperatureField.setValue(minTemperature + " °C");
        minTemperatureField.setReadOnly(true);
        minTemperatureField.addClassName("min-temperature");

        TextField sunriseField = new TextField("Sunrise");
        sunriseField.setValue(sunriseDateTime.format(formatter));
        sunriseField.setReadOnly(true);
        sunriseField.addClassName("sunrise");

        TextField sunsetField = new TextField("Sunset");
        sunsetField.setValue(sunsetDateTime.format(formatter));
        sunsetField.setReadOnly(true);
        sunsetField.addClassName("sunset");

        TextField rainField = new TextField("Rain");
        rainField.setValue(rainSum + "mm");
        rainField.setReadOnly(true);
        rainField.addClassName("rain");

        TextField maxWindSpeedField = new TextField("Max Wind Speed");
        maxWindSpeedField.setValue(maxWindSpeed + "km/h");
        maxWindSpeedField.setReadOnly(true);
        maxWindSpeedField.addClassName("max-wind-speed");

        infoLayout.add(dayField, maxTemperatureField, minTemperatureField, sunriseField, sunsetField, rainField, maxWindSpeedField);

        add(infoLayout);

        configureHourlyWeatherButton(day, location, weatherProviderService, infoLayout);
    }

    private void configureHourlyWeatherButton(String day,
                                              LocationDetails location,
                                              WeatherProviderService weatherProviderService,
                                              HorizontalLayout infoLayout) {

        Button showHourlyButton = new Button("Show Hourly Weather");
        showHourlyButton.setIcon(new Icon(VaadinIcon.ARROW_DOWN));

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        buttonLayout.add(showHourlyButton);

        showHourlyButton.addClickListener(event -> showHourlyWeatherDialog(day, location, weatherProviderService));

        add(infoLayout, buttonLayout);
    }

    private void showHourlyWeatherDialog(String selectedDay, LocationDetails location, WeatherProviderService weatherProviderService) {
        HourlyWeatherDialog dialog = new HourlyWeatherDialog(selectedDay, location, weatherProviderService);
        dialog.open();
    }
}
