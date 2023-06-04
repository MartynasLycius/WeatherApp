package com.weather.app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.*;
import com.weather.app.model.DailyWeather;
import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.DailyWeatherView;
import com.weather.app.service.WeatherService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@PageTitle("Daily Weather")
@Route(value = "daily", layout = MainLayout.class)
public class WeatherViewDaily extends VerticalLayout implements HasUrlParameter<String> {
    Grid<DailyWeatherView> grid = new Grid<>(DailyWeatherView.class);
    public static String queryString;
    HorizontalLayout toolbar = new HorizontalLayout();
    private WeatherService weatherService;

    public WeatherViewDaily(WeatherService weatherService) {
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
        grid.setColumns("timezone", "time", "latitude", "longitude", "temperature_max", "temperature_min", "rain", "surfaceWind");
        grid.addColumn(createActionRenderer()).setHeader("Actions").setFrozenToEnd(true)
                .setAutoWidth(true).setFlexGrow(0);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private static Renderer<DailyWeatherView> createActionRenderer() {
        return LitRenderer.<DailyWeatherView>of(
                        "<vaadin-button @click=\"${checkWeather}\">Hourly Weather</vaadin-button>")
                .withFunction("checkWeather", dailyWeather -> {
                            UI.getCurrent().getPage().setLocation("hourly?selectedTime=" + dailyWeather.getTime() + "&latitude=" + dailyWeather.getLatitude()
                                    + "&longitude=" + dailyWeather.getLatitude());
                        }
                );
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String params) {
        queryString = beforeEvent.getLocation().getQueryParameters().getQueryString().replaceAll("%2F", "/");
        log.info("=====================setting query string with: {}", queryString);
        DailyWeatherResponseModel dailyWeatherResponseModel = weatherService.getWeatherDaily(queryString);
        List<DailyWeatherView> dailyWeatherViews = new ArrayList<>();
        if (dailyWeatherResponseModel == null) return;
        DailyWeather dailyWeather = dailyWeatherResponseModel.getDaily();
        for (int i = 0; i < dailyWeather.getTime().size(); i++) {
            dailyWeatherViews.add(DailyWeatherView.builder()
                    .time(dailyWeather.getTime().get(i))
                    .timezone(dailyWeatherResponseModel.getTimezone())
                    .rain(dailyWeather.getRain_sum().get(i))
                    .latitude(dailyWeatherResponseModel.getLatitude())
                    .longitude(dailyWeatherResponseModel.getLongitude())
                    .surfaceWind(dailyWeather.getWindspeed_10m_max().get(i))
                    .temperature_max(dailyWeather.getTemperature_2m_max().get(i))
                    .temperature_min(dailyWeather.getTemperature_2m_min().get(i))
                    .build());
        }
        grid.setItems(dailyWeatherViews);
    }
}