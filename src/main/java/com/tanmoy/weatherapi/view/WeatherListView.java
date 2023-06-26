package com.tanmoy.weatherapi.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanmoy.weatherapi.dto.DateTimeWiseWeatherDetailsDto;
import com.tanmoy.weatherapi.dto.WeatherResponseDto;
import com.tanmoy.weatherapi.entity.City;
import com.tanmoy.weatherapi.service.CityService;
import com.tanmoy.weatherapi.service.WeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

import java.util.Objects;


@Route(value = "")
@PageTitle("Weather Details")
@SpringComponent
@UIScope
@PermitAll
public class WeatherListView extends VerticalLayout {

    ComboBox<String> city = new ComboBox<>("Select City");
    WeatherForm weatherForm;
    Button button = new Button("Show hourly details");
    Button hide = new Button("Hide details");
    Grid<DateTimeWiseWeatherDetailsDto> grid = new Grid<>(DateTimeWiseWeatherDetailsDto.class, false);
    private final CityService cityService;
    private final WeatherService weatherService;

    public WeatherListView(CityService cityService, WeatherService weatherService, AuthenticationContext authContext) {
        this.cityService = cityService;
        this.weatherService = weatherService;
        weatherForm = new WeatherForm();
        addClassName("list-view");
        add(new MainLayout(authContext), getToolbar(), getContent());
    }

    private VerticalLayout getContent() {
        setGridColumnName();
        grid.setVisible(false);
        VerticalLayout content = new VerticalLayout(weatherForm, new HorizontalLayout(button, hide), grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void setGridColumnName() {
        grid.addColumn(DateTimeWiseWeatherDetailsDto::getTime).setHeader("Time");
        grid.addColumn(DateTimeWiseWeatherDetailsDto::getTemperature_2m).setHeader("Temperature");
        grid.addColumn(DateTimeWiseWeatherDetailsDto::getRelativehumidity_2m).setHeader("Humidity");
        grid.addColumn(DateTimeWiseWeatherDetailsDto::getWindspeed_10m).setHeader("Wind Speed");
    }

    private HorizontalLayout getToolbar() {
        city.setItems(cityService.getCityList().stream().map(c -> c.getCityName() + "-" + c.getCountryName()).toList());
        city.addValueChangeListener(l -> updateList());
        button.addClickListener(l -> grid.setVisible(true));
        hide.addClickListener(l -> grid.setVisible(false));
        HorizontalLayout content = new HorizontalLayout(city);
        content.addClassNames("city-selection-combo");
        return content;
    }

    private void updateList() {
        if (Objects.isNull(city.getValue())) return;
        City entity = cityService.findByCityNameAndCountryName(city.getValue().split("-")[0], city.getValue().split("-")[1]);
        try {
            WeatherResponseDto weatherResponseDto = weatherService.getWeatherDetailsByLatLng(entity.getLatitude(), entity.getLongitude());
            setWeatherFormLabelsData(weatherResponseDto);
            grid.setVisible(false);
            setGridItems(weatherResponseDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void setWeatherFormLabelsData(WeatherResponseDto weatherResponseDto) {
        weatherForm.latitude.setValue(String.valueOf(weatherResponseDto.getLatitude()));
        weatherForm.longitude.setValue(String.valueOf(weatherResponseDto.getLongitude()));
        weatherForm.timeZone.setValue(weatherResponseDto.getTimezone());
        weatherForm.temperature.setValue(String.valueOf(weatherResponseDto.getCurrent_weather().getTemperature()));
        weatherForm.windspeed.setValue(String.valueOf(weatherResponseDto.getCurrent_weather().getWindspeed()));
        weatherForm.winddireciton.setValue(String.valueOf(weatherResponseDto.getCurrent_weather().getWinddirection()));
        weatherForm.weatherCode.setValue(String.valueOf(weatherResponseDto.getCurrent_weather().getWeathercode()));
    }

    private void setGridItems(WeatherResponseDto weatherResponseDto) {
        grid.setRowsDraggable(true);
        grid.setItems(DateTimeWiseWeatherDetailsDto.getDateTimeWiseWeatherDetailsList(weatherResponseDto.getHourly()));
    }

}
