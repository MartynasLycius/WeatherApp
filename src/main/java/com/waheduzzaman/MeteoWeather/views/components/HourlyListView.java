package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.data.model.ui.HourlyForecastItemForListViewModel;
import com.waheduzzaman.MeteoWeather.service.impl.WeatherDetailsServiceImpl;

public class HourlyListView extends VerticalLayout {
    private final WeatherDetailsServiceImpl weatherDetailsService;
    private final int currentIndex;
    private final Grid<HourlyForecastItemForListViewModel> grid;

    public HourlyListView(WeatherDetailsServiceImpl weatherDetailsService, int currentIndex) {
        this.weatherDetailsService = weatherDetailsService;
        this.currentIndex = currentIndex;
        this.grid = new Grid<>(HourlyForecastItemForListViewModel.class);
        initStyles();
        add(new CardHeader("24 Hours Forecast(Time,Temperature,Precipitation,Rain Sum, Wind)", "https://www.svgrepo.com/show/25968/24-hour-service.svg"));
        add(grid);

        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("time", "temperature", "precipitation", "rainSum", "wind");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addClassName("shadow-m");
        grid.addClassName("p-s");
        grid.addClassName(LumoUtility.BorderRadius.LARGE);
        grid.setItems(weatherDetailsService.getHourlyForecastItemForListView(currentIndex));
    }

    private void initStyles() {
        addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BorderRadius.LARGE
        );
        setWidthFull();
        setHeight("600px");
        getStyle().set("margin", "5px");
    }
}
