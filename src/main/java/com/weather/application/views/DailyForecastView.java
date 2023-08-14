package com.weather.application.views;


import com.weather.application.data.entity.Favourites;
import com.weather.application.service.FavouritesService;
import com.weather.application.service.WaService;
import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
import com.weather.application.data.dto.HourlyForecast;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.events.PointClickEvent;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.weather.application.util.Constants;

import java.util.logging.Logger;

@CssImport(value="./styles/MultipleAxes.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
public class DailyForecastView extends VerticalLayout {

    private static final Logger LOGGER = Logger.getLogger(DailyForecastView.class.getName());

    Chart chart;
    GeoCode geoCode;
    WaService waService;
    private FavouritesService favouritesService;
    private HorizontalLayout buttonLayout;
    private int isFavourite;
    private Long favouriteLocationId;
    public DailyForecastView(FavouritesService favouritesService){
        LOGGER.info("Initiate Daily Forecast View");
        this.favouritesService = favouritesService;
    }

    public void setDailyForecastData(GeoCode geoCode, DailyForecast dailyForecast, WaService waService){
        LOGGER.info("Set Daily Forecast Data Start");
        this.geoCode = geoCode;
        this.waService = waService;
        if(chart != null){
            remove(chart);
        }
        if(this.geoCode != null && this.geoCode.getId() != null){
            setFavouriteFlag(this.geoCode.getId());
        }
        chart = new Chart();
        Configuration conf = chart.getConfiguration();

        conf.getChart().setZoomType(Dimension.XY);
        if(this.geoCode != null){
            conf.setTitle("Average Daily Weather Forecast for " + this.geoCode.getName() + ", " + this.geoCode.getCountry());
        }
        conf.setSubTitle("Source: open-meteo.com");

        XAxis x = new XAxis();
        x.setCategories(dailyForecast.getDaily().getTime());
        conf.addxAxis(x);

        YAxis y1 = new YAxis();
        y1.setShowEmpty(false);
        y1.setTitle(new AxisTitle(Constants.TEMPERATURE_TEXT));
        Labels labels = new Labels();
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getTemperature2m()+"'");
        y1.setLabels(labels);
        y1.setOpposite(true);
        y1.setClassName("y1");
        conf.addyAxis(y1);

        YAxis y2 = new YAxis();
        y2.setShowEmpty(false);
        y2.setTitle(new AxisTitle(Constants.RAIN_FALL_TEXT));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getRain()+"'");
        y2.setLabels(labels);
        y2.setClassName("y2");
        conf.addyAxis(y2);

        YAxis y3 = new YAxis();
        y3.setShowEmpty(false);
        y3.setTitle(new AxisTitle(Constants.WIND_SPEED));
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
        series.setName(Constants.RAIN_FALL_TEXT);
        series.setyAxis(1);
        series.setData(dailyForecast.getDaily().getRain());
        conf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName(Constants.TEMPERATURE_TEXT);
        series.setData(dailyForecast.getDaily().getTemperature2m());
        conf.addSeries(series);

        series = new DataSeries();
        plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName(Constants.WIND_SPEED);
        series.setyAxis(2);
        series.setData(dailyForecast.getDaily().getWindspeed10m());
        conf.addSeries(series);

        chart.addPointClickListener(this::chartClicked);
        add(chart, createButtonLayout());
        LOGGER.info("Set Daily Forecast Data End");
    }

    private void setFavouriteFlag(Long id) {
        Favourites favourites = favouritesService.getFavouriteByUserIdAndGeoCodeId(id);
        if(favourites == null){
            this.isFavourite = 0;
        }
        else {
            this.isFavourite = favourites.getIsFavourite();
            this.favouriteLocationId = favourites.getId();
        }
    }


    public void chartClicked(PointClickEvent event) {
        HourlyForecast hourlyForecast = waService.getHourlyForecast(geoCode.getLatitude(), geoCode.getLongitude(), event.getCategory(), event.getCategory());
        openPopupHourlyForecast(hourlyForecast, event.getCategory());
    }

    private void openPopupHourlyForecast(HourlyForecast hourlyForecast, String date) {
        LOGGER.info("Set hourly forecast data start");
        Dialog dialog = new Dialog();

        VerticalLayout dialogLayout = new VerticalLayout();
        String chartTitle = "Hourly Weather forecast for City : %s, Country : %s and Date : %s";
        Span contentLabel = new Span(String.format(chartTitle, geoCode.getName(), geoCode.getCountry(), date));
        contentLabel.addClassNames("text-xl", "mt-m");
        contentLabel.setWidthFull();
        Chart hourlyChart = getHourlyForecastChart(hourlyForecast);

        Button closeButton = new Button("Close", e -> dialog.close());
        closeButton.getStyle().set(Constants.MARGIN_LEFT_TEXT, "auto");

        dialogLayout.add(contentLabel, hourlyChart, closeButton);
        dialogLayout.setWidthFull();
        dialog.add(dialogLayout);
        dialog.open();
        LOGGER.info("Set hourly forecast data end");
    }

    public Chart getHourlyForecastChart(HourlyForecast hourlyForecast){
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
        y1.setTitle(new AxisTitle(Constants.TEMPERATURE_TEXT));
        Labels labels = new Labels();
        labels.setFormatter("return this.value +' "+hourlyForecast.getHourlyUnits().getTemperature2m()+"'");
        y1.setLabels(labels);
        y1.setOpposite(true);
        y1.setClassName("y1");
        hourlyConf.addyAxis(y1);

        YAxis y2 = new YAxis();
        y2.setShowEmpty(false);
        y2.setTitle(new AxisTitle(Constants.RAIN_FALL_TEXT));
        labels = new Labels();
        labels.setFormatter("return this.value +' "+hourlyForecast.getHourlyUnits().getRain()+"'");
        y2.setLabels(labels);
        y2.setClassName("y2");
        hourlyConf.addyAxis(y2);

        YAxis y3 = new YAxis();
        y3.setShowEmpty(false);
        y3.setTitle(new AxisTitle(Constants.WIND_SPEED));
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
        series.setName(Constants.RAIN_FALL_TEXT);
        series.setyAxis(1);
        series.setData(hourlyForecast.getHourly().getRain());
        hourlyConf.addSeries(series);

        series = new DataSeries();
        PlotOptionsSpline plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName(Constants.TEMPERATURE_TEXT);
        series.setData(hourlyForecast.getHourly().getTemperature2m());
        hourlyConf.addSeries(series);

        series = new DataSeries();
        plotOptionsSpline = new PlotOptionsSpline();
        series.setPlotOptions(plotOptionsSpline);
        series.setName(Constants.WIND_SPEED);
        series.setyAxis(2);
        series.setData(hourlyForecast.getHourly().getWindspeed10m());
        hourlyConf.addSeries(series);

        return hourlyChart;
    }
    private Component createButtonLayout() {
        String buttonText = "";
        if(buttonLayout != null){
            remove(buttonLayout);
        }
        buttonLayout = new HorizontalLayout();
        buttonLayout.setAlignItems(Alignment.CENTER);
        Icon favoriteIcon = VaadinIcon.HEART.create();
        Button makeFavouriteButton = new Button(favoriteIcon);
        if (this.isFavourite == 1) {
            makeFavouriteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            buttonText = Constants.REMOVE_FAVOURITE_TEXT;
        } else {
            makeFavouriteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buttonText = Constants.MAKE_FAVOURITE_TEXT;
        }

        Span spanButtonText = new Span(buttonText);
        makeFavouriteButton.getStyle().set(Constants.MARGIN_LEFT_TEXT, "auto");
        makeFavouriteButton.addClickListener(e ->  makeLocationFavourite(geoCode));
        buttonLayout.add(makeFavouriteButton, spanButtonText);
        return buttonLayout;
    }

    private void makeLocationFavourite(GeoCode geoCode){
        LOGGER.info("Make location favourite start");
        String buttonText = "";
        if(buttonLayout != null){
            remove(buttonLayout);
        }
        buttonLayout = new HorizontalLayout();
        buttonLayout.setAlignItems(Alignment.CENTER);
        Icon favoriteIcon = VaadinIcon.HEART.create();
        Button makeFavouriteButton = new Button(favoriteIcon);
        makeFavouriteButton.getStyle().set(Constants.MARGIN_LEFT_TEXT, "auto");
        makeFavouriteButton.addClickListener(e ->  makeLocationFavourite(geoCode));
        if(this.isFavourite == 1){
            favouritesService.removeFavourites(this.favouriteLocationId);
            makeFavouriteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            this.isFavourite = 0;
            buttonText = Constants.MAKE_FAVOURITE_TEXT;
        }else {
            Favourites favourites = favouritesService.makeFavorites(geoCode);
            if(favourites != null){
                this.favouriteLocationId = favourites.getId();
            }
            makeFavouriteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            this.isFavourite = 1;
            buttonText = Constants.REMOVE_FAVOURITE_TEXT;
        }
        Span spanButtonText = new Span(buttonText);
        buttonLayout.add(makeFavouriteButton, spanButtonText);
        add(buttonLayout);
        LOGGER.info("Make location favourite end");
    }
}
