package com.example.application.views;


import com.example.application.data.service.WaService;
import com.example.application.dto.DailyForecast;
import com.example.application.dto.GeoCode;
import com.example.application.dto.HourlyForecast;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.events.PointClickEvent;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@CssImport(value="./styles/MultipleAxes.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
public class DailyForecastView extends AbstractChartExample {

    Chart chart;
    GeoCode geoCode;
    WaService waService;
    String userId;


    public void setDailyForecastData(GeoCode geoCode, DailyForecast dailyForecast, WaService waService){
        this.geoCode = geoCode;
        this.waService = waService;
        if(chart != null){
            remove(chart);
        }
        chart = new Chart();
        Configuration conf = chart.getConfiguration();

        conf.getChart().setZoomType(Dimension.XY);
        conf.setTitle("Average Daily Weather Forecast for " + this.geoCode.getName() + ", " + this.geoCode.getCountry());
        conf.setSubTitle("Source: open-meteo.com");

        XAxis x = new XAxis();
        x.setCategories(dailyForecast.getDaily().getTime());
        conf.addxAxis(x);

        YAxis y1 = new YAxis();
        y1.setShowEmpty(false);
        y1.setTitle(new AxisTitle("Temperature"));
        Labels labels = new Labels();
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getTemperature2m()+"'");
        y1.setLabels(labels);
        y1.setOpposite(true);
        y1.setClassName("y1");
        conf.addyAxis(y1);

        YAxis y2 = new YAxis();
        y2.setShowEmpty(false);
        y2.setTitle(new AxisTitle("Rainfall"));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getRain()+"'");
        y2.setLabels(labels);
        y2.setClassName("y2");
        conf.addyAxis(y2);

        YAxis y3 = new YAxis();
        y3.setShowEmpty(false);
        y3.setTitle(new AxisTitle("Wind Speed"));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getWindspeed10m()+"'");
        y3.setLabels(labels);
        y3.setOpposite(true);
        y3.setClassName("y3");
        conf.addyAxis(y3);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { "
                + "var unit = { 'Rainfall': '"+dailyForecast.getDailyUnits().getRain()+"', 'Temperature': '"+dailyForecast.getDailyUnits().getTemperature2m()+"', 'Wind Speed': '"+dailyForecast.getDailyUnits().getRain()+"' }[this.series.name];"
                + "return ''+ this.x +': '+ this.y +' '+ unit; }");
        conf.setTooltip(tooltip);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setX(120);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setY(80);
        legend.setFloating(true);
        conf.setLegend(legend);

        DataSeries series = new DataSeries();
        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        series.setPlotOptions(plotOptionsColumn);
        series.setName("Rainfall");
        series.setyAxis(1);
        series.setData(dailyForecast.getDaily().getRain());
        conf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName("Temperature");
        series.setData(dailyForecast.getDaily().getTemperature2m());
        conf.addSeries(series);

        series = new DataSeries();
        plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName("Wind Speed");
        series.setyAxis(2);
        series.setData(dailyForecast.getDaily().getWindspeed10m());
        conf.addSeries(series);

        chart.addPointClickListener(this::chartClicked);

        add(chart);
    }


    public void chartClicked(PointClickEvent event) {
        HourlyForecast hourlyForecast = waService.getHourlyForecast(geoCode.getLatitude(), geoCode.getLongitude(), event.getCategory(), event.getCategory());
        openPopupHourlyForecast(hourlyForecast, event.getCategory());
    }

    private void setUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            this.userId = authentication.getName();
        }
    }

    private void openPopupHourlyForecast(HourlyForecast hourlyForecast, String date) {
        Dialog dialog = new Dialog();

        VerticalLayout dialogLayout = new VerticalLayout();
        String chartTitle = "Hourly Weather forecast for City : %s, Country : %s and Date : %s";
        Span contentLabel = new Span(String.format(chartTitle, geoCode.getName(), geoCode.getCountry(), date));
        contentLabel.addClassNames("text-xl", "mt-m");
        contentLabel.setWidthFull();
        Chart hourlyChart = getHourlyForecastChart(hourlyForecast, date);

        Button closeButton = new Button("Close", e -> dialog.close());
        closeButton.getStyle().set("margin-left", "auto");

        dialogLayout.add(contentLabel, hourlyChart, closeButton);
        dialogLayout.setWidthFull();
        dialog.add(dialogLayout);
        dialog.open();
    }

    public Chart getHourlyForecastChart(HourlyForecast hourlyForecast, String date){

        Chart hourlyChart = new Chart();
        Configuration hourlyConf = hourlyChart.getConfiguration();

        hourlyConf.getChart().setZoomType(Dimension.XY);
        hourlyConf.setTitle("Hourly Weather Forecast");
        hourlyConf.setSubTitle("Source: open-meteo.com");

        XAxis x = new XAxis();
        x.setCategories(hourlyForecast.getHourly().getTime());
        hourlyConf.addxAxis(x);

        YAxis y1 = new YAxis();
        y1.setShowEmpty(false);
        y1.setTitle(new AxisTitle("Temperature"));
        Labels labels = new Labels();
        labels.setFormatter("return this.value +' "+hourlyForecast.getHourlyUnits().getTemperature2m()+"'");
        y1.setLabels(labels);
        y1.setOpposite(true);
        y1.setClassName("y1");
        hourlyConf.addyAxis(y1);

        YAxis y2 = new YAxis();
        y2.setShowEmpty(false);
        y2.setTitle(new AxisTitle("Rainfall"));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+hourlyForecast.getHourlyUnits().getRain()+"'");
        y2.setLabels(labels);
        y2.setClassName("y2");
        hourlyConf.addyAxis(y2);

        YAxis y3 = new YAxis();
        y3.setShowEmpty(false);
        y3.setTitle(new AxisTitle("Wind Speed"));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+hourlyForecast.getHourlyUnits().getWindspeed10m()+"'");
        y3.setLabels(labels);
        y3.setOpposite(true);
        y3.setClassName("y3");
        hourlyConf.addyAxis(y3);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("function() { "
                + "var unit = { 'Rainfall': '"+hourlyForecast.getHourlyUnits().getRain()
                +"', 'Temperature': '"+hourlyForecast.getHourlyUnits().getTemperature2m()
                +"', 'Wind Speed': '"+hourlyForecast.getHourlyUnits().getWindspeed10m()
                +"' }[this.series.name];"
                + "return ''+ this.x +': '+ this.y +' '+ unit; }");
        hourlyConf.setTooltip(tooltip);

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setX(120);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setY(80);
        legend.setFloating(true);
        hourlyConf.setLegend(legend);

        DataSeries series = new DataSeries();
        PlotOptionsColumn plotOptionsColumn = new PlotOptionsColumn();
        series.setPlotOptions(plotOptionsColumn);
        series.setName("Rainfall");
        series.setyAxis(1);
        series.setData(hourlyForecast.getHourly().getRain());
        hourlyConf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName("Temperature");
        series.setData(hourlyForecast.getHourly().getTemperature2m());
        hourlyConf.addSeries(series);

        series = new DataSeries();
        plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName("Wind Speed");
        series.setyAxis(2);
        series.setData(hourlyForecast.getHourly().getWindspeed10m());
        hourlyConf.addSeries(series);

        return hourlyChart;
    }

    @Override
    public void initDemo() {
    }
}
