package com.nashid.weatherapp.views.graph;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.TitleSubtitle;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.datalables.builder.StyleBuilder;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.yaxis.Title;
import com.github.appreciated.apexcharts.helper.Series;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.services.WeatherService;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherHourlyGraph extends VerticalLayout {

    private final ApexCharts chart;

    public WeatherHourlyGraph(List<Map> hourlyForecast, WeatherService weatherService) {
        addClassName("chart-container-class");
        Div chartContainer = new Div();
        chartContainer.setId("chart-container");

        /*chart = ApexChartsBuilder.get()
                .withTheme(ThemeBuilder.get().build())
                .withChart(ChartBuilder.get()
                        .withType(Type.LINE)
                        .withToolbar(ToolbarBuilder.get().withShow(false).build())
                        .withHeight("300px")
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(true)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .withStyle(StyleBuilder.get().build())
                        .build())
                .withSeries(new Series(new Double[0]))
                .withXaxis(XAxisBuilder.get()
                        .withCategories("0")
                        .build())
                .build();*/
        List<String> categories = new ArrayList<>();
        List<Double> temparatureSeries = new ArrayList<>();
        List<Double> windSeries = new ArrayList<>();
        List<Double> precipitationSeries = new ArrayList<>();
        hourlyForecast.forEach(map -> {
            categories.add(map.get("time").toString());
            temparatureSeries.add(Double.valueOf(map.get("tempRaw").toString()));
            windSeries.add(Double.valueOf(map.get("windRaw").toString()));
            precipitationSeries.add(Double.valueOf(map.get("rainRaw").toString()));
        });
        Double[] tempArray = temparatureSeries.toArray(new Double[0]);
        Double[] windArray = windSeries.toArray(new Double[0]);
        Double[] precipitationArray = precipitationSeries.toArray(new Double[0]);
        Settings settings = weatherService.getCurrentUserSettings();
        chart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.LINE).build())
                .withTitle(TitleSubtitleBuilder.get().withText("Hourly Weather").withAlign(Align.CENTER).build())
                .withLegend(LegendBuilder.get().withPosition(Position.BOTTOM).build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.SMOOTH).build())
                .withFill(FillBuilder.get().withType("solid").build())
                .withXaxis(XAxisBuilder.get().withCategories(categories).build())
                .withSeries(
                        new Series<>("Temperature (".concat(settings.getTemperatureUnit().toString()).concat(")"), tempArray),
                        new Series<>("Surface Wind (".concat(settings.getWindSpeedUnit().toString()).concat(")"), windArray),
                        new Series<>("Rain (".concat(settings.getPrecipitationUnit().toString()).concat(")"), precipitationArray)
                )
                .build();
        chart.setHeight("400px");
        chartContainer.add(chart);

        add(chartContainer);
    }

}
