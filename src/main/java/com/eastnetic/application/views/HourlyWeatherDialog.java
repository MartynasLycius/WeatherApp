package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.entity.HourlyWeather;
import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HourlyWeatherDialog extends Dialog {

    private final WeatherProviderService weatherProviderService;

    public HourlyWeatherDialog(String day,
                               LocationDetails location,
                               WeatherProviderService weatherProviderService) {

        this.weatherProviderService = weatherProviderService;

        setWidth("50%");
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);

        WeatherData weatherData = getHourlyWeatherData(day, location);

        setHourlyData(weatherData.getHourlyWeather());
    }

    private WeatherData getHourlyWeatherData(String day, LocationDetails locationDetails) {

        LocalDate date = LocalDate.parse(day, DateTimeFormatter.ISO_DATE);

        return weatherProviderService.getHourlyWeatherDataOfADay(
                locationDetails.getLatitude(), locationDetails.getLongitude(), locationDetails.getTimezone(), date
        );

    }

    private void setHourlyData(HourlyWeather hourlyWeather) {

        List<HourlyWeatherGridData> hourlyWeatherGridDataList = new ArrayList<>();

        int dataSize = hourlyWeather.getTime().size();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (int i=0; i<dataSize; i++) {

            String date = hourlyWeather.getTime().get(i);
            LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);

            hourlyWeatherGridDataList.add(
                    new HourlyWeatherGridData(
                            dateTime.format(formatter),
                            hourlyWeather.getRain().get(i) + " mm",
                            hourlyWeather.getWindSpeed().get(i) + " km/h",
                            hourlyWeather.getTemperature().get(i) + " °C"
                    ));
        }

        Grid<HourlyWeatherGridData> hourlyWeatherGrid = new Grid<>(HourlyWeatherGridData.class);
        hourlyWeatherGrid.setItems(hourlyWeatherGridDataList);

        add(hourlyWeatherGrid);
    }

    public static class HourlyWeatherGridData {

        public String time;

        private String rain;

        private String windSpeed;

        private String temperature;

        public HourlyWeatherGridData(String time, String rain, String windSpeed, String temperature) {
            this.time = time;
            this.rain = rain;
            this.windSpeed = windSpeed;
            this.temperature = temperature;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRain() {
            return rain;
        }

        public void setRain(String rain) {
            this.rain = rain;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }
    }
}
