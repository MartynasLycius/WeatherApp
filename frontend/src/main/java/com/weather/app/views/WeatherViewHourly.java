package com.weather.app.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.weather.app.model.HourlyWeather;
import com.weather.app.model.HourlyWeatherResponseModel;
import com.weather.app.model.HourlyWeatherView;
import com.weather.app.service.WeatherService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PageTitle("Hourly Weather")
@Route(value = "hourly", layout = MainLayout.class)
public class WeatherViewHourly extends VerticalLayout implements HasUrlParameter<String> {
    Grid<HourlyWeatherView> grid = new Grid<>(HourlyWeatherView.class);
    public static String queryString;
    HorizontalLayout toolbar = new HorizontalLayout();
    private WeatherService weatherService;

    public WeatherViewHourly(WeatherService weatherService) {
        this.weatherService = weatherService;
        toolbar.addClassName("toolbar");
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        add(toolbar, grid);
    }

    private void configureGrid() {
        log.debug("================Query params: {}", queryString);
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("times", "temperature", "rain", "surfaceWind");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String params) {
        queryString = beforeEvent.getLocation().getQueryParameters().getQueryString().replaceAll("%2F", "/");
        String selectedTimeReg = "(?ism)selectedTime=(.*?)&";
        Pattern pattern = Pattern.compile(selectedTimeReg);
        Matcher matcher = pattern.matcher(queryString);
        String selectedTime = matcher.find() ? matcher.group(1) : "";
        queryString = queryString.replaceAll(selectedTimeReg, "");
        log.info("=====================setting query string with: {}", queryString);
        HourlyWeatherResponseModel hourlyWeatherResponseModel = weatherService.getWeatherHourly(queryString);
        List<HourlyWeatherView> hourlyWeatherViews = new ArrayList<>();
        if (hourlyWeatherResponseModel == null) return;
        HourlyWeather hourlyWeather = hourlyWeatherResponseModel.getHourly();
        for (int i = 0; i < hourlyWeather.getTime().size(); i++) {
            String time = hourlyWeather.getTime().get(i);
            if (time.startsWith(selectedTime)) {
                hourlyWeatherViews.add(HourlyWeatherView.builder()
                        .times(time)
                        .rain(hourlyWeather.getRain().get(i))
                        .surfaceWind(hourlyWeather.getWindspeed_10m().get(i))
                        .temperature(hourlyWeather.getTemperature_2m().get(i))
                        .build());
            }
        }
        grid.setItems(hourlyWeatherViews);
    }
}