package com.proit.weather.view;

import com.proit.weather.dto.DailyUnitDetails;
import com.proit.weather.dto.HourlyUnitDetails;
import com.proit.weather.dto.WeatherInfo;
import com.proit.weather.utils.Messages;
import com.proit.weather.view.chart.HighChartsComponent;
import com.proit.weather.view.litgrid.PaginatedGrid;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.format.DateTimeFormatter;

import static com.proit.weather.utils.KEY_CONSTANTS.HOURLY_FORECAST_TITLE;

@Route(value = "hourly_forecast", layout = MainLayout.class)
public class HourlyForecast extends VerticalLayout {
    private static final String HEADING = "%s of %s on %s";
    private static final String DTF = "dd MMM yyyy";
    private final H3 title = new H3();
    private String cityName;
    private WeatherInfo weatherInfo;
    private final Grid<HourlyUnitDetails.HourlyDetails> grid = new PaginatedGrid<>(HourlyUnitDetails.HourlyDetails.class);

    public HourlyForecast() {
        grid.setPageSize(10);
        add(title, grid);
    }

    public void showHourlyWeatherForecast(String cityName, WeatherInfo weatherInfo, DailyUnitDetails.DailyDetails dailyDetails) {
        this.cityName = cityName;
        this.weatherInfo = weatherInfo;
        prepareView(dailyDetails);
    }

    public void prepareView(DailyUnitDetails.DailyDetails dailyDetails) {
        grid.setItems(weatherInfo.getHourlyForecast(dailyDetails));
        HighChartsComponent<HourlyUnitDetails> highChartsComponent = new HighChartsComponent<>(weatherInfo.getHourlyUnitDetails());
        String date = dailyDetails.getDate().format(DateTimeFormatter.ofPattern(DTF));
        String titleText = String.format(HEADING, Messages.get(HOURLY_FORECAST_TITLE), cityName, date);
        title.setText(titleText);
        add(highChartsComponent);
    }
}
