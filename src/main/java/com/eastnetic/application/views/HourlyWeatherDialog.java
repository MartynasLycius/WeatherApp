package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HourlyWeatherDialog extends Dialog {

    private final WeatherProviderService weatherProviderService;

    public HourlyWeatherDialog(String day,
                               LocationDetails location,
                               WeatherProviderService weatherProviderService) {

        this.weatherProviderService = weatherProviderService;

        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        WeatherData weatherData = getHourlyWeatherData(day, location);

        setHourlyData(weatherData);
    }

    private WeatherData getHourlyWeatherData(String day, LocationDetails locationDetails) {

        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);

        return weatherProviderService.getHourlyWeatherDataOfADay(
                locationDetails.getLatitude(), locationDetails.getLongitude(), locationDetails.getTimezone(), date
        );

    }

    private void setHourlyData(WeatherData weatherData) {

        VerticalLayout hourlyWeatherLayout = new VerticalLayout();
        hourlyWeatherLayout.setSpacing(true);

        int dataSize = weatherData.getHourlyWeather().getTime().size();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (int i=0; i<dataSize; i++) {

            String date = weatherData.getHourlyWeather().getTime().get(i);
            LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

            Div entryDiv = new Div();
            entryDiv.add(new Text("Time: " + dateTime.format(formatter) + " - "));
            entryDiv.add(new Text("Temperature: " + weatherData.getHourlyWeather().getTemperature().get(i) + " °C, "));
            entryDiv.add(new Text("Rain: " + weatherData.getHourlyWeather().getRain().get(i) + " mm, "));
            entryDiv.add(new Text("Wind Speed: " + weatherData.getHourlyWeather().getWindSpeed().get(i) + " km/h"));
            hourlyWeatherLayout.add(entryDiv);
        }

        add(hourlyWeatherLayout);
    }
}
