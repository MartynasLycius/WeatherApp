package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.weather.app.Utilities.Constraints;
import org.weather.app.Utilities.Utils;
import org.weather.app.models.HourlyForecast;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HourlyForecastComponent {
    private HorizontalLayout contentLayout;
    private String country;
    private String city;

    public HourlyForecastComponent(String country, String city) {
        this.country = country;
        this.city = city;
        contentLayout = new HorizontalLayoutBuilder()
                .setWidth("60%")
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .build();
    }

    public HorizontalLayout getContentLayout() {
        return contentLayout;
    }

    public void loadHourlyForecast(HourlyForecast hourlyForecast) {
        if (null != hourlyForecast) {
            VerticalLayout hourlyForecastStartLayout = generateStartLayout(hourlyForecast);
            VerticalLayout hourlyForecastEndLayout = generateEndLayout(hourlyForecast);
            VerticalLayout hourlyForecastCenterLayout = generateCenterLayout(hourlyForecast);
            contentLayout.add(hourlyForecastStartLayout, hourlyForecastCenterLayout, hourlyForecastEndLayout);
        }
    }

    private VerticalLayout generateStartLayout(HourlyForecast hourlyForecast) {
        VerticalLayout startLayout = new VerticalLayoutBuilder()
                .setAlignItems(FlexComponent.Alignment.START)
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setBackgroundColor("")
                .setWidth("auto")
                .build();

        Div currentLocationDiv = ComponentUtils.componentInDiv(new Text(city+", "+country), "default-text", "margin-left-20");
        Div lastUpdateDiv = ComponentUtils.componentInDiv(new Div(new Text(Utils.convertDate(hourlyForecast.getTime(), DateTimeFormatter.ofPattern("EEE, MMM d, h:mm a", Locale.ENGLISH)))),
                "current-location-text", "date-text-color");
        Div windDirectionDiv = new Div(new Text("Wind Direction: " + hourlyForecast.getWindDirection() + "°"));
        Div windSpeedDiv = new Div(new Text("Wind Speed: " + hourlyForecast.getWindSpeed() + "km/h"));
        Div dewPointDiv = new Div(new Text("Dew point: " + hourlyForecast.getDewPoint() + "%"));
        Div othersDive = ComponentUtils.componentsInDiv("other-info-text", windDirectionDiv, windSpeedDiv, dewPointDiv);
        startLayout.add(lastUpdateDiv, currentLocationDiv, othersDive);
        return startLayout;
    }

    private VerticalLayout generateCenterLayout(HourlyForecast hourlyForecast) {
        VerticalLayout centerLayout = new VerticalLayoutBuilder()
                .setWidth("50%")
                .setBackgroundColor("")
                .build();

        Div textContainer = ComponentUtils.componentsInDiv("temperature-text", new Text(hourlyForecast.getTemperature() + "°C"));

        Image temparatureImage = ComponentUtils.createImage(Constraints.getWeatherIconMap(Utils.getDayOrNight(hourlyForecast.getTime())).get(hourlyForecast.getWeather()), "current weather",
                "weather", "110px", "110px", null);

        String weatherCodeName = Constraints.getWeatherCodeMap().get(hourlyForecast.getWeather());
        String weatherType = "";
        String weatherIntensity = "";

        if (weatherCodeName.contains(":")) {
            String[] weatherCodeParts = weatherCodeName.split(":");
            weatherType = weatherCodeParts[0];
            weatherIntensity = weatherCodeParts[1];
        } else {
            weatherType = weatherCodeName;
        }

        Div cloudDiv = ComponentUtils.componentInDiv(new Text(weatherType), "default-text", "font-size-20", "font-weight-bold");
        Div intensityDiv = ComponentUtils.componentInDiv(new Text(weatherIntensity), "default-text", "font-size-20", "font-weight-bold");

        centerLayout.add(temparatureImage, cloudDiv, intensityDiv, textContainer);
        return centerLayout;
    }

    private VerticalLayout generateEndLayout(HourlyForecast hourlyForecast) {
        VerticalLayout endLayout = new VerticalLayoutBuilder()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setBackgroundColor("")
                .setWidth("auto")
                .setClassNames("padding-top", "10px")
                .build();
        Div rainDiv = ComponentUtils.componentsInDiv("default-text", new Text(hourlyForecast.getRain() + "mm"));

        String rainImageSrc = hourlyForecast.getRain();
        if (rainImageSrc.contains("AM")) {
            rainImageSrc = Constraints.rainForecastIconMap(Double.parseDouble(hourlyForecast.getRain()), "1");
        } else {
            rainImageSrc = Constraints.rainForecastIconMap(Double.parseDouble(hourlyForecast.getRain()), "0");
        }

        Image rainImage = ComponentUtils.createImage(rainImageSrc, "rain", "rain", "60px", "60px", null);

        HorizontalLayout rainLayout = new HorizontalLayoutBuilder()
                .setWidth("auto")
                .setBackgroundColor("")
                .build();
        rainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        rainLayout.add(rainImage, rainDiv);

        Image humidityImage = ComponentUtils.createImage("images/animated/humidity.svg", "humidity", "humidity", "60px", "60px", null);
        Div humidityDiv = ComponentUtils.componentsInDiv("default-text", new Text(hourlyForecast.getHumidity() + "%"));

        HorizontalLayout humidityLayout = new HorizontalLayoutBuilder()
                .setWidth("auto")
                .setBackgroundColor("")
                .build();
        humidityLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        humidityLayout.add(humidityImage, humidityDiv);


        Image snowFallImage = ComponentUtils.createImage("images/animated/snowy-3.svg", "humidity", "snow", "60px", "60px", null);
        Div snowFallDiv = ComponentUtils.componentsInDiv("default-text", new Text(hourlyForecast.getHumidity() + "%"));

        HorizontalLayout snowFallLayout = new HorizontalLayoutBuilder()
                .setWidth("auto")
                .setBackgroundColor("")
                .build();
        snowFallLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        snowFallLayout.add(snowFallImage, snowFallDiv);

        endLayout.add(humidityLayout, rainLayout, snowFallLayout);
        return endLayout;
    }

}