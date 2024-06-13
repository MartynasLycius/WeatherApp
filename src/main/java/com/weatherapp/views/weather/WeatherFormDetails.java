package com.weatherapp.views.weather;

import com.vaadin.flow.component.html.NativeLabel;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.weatherapp.detail.DailyUnitsDetail;
import com.weatherapp.detail.HourlyUnitsDetail;
import com.weatherapp.detail.LocationInformation;
import com.weatherapp.detail.WeatherData;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import org.vaadin.addons.chartjs.ChartJs;
import org.vaadin.addons.chartjs.config.BarChartConfig;
import org.vaadin.addons.chartjs.data.BarDataset;
import org.vaadin.addons.chartjs.data.Dataset;
import org.vaadin.addons.chartjs.data.LineDataset;
import org.vaadin.addons.chartjs.options.Position;

import java.util.ArrayList;
import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created on 09 Jun, 2024
 */
public class WeatherFormDetails extends FormLayout {
    private WebClient client;
    private ChartJs chart = new ChartJs();
    private BarChartConfig config = new BarChartConfig();
    private Grid<DailyUnitsDetail> gridDaily = new Grid<>(DailyUnitsDetail.class);
    private Grid<HourlyUnitsDetail> gridHourly = new Grid<>(HourlyUnitsDetail.class);
    private  NativeLabel labelDaily = new NativeLabel("Weather Daily Details");
    private NativeLabel labelHourly = new NativeLabel("Weather Hourly Details");


    private String baseURLForWeatherData = "https://api.open-meteo.com/v1/forecast";
    public WeatherFormDetails(WebClient.Builder builder) {
        this.client = builder.baseUrl(baseURLForWeatherData).build();
        addClassName("weather-detail-form");
        configureGrids();
        VerticalLayout content = createContentLayout();
        add(content);
        buildChart(null);
        add(chart);
    }
    private VerticalLayout createContentLayout() {
        VerticalLayout verticalLayoutDaily = new VerticalLayout();
        verticalLayoutDaily.add(labelDaily, gridDaily);
        VerticalLayout verticalLayoutHourly = new VerticalLayout();
        verticalLayoutHourly.add(labelHourly, gridHourly);
        VerticalLayout content = new VerticalLayout();
        content.add(verticalLayoutDaily, verticalLayoutHourly);
        content.setFlexGrow(2, gridDaily);
        content.setFlexGrow(4, gridHourly);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }
    private void configureGrids() {
        configureGridDaily();
        configureGridHourly();
    }

    void setWeatherDetails(LocationInformation locationInformation) {
        WeatherData weatherData = fetchWeatherData(locationInformation);
        List<DailyUnitsDetail> dailyDetails = createDailyDetails(weatherData, locationInformation);
        gridDaily.setItems(dailyDetails);
    }

    private WeatherData fetchWeatherData(LocationInformation locationInformation) {
        return client.get()
                .uri(uri -> uri.path("/")
                        .queryParam("latitude", locationInformation.getLatitude())
                        .queryParam("longitude", locationInformation.getLongitude())
                        .queryParam("daily", "weather_code,wind_speed_10m_max,temperature_2m_max,rain_sum")
                        .build())
                .retrieve()
                .toEntity(WeatherData.class)
                .block()
                .getBody();
    }

    private List<DailyUnitsDetail> createDailyDetails(WeatherData weatherData, LocationInformation locationInformation) {
        List<DailyUnitsDetail> dailyDetails = new ArrayList<>();
        for (int i = 0; i < weatherData.getDaily().getTime().size(); i++) {
            dailyDetails.add(new DailyUnitsDetail(
                    weatherData.getDaily().getTime().get(i),
                    weatherData.getDaily().getWeather_code().get(i),
                    weatherData.getDaily().getWind_speed_10m_max().get(i),
                    locationInformation.getLatitude(),
                    locationInformation.getLongitude(),
                    weatherData.getDaily().getRain_sum().get(i),
                    weatherData.getDaily().getTemperature_2m_max().get(i)
            ));
        }
        return dailyDetails;
    }


    private void setWeatherHourly(DailyUnitsDetail value) {
        WeatherData weatherData = fetchHourlyWeatherData(value);
        List<HourlyUnitsDetail> hourlyDetails = createHourlyDetails(weatherData);
        gridHourly.setItems(hourlyDetails);
        buildChart(weatherData);
    }

    private WeatherData fetchHourlyWeatherData(DailyUnitsDetail value) {
        if(value == null) {
            return null;
        }
        return client.get().uri(uri -> uri.path("/")
                        .queryParam("latitude", value.getLatitude())
                        .queryParam("longitude", value.getLongitude())
                        .queryParam("hourly", "temperature_2m,rain,surface_pressure,wind_speed_10m")
                        .queryParam("start_date", value.getTime())
                        .queryParam("end_date", value.getTime())
                        .build())
                .retrieve()
                .toEntity(WeatherData.class)
                .block()
                .getBody();
    }

    private List<HourlyUnitsDetail> createHourlyDetails(WeatherData weatherData) {
        if(weatherData == null) {
            return new ArrayList<>();
        }
        List<HourlyUnitsDetail> hourlyDetails = new ArrayList<>();
        for (int i = 0; i < weatherData.getHourly().getTime().size(); i++) {
            hourlyDetails.add(new HourlyUnitsDetail(
                    weatherData.getHourly().getTime().get(i),
                    weatherData.getHourly().getTemperature_2m().get(i),
                    weatherData.getHourly().getSurface_pressure().get(i),
                    weatherData.getHourly().getWind_speed_10m().get(i),
                    weatherData.getHourly().getRain().get(i)
            ));
        }
        return hourlyDetails;
    }

    public void buildChart(WeatherData weatherData) {


            config = new BarChartConfig();
            config
                    .data()
                    //.labelsAsList(weatherData.getHourly().getTime())
                    .addDataset(new BarDataset().type().label("Wind speed").backgroundColor("rgba(60, 179, 113,0.5)").borderColor("white").borderWidth(2))
                    .addDataset(new LineDataset().type().label("Temperature").backgroundColor("rgba(151,187,205,0.5)").borderColor("white").borderWidth(2))
//                .addDataset(new BarDataset().type().label("Dataset 3").backgroundColor("rgba(220,220,220,0.5)"))
                    .and();

        config.
                options()
                .responsive(true)
                .title()
                .display(true)
                .position(Position.LEFT)
                .text("Hourly Temperature")
                .and()
                .done();


        if (weatherData != null) {

            config.data().labelsAsList(weatherData.getHourly().getTime());
            List<String> labels = config.data().getLabels();
            for (Dataset<?, ?> ds : config.data().getDatasets()) {
                List<Double> data = new ArrayList<>();



                if (ds instanceof BarDataset) {
                    for (int i = 0; i < labels.size(); i++) {
                        data.add((double) (weatherData.getHourly().getWind_speed_10m().get(i)));
                    }
                    BarDataset bds = (BarDataset) ds;
                    bds.dataAsList(data);
                }

                if (ds instanceof LineDataset) {

                    for (int i = 0; i < labels.size(); i++) {
                        data.add((double) (weatherData.getHourly().getTemperature_2m().get(i)));
                    }
                    LineDataset lds = (LineDataset) ds;
                    lds.dataAsList(data);
                }
            }

        }

        chart.configure(config);
        chart.setWidth("100%");
        chart.setHeight("5px");
        chart.refreshData();
    }

    private void configureGridDaily() {
        gridDaily.addClassNames("contact-grid-daily");

        gridDaily.setColumns("time","weather_code", "wind_speed_10m_max", "temperature_2m_max", "rain_sum");

        gridDaily.getColumns().forEach(col -> col.setAutoWidth(true));
        gridDaily.asSingleSelect().addValueChangeListener(event -> setWeatherHourly(event.getValue()));
        gridDaily.getColumns().forEach(col ->
                {

                    switch (col.getKey()) {
                        case "time":
                            col.setHeader("Date");
                            break;
                        case "weather_code":
                            col.setHeader("Weather Code");
                            break;
                        case "wind_speed_10m_max":
                            col.setHeader("Wind Speed (10m)");
                            break;
                        case "temperature_2m_max":
                            col.setHeader("Temperature (2m)");
                            break;
                        case "rain_sum":
                            col.setHeader("Rain");
                            break;
                    }

                }
        );

    }
    private void configureGridHourly() {
        gridHourly.addClassNames("grid-hourly");

        gridHourly.setColumns("time", "temperature_2m", "surface_pressure", "wind_speed_10m", "rain");


        gridHourly.getColumns().forEach(col -> col.setAutoWidth(true));

        gridHourly.getColumns().forEach(col ->
                {

                    switch (col.getKey()) {
                        case "time":
                            col.setHeader("Time");
                            break;
                        case "temperature_2m":
                            col.setHeader("Temperature");
                            break;
                        case "surface_pressure":
                            col.setHeader("Surface Pressure");
                            break;
                        case "wind_speed_10m":
                            col.setHeader("Wind Speed (10m)");
                            break;
                        case "rain":
                            col.setHeader("Rain");
                            break;
                    }

                }
        );
    }


}
