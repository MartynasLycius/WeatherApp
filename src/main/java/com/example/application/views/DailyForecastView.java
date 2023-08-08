package com.example.application.views;


import com.example.application.dto.DailyForecast;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@CssImport(value="./styles/MultipleAxes.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
public class DailyForecastView extends AbstractChartExample {

    Chart chart;
    String locationName;

    String Country;

    public void setDailyForecastData(String locationName, String Country, DailyForecast dailyForecast){
        this.locationName = locationName;
        this.Country = Country;
        if(chart != null){
            remove(chart);
        }
        chart = new Chart();
        Configuration conf = chart.getConfiguration();

        conf.getChart().setZoomType(Dimension.XY);
        conf.setTitle("Average Daily Weather Forecast for " + this.locationName + ", " + this.Country);
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
        labels.setFormatter("return this.value +' "+dailyForecast.getDailyUnits().getRain()+"'");
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

        add(chart);
    }

    @Override
    public void initDemo() {
    }
}
