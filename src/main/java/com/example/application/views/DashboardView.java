package com.example.application.views;

import com.example.application.data.service.WaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | WeatherApp")
@PermitAll
public class DashboardView extends VerticalLayout {
    private WaService waService;

    public DashboardView(WaService waService) {
        this.waService = waService;
        addClassNames("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getDailyForecast(), getCompaniesChart());
    }

    private Component getDailyForecast() {
        Span dailyForecast = new Span(" daily forecast");
        dailyForecast.addClassNames("text-xl", "mt-m");
        return dailyForecast;
    }

    private Component getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
