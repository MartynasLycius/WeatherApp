package com.example.application.views.weatherdetail;

import com.example.application.dto.DailyForecast;
import com.example.application.dto.ForecastResponse;
import com.example.application.dto.HourlyForecast;
import com.example.application.services.WeatherForecastService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@PermitAll
@PageTitle("Weather Forecasts")
@Route(value = "weather-detail/", layout = MainLayout.class)
public class WeatherDetail extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private WeatherForecastService weatherForecastService;
    private RestTemplate restTemplate;

    private H2 mainTitle = new H2();
    private H3 hourlyForecastTitle = new H3("Hourly Forecasts");
    private HorizontalLayout forecastHorizontalLayout = new HorizontalLayout();
    private Div dailyForecastsDiv = new Div();
    private Div hourlyForecastsDiv = new Div();
    private Div currentWeatherDiv = new Div();

    private ForecastResponse forecastResponse;

    @Autowired
    WeatherDetail(RestTemplate restTemplate, WeatherForecastService weatherForecastService) {
        this.restTemplate = restTemplate;
        this.weatherForecastService = weatherForecastService;

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
            this.forecastResponse = this.weatherForecastService.getWeatherForecastForLocation(latitude, longitude, tz);
            if (this.forecastResponse != null) {
                currentWeatherDiv.add(this.createCard(forecastResponse));
                addDailyForecasts();
            }
        }
    }

    private void addDailyForecasts() {
        List<DailyForecast> dailyForeCasts = this.weatherForecastService.getDailyForeCast(this.forecastResponse);

        dailyForecastsDiv.removeAll();
        for (DailyForecast dailyForecast : dailyForeCasts) {
            dailyForecastsDiv.add(dailyForecastsCard(
                    dailyForecast.getDate(),
                    dailyForecast.getMinTemperature(),
                    dailyForecast.getMinTemperature(),
                    dailyForecast.getRainSum(),
                    dailyForecast.getMaxWindSpeed()
                    )
            );
        }
    }

    private HorizontalLayout dailyForecastsCard(String date, Double tempMin, Double tempMax, Double rain, Double windSpeed) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassNames("card", "daily-forcast-item");
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
           this.showHourlyForecasts(date);
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

    private void showHourlyForecasts(String date) {
        List<HourlyForecast> hourlyForeCasts = this.weatherForecastService.getHourlyForeCast(this.forecastResponse, date);

        hourlyForecastsDiv.removeAll();
        this.hourlyForecastTitle.setText("Hourly Forecasts for " + date);

        for (HourlyForecast hourlyForecast : hourlyForeCasts) {
            hourlyForecastsDiv.add(
                    hourlyForecastsCard(
                            hourlyForecast.getHour(),
                            hourlyForecast.getTemperature(),
                            hourlyForecast.getRain(),
                            hourlyForecast.getWindSpeed()
                    )
            );
        }
    }

    private HorizontalLayout hourlyForecastsCard(String time, Double temp, Double rain, Double windSpeed) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassNames("card", "daily-forcast-item");
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
        Span date = new Span(forecastResponse.getCurrent_weather().getTemperature() + "°C");
        date.addClassName("date");
        header.add(temperature, date);

        Span windSpeed = new Span("Wind Speed: " + forecastResponse.getCurrent_weather().getWindspeed() + "km/h");
        windSpeed.addClassName("windSpeed");

        description.add(header, windSpeed);
        card.add(description);
        return card;
    }
}
