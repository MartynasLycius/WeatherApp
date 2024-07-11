package com.proit.weather.view;

import com.proit.weather.dto.DailyUnitDetails;
import com.proit.weather.dto.WeatherInfo;
import com.proit.weather.service.WeatherInfoService;
import com.proit.weather.utils.Messages;
import com.proit.weather.view.chart.HighChartsComponent;
import com.proit.weather.view.litgrid.PaginatedGrid;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

import static com.proit.weather.utils.KEY_CONSTANTS.DAILY_FORECAST_TITLE;

@Route(value = "daily_forecast", layout = MainLayout.class)
public class DailyForecast extends VerticalLayout {
    private static final String NOTIFICATION_MSG = "Cannot show data after: %s";
    private static final String HEADING = "%s, %s";
    private String cityName;
    private Double longitude;
    private Double latitude;
    private final Grid<DailyUnitDetails.DailyDetails> grid = new PaginatedGrid<>(DailyUnitDetails.DailyDetails.class);
    private final H3 title = new H3();
    private HighChartsComponent<DailyUnitDetails> highChartsComponent;
    private WeatherInfo weatherForeCast;
    public DailyForecast() {
        DatePicker datePicker = new DatePicker();
        datePicker.addValueChangeListener(this::onDateChange);
        add(title, datePicker, grid);
        grid.setPageSize(10);
    }

    public void showDailyWeather(String cityName, Double longitude, Double latitude) {
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
        weatherForeCast = WeatherInfoService.getWeatherInformation(latitude, longitude);
        prepareView();
    }

    public void prepareView() {
        grid.setItems(weatherForeCast.getDailyForecast());
        grid.addItemClickListener(item -> UI.getCurrent().navigate(HourlyForecast.class)
                .ifPresent(editor -> editor.showHourlyWeatherForecast(cityName, weatherForeCast, item.getItem())));
        highChartsComponent = new HighChartsComponent<>(weatherForeCast.getDailyUnitDetails());
        title.setText(String.format(HEADING, Messages.get(DAILY_FORECAST_TITLE), cityName));
        add(highChartsComponent);
    }

    private void onDateChange(AbstractField.ComponentValueChangeEvent<DatePicker, LocalDate> event) {
        LocalDate selectedDate = event.getValue();
        LocalDate allowedDate = LocalDate.now().plusDays(14);
        if (selectedDate.isAfter(allowedDate)) {
            Notification.show(String.format(NOTIFICATION_MSG, allowedDate));
            return;
        }
        weatherForeCast = WeatherInfoService.getWeatherInformationByDate(latitude, longitude, selectedDate, selectedDate);
        highChartsComponent = new HighChartsComponent<>(weatherForeCast.getDailyUnitDetails());
        prepareView();
    }
}
