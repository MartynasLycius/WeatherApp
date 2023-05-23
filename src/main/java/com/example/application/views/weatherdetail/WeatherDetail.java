package com.example.application.views.weatherdetail;

import com.example.application.dto.ForecastResponse;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@PermitAll
@PageTitle("Weather Forecasts")
@Route(value = "weather-detail/:cityName?/", layout = MainLayout.class)
public class WeatherDetail extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {

    private H2 mainTitle = new H2();
    private H3 hourlyForecastTitle = new H3("Hourly Forecasts");
    private HorizontalLayout forecastHorizontalLayout = new HorizontalLayout();
    private Div dailyForecastsDiv = new Div();
    private Div hourlyForecastsDiv = new Div();
    private Div currentWeatherDiv = new Div();

    RestTemplate restTemplate;

    @Autowired
    WeatherDetail(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        addClassName("card-list-view");
        setSizeFull();
        setMargin(true);

        add(mainTitle);
        add(currentWeatherDiv);

        VerticalLayout dailyForecastLayout = new VerticalLayout();
        dailyForecastLayout.setSpacing(false);
        dailyForecastLayout.setPadding(false);
        dailyForecastLayout.getThemeList().add("spacing-s");

        dailyForecastLayout.add(new H3("Daily Forecasts"));
        dailyForecastsDiv.getElement().setAttribute("style", "width: 100%");
        dailyForecastLayout.add(dailyForecastsDiv);

        Div dailyForecastContainer = new Div(dailyForecastLayout);
        dailyForecastContainer.getElement().setAttribute("style", "flex: 1");

        forecastHorizontalLayout.add(dailyForecastContainer);

        VerticalLayout hourlyForecastLayout = new VerticalLayout();
        hourlyForecastLayout.setSpacing(false);
        hourlyForecastLayout.setPadding(false);
        hourlyForecastLayout.getThemeList().add("spacing-s");

        hourlyForecastLayout.add(hourlyForecastTitle);
        hourlyForecastsDiv.getElement().setAttribute("style", "width: 100%");
        hourlyForecastLayout.add(hourlyForecastsDiv);

        Div hourlyForecastContainer = new Div(hourlyForecastLayout);
        hourlyForecastContainer.getElement().setAttribute("style", "flex: 1");

        forecastHorizontalLayout.add(hourlyForecastContainer);

        add(forecastHorizontalLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String cityName = event.getRouteParameters().get("cityName").
                orElse("Dhaka");
    }

    @Override
    public void onAttach(AttachEvent event) {
//        mainTitle.setText("Weather Forecasts For: " + this.cityName);
//
//        //RestTemplate restTemplate = new RestTemplate();
//        String url
//                = "https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = null;
//        try {
//            root = mapper.readTree(response.getBody());
//            //this.getCity(this.cityName);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        JsonNode name = root.path("current_weather");
//        add(this.createCard());
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();
        String cityName = null;
        if (parametersMap.containsKey("city")) {
            cityName = parametersMap.get("city").get(0);
        }
        String longitude = null;
        if (parametersMap.containsKey("lon")) {
            longitude = parametersMap.get("lon").get(0);
        }
        String latitude = null;
        if (parametersMap.containsKey("lat")) {
            latitude = parametersMap.get("lat").get(0);
        }
        String tz = null;
        if (parametersMap.containsKey("tz")) {
            tz = parametersMap.get("tz").get(0);
        }

        mainTitle.setText("Weather Forecasts For: " + cityName);

        if (latitude != null && longitude != null && tz != null) {
            String url
                    = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=" + latitude + "&longitude=" + longitude + "&" +
                    "hourly=temperature_2m,rain,windspeed_10m&" +
                    "daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&" +
                    "current_weather=true&timezone=" + tz + "";
            ResponseEntity<ForecastResponse> response = restTemplate.getForEntity(url, ForecastResponse.class);

            ForecastResponse forecastResponse = response.getBody();
            currentWeatherDiv.add(this.createCard(forecastResponse));
            addDailyForecasts(forecastResponse);
        }
    }

    private void addDailyForecasts(ForecastResponse forecastResponse) {
        dailyForecastsDiv.removeAll();
        ArrayList<String> time = forecastResponse.daily.time;
        ArrayList<Double> temperature_2m_min = forecastResponse.daily.temperature_2m_min;
        ArrayList<Double> temperature_2m_max = forecastResponse.daily.temperature_2m_max;
        ArrayList<Double> rain_sum = forecastResponse.daily.rain_sum;
        ArrayList<Double> windspeed_10m_max = forecastResponse.daily.windspeed_10m_max;

        for (int i = 0; i < time.size(); i++) {
            dailyForecastsDiv.add(dailyForecastsCard(
                    forecastResponse,
                    time.get(i),
                    temperature_2m_min.get(i),
                    temperature_2m_max.get(i),
                    rain_sum.get(i),
                    windspeed_10m_max.get(i)
                    )
            );
        }
    }

    private HorizontalLayout dailyForecastsCard(ForecastResponse forecastResponse, String date, Double tempMin, Double tempMax, Double rain, Double windSpeed) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        //date
        HorizontalLayout dateLayout = new HorizontalLayout();
        dateLayout.setSpacing(false);
        dateLayout.getThemeList().add("spacing-s");

        dateLayout.add(new Span("Date"), new Span(date));

        //min temp
        HorizontalLayout minTempLayout = new HorizontalLayout();
        minTempLayout.setSpacing(false);
        minTempLayout.getThemeList().add("spacing-s");

        minTempLayout.add(new Span("Minimum Temperature"), new Span(tempMin + "°C"));

        //max temp
        HorizontalLayout maxTempLayout = new HorizontalLayout();
        maxTempLayout.setSpacing(false);
        maxTempLayout.getThemeList().add("spacing-s");

        maxTempLayout.add(new Span("Maximum Temperature"), new Span(tempMax + "°C"));

        //rain
        HorizontalLayout rainLayout = new HorizontalLayout();
        rainLayout.setSpacing(false);
        rainLayout.getThemeList().add("spacing-s");

        rainLayout.add(new Span("Rain Sum"), new Span(rain + "mm"));

        //maxWindSpeed
        HorizontalLayout maxWindSpeed = new HorizontalLayout();
        maxWindSpeed.setSpacing(false);
        maxWindSpeed.getThemeList().add("spacing-s");

        maxWindSpeed.add(new Span("Maximum Wind Speed"), new Span(windSpeed + "km/h"));

        card.addClickListener(event -> {
           this.showHourlyForecasts(forecastResponse, date);
        });

        description.add(
                dateLayout,
                minTempLayout,
                maxTempLayout,
                rainLayout,
                maxWindSpeed);
        card.add(description);
        return card;
    }

    private void showHourlyForecasts(ForecastResponse forecastResponse, String date) {
        hourlyForecastsDiv.removeAll();

        ArrayList<String> hourlyTimes = forecastResponse.hourly.time;
        ArrayList<Double> temperature_2m = forecastResponse.hourly.temperature_2m;
        ArrayList<Double> windspeed_10m = forecastResponse.hourly.windspeed_10m;
        ArrayList<Double> rain = forecastResponse.hourly.rain;

        List<Integer> timeIndices = IntStream.range(0, hourlyTimes.size())
                .filter(i -> hourlyTimes.get(i).startsWith(date))
                .mapToObj(i -> i)
                .collect(Collectors.toList());

        this.hourlyForecastTitle.setText("Hourly Forecasts for " + date);
        for (Integer idx : timeIndices) {
            hourlyForecastsDiv.add(
                    hourlyForecastsCard(
                            date,
                            hourlyTimes.get(idx),
                            temperature_2m.get(idx),
                            rain.get(idx),
                            windspeed_10m.get(idx)
                    )
            );
        }
    }

    private HorizontalLayout hourlyForecastsCard(String date, String time, Double temp, Double rain, Double windSpeed) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        //date
        HorizontalLayout dateLayout = new HorizontalLayout();
        dateLayout.setSpacing(false);
        dateLayout.getThemeList().add("spacing-s");

        dateLayout.add(new Span("Hour"), new Span(time.split("T")[1]));

        //temp
        HorizontalLayout minTempLayout = new HorizontalLayout();
        minTempLayout.setSpacing(false);
        minTempLayout.getThemeList().add("spacing-s");

        minTempLayout.add(new Span("Temperature"), new Span(temp + "°C"));

        //rain
        HorizontalLayout rainLayout = new HorizontalLayout();
        rainLayout.setSpacing(false);
        rainLayout.getThemeList().add("spacing-s");

        rainLayout.add(new Span("Rain"), new Span(rain + "mm"));

        //maxWindSpeed
        HorizontalLayout maxWindSpeed = new HorizontalLayout();
        maxWindSpeed.setSpacing(false);
        maxWindSpeed.getThemeList().add("spacing-s");

        maxWindSpeed.add(new Span("Wind Speed"), new Span(windSpeed + "km/h"));

        description.add(
                dateLayout,
                minTempLayout,
                rainLayout,
                maxWindSpeed);
        card.add(description);
        return card;
    }


    private HorizontalLayout createCard(ForecastResponse forecastResponse) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span temperature = new Span("Temperature");
        temperature.addClassName("temperature");
        Span date = new Span(forecastResponse.current_weather.temperature + "°C");
        date.addClassName("date");
        header.add(temperature, date);

        Span windSpeed = new Span("Wind Speed: " + forecastResponse.current_weather.windspeed + "km/h");
        windSpeed.addClassName("windSpeed");

        description.add(header, windSpeed);
        card.add(description);
        return card;
    }
}
