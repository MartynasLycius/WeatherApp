package com.nashid.weatherapp.views;

import com.nashid.weatherapp.core.notification.NotificationUtils;
import com.nashid.weatherapp.dto.HourlyWeatherResult;
import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.services.FavouriteLocationService;
import com.nashid.weatherapp.services.WeatherService;
import com.nashid.weatherapp.views.graph.WeatherHourlyGraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WeatherUpdateView extends VerticalLayout implements BeforeEnterObserver {
    private Image weatherCode;
    private Span dailyForecast;
    private Span currentTemperature;
    private Span currentWindSurface;
    private Span currentRain;
    private Span hourlyForecast;

    public WeatherUpdateView(Location location, WeatherService weatherService, FavouriteLocationService favouriteLocationService) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        addClassName("weather-container");

        // Daily Forecast
        VerticalLayout verticalLayout = new VerticalLayout();
        dailyForecast = new Span("Daily Forecast");
        dailyForecast.addClassName("daily-forcast-title");
        verticalLayout.add(dailyForecast);
        Hr hr = new Hr();
        hr.setClassName("custom-hr");
        verticalLayout.add(hr);


        VerticalLayout another = new VerticalLayout();
        weatherCode = new Image(getWeatherCodeAsIconUrl(location.getWeatherCode(), "50"), getWeatherCodeAsTitle(location.getWeatherCode()));
        another.add(weatherCode);
        currentTemperature = new Span(location.getTemperature());
        currentTemperature.addClassName("daily-forcast-temparature");
        another.add(currentTemperature);
        currentWindSurface = new Span("Wind Surface : " + location.getSurfaceWind());
        another.add(currentWindSurface);
        currentRain = new Span("Rain : " + location.getRain());
        another.add(currentRain);
        Long loggedUserId = Long.valueOf(VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID").toString());
        Button favouriteButton = new Button("Remove", event -> {
            if (favouriteLocationService.isLocationFavourite(loggedUserId, location.getLatitude(), location.getLongitude(), location.getLocation(), location.getCountry())) {
                favouriteLocationService.removeFavouriteLocation(loggedUserId, location.getLatitude(), location.getLongitude(), location.getLocation(), location.getCountry());
                NotificationUtils.showSuccessMessage("Location removed from favourite list");
                event.getSource().setText("Add");
            }
            else {
                favouriteLocationService.createFavouriteLocation(loggedUserId, location.getLatitude(), location.getLongitude(), location.getLocation(), location.getCountry());
                NotificationUtils.showSuccessMessage("Location added to favourite list");
                event.getSource().setText("Remove");
            }
        });
        favouriteButton.setIcon(VaadinIcon.HEART.create());
        if (!favouriteLocationService.isLocationFavourite(loggedUserId, location.getLatitude(), location.getLongitude(), location.getLocation(), location.getCountry())) {
            favouriteButton.setText("Add");
        }
        favouriteButton.addClassName("load-more-button");
        another.add(favouriteButton);
        another.setAlignItems(Alignment.CENTER);
        another.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.add(another);
        add(verticalLayout);

        //Hourly Forecast
        VerticalLayout verticalLayout1 = new VerticalLayout();
        hourlyForecast = new Span("Hourly Forecast");
        hourlyForecast.addClassName("daily-forcast-title");
        verticalLayout1.add(hourlyForecast);
        Hr hr2 = new Hr();
        hr2.setClassName("custom-hr");
        verticalLayout1.add(hr2);

        // 5-day forecast
        HourlyWeatherResult hourlyWeatherResult = weatherService.getHourlyWeather(location.getLatitude(), location.getLongitude());
        List<Map> hourlyForecast = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        for (int i=0; i<hourlyWeatherResult.getHourly().getTime().size(); i++) {
            Map hourly = new HashMap();
            Date d = new Date();
            d.setHours(i);
            d.setMinutes(0);
            hourly.put("time", simpleDateFormat.format(d));
            hourly.put("weatherTitle", getWeatherCodeAsTitle(hourlyWeatherResult.getHourly().getWeather_code().get(i)));
            hourly.put("weatherIconUrl", getWeatherCodeAsIconUrl(hourlyWeatherResult.getHourly().getWeather_code().get(i), "150"));
            hourly.put("temperature", hourlyWeatherResult.getHourly().getTemperature_2m().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getTemperature_2m()));
            hourly.put("surfaceWind", hourlyWeatherResult.getHourly().getWind_speed_10m().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getWind_speed_10m()));
            hourly.put("rain", hourlyWeatherResult.getHourly().getRain().get(i).toString().concat(" ").concat(hourlyWeatherResult.getHourly_units().getRain()));
            hourly.put("tempRaw", hourlyWeatherResult.getHourly().getTemperature_2m().get(i).toString());
            hourly.put("windRaw", hourlyWeatherResult.getHourly().getWind_speed_10m().get(i).toString());
            hourly.put("rainRaw", hourlyWeatherResult.getHourly().getRain().get(i).toString());
            hourlyForecast.add(hourly);
        }

        // Graph
        WeatherHourlyGraph weatherHourlyGraph = new WeatherHourlyGraph(hourlyForecast, weatherService);
        verticalLayout1.add(weatherHourlyGraph);

        VerticalLayout weekForecastLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        for (Map forecast : hourlyForecast) {
            VerticalLayout hourlyForecastLayout = new VerticalLayout();
            hourlyForecastLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

            Span span = new Span(forecast.get("temperature").toString());
            span.setClassName("temparature");
            span.setWidth("100px");

            H3 h3 = new H3(forecast.get("time").toString());
            h3.setWidth("100px");
            h3.setHeight("50px");

            Image image = new Image(forecast.get("weatherIconUrl").toString(), forecast.get("weatherTitle").toString());
            image.setWidth("100px");
            image.setHeight("80px");

            Paragraph paragraph = new Paragraph(forecast.get("weatherTitle").toString());
            paragraph.setWidth("100px");
            paragraph.setHeight("50px");

            Span span1 = new Span(forecast.get("surfaceWind").toString());
            span1.setWidth("100px");

            Span span2 = new Span(forecast.get("rain").toString());
            span2.setWidth("100px");

            hourlyForecastLayout.add(
                    h3,
                    image,
                    paragraph,
                    span,
                    span1,
                    span2
            );
            hourlyForecastLayout.setClassName("hourly-forecast");
            horizontalLayout.add(hourlyForecastLayout);
        }
        weekForecastLayout.add(horizontalLayout);
        weekForecastLayout.setClassName("week-forecast");
        verticalLayout1.add(weekForecastLayout);
        add(verticalLayout1);
    }

    public String getWeatherCodeAsIconUrl(Integer weatherCode, String size) {
        switch (weatherCode) {
            case 0:
                return "https://img.icons8.com/?size=" + size + "&id=50126&format=png&color=000000";
            case 1:
                return "https://img.icons8.com/?size=" + size + "&id=51462&format=png&color=000000";
            case 2:
                return "https://img.icons8.com/?size=" + size + "&id=51462&format=png&color=000000";
            case 3:
                return "https://img.icons8.com/?size=" + size + "&id=50126&format=png&color=000000";
            case 45:
                return "https://img.icons8.com/?size=" + size + "&id=51508&format=png&color=000000";
            case 48:
                return "https://img.icons8.com/?size=" + size + "&id=51508&format=png&color=000000";
            case 51:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 53:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 55:
                return "https://img.icons8.com/?size=" + size + "&id=51498&format=png&color=000000";
            case 56:
                return "https://img.icons8.com/?size=" + size + "&id=51512&format=png&color=000000";
            case 57:
                return "https://img.icons8.com/?size=" + size + "&id=51512&format=png&color=000000";
            case 61:
                return "https://img.icons8.com/?size=" + size + "&id=51451&format=png&color=000000";
            case 63:
                return "https://img.icons8.com/?size=" + size + "&id=51449&format=png&color=000000";
            case 65:
                return "https://img.icons8.com/?size=" + size + "&id=51510&format=png&color=000000";
            case 66:
                return "https://img.icons8.com/?size=" + size + "&id=51468&format=png&color=000000";
            case 67:
                return "https://img.icons8.com/?size=" + size + "&id=51468&format=png&color=000000";
            case 71:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 73:
                return "https://img.icons8.com/?size=" + size + "&id=51470&format=png&color=000000";
            case 75:
                return "https://img.icons8.com/?size=" + size + "&id=51470&format=png&color=000000";
            case 77:
                return "https://img.icons8.com/?size=" + size + "&id=49873&format=png&color=000000";
            case 80:
                return "https://img.icons8.com/?size=" + size + "&id=51455&format=png&color=000000";
            case 81:
                return "https://img.icons8.com/?size=" + size + "&id=51455&format=png&color=000000";
            case 82:
                return "https://img.icons8.com/?size=" + size + "&id=51501&format=png&color=000000";
            case 85:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 86:
                return "https://img.icons8.com/?size=" + size + "&id=51469&format=png&color=000000";
            case 95:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
            case 96:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
            case 99:
                return "https://img.icons8.com/?size=" + size + "&id=51499&format=png&color=000000";
        }
        return "Unknown";
    }

    public String getWeatherCodeAsTitle(Integer weatherCode) {
        switch (weatherCode) {
            case 0:
                return "Clear Sky";
            case 1:
                return "Mainly Clear";
            case 2:
                return "Partly Cloudy";
            case 3:
                return "Overcast";
            case 45:
                return "Fog";
            case 48:
                return "Depositing Rime Fog";
            case 51:
                return "Drizzle: Light";
            case 53:
                return "Drizzle: Moderate";
            case 55:
                return "Drizzle: Dense Intensity";
            case 56:
                return "Freezing Drizzle: Light";
            case 57:
                return "Freezing Drizzle: Dense Intensity";
            case 61:
                return "Rain: Sligh";
            case 63:
                return "Rain: Moderate ";
            case 65:
                return "Rain: Heavy Intensity";
            case 66:
                return "Freezing Rain: Light";
            case 67:
                return "Freezing Rain: Heavy Intensity";
            case 71:
                return "Snow Fall: Slight";
            case 73:
                return "Snow Fall: Moderate";
            case 75:
                return "Snow Fall: Heavy Intensity";
            case 77:
                return "Snow Grains";
            case 80:
                return "Rain Showers: Slight";
            case 81:
                return "Rain Showers: Moderate";
            case 82:
                return "Rain Showers: Violent";
            case 85:
                return "Snow Showers Slight";
            case 86:
                return "Snow Showers Heavy";
            case 95:
                return "Thunderstorm: Slight";
            case 96:
                return "Thunderstorm with Slight Hail";
            case 99:
                return "Thunderstorm with Heavy Hail";
        }
        return "Unknown";
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
            beforeEnterEvent.forwardTo("login");
        }
    }
}
