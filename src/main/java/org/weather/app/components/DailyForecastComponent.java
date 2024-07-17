package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import org.weather.app.Utilities.Constraints;
import org.weather.app.models.DailyForecast;
import org.weather.app.views.HourlyForecastView;

import java.util.List;

public class DailyForecastComponent {

    private VerticalLayout dailyForecastLayout;
    private String country;
    private String city;
    public DailyForecastComponent() {
        dailyForecastLayout = new VerticalLayoutBuilder()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setAlignItems(FlexComponent.Alignment.START)
                .build();

    }

    public VerticalLayout getDailyForecastLayout() {
        return dailyForecastLayout;
    }

    public void fillDailyForecast(String country, String city, String latitude, String longitude, String dayNight, List<DailyForecast> dailyForecastList) {
        this.country = country;
        this.city = city;
        Div dailyForeCastTitleDiv = ComponentUtils.componentsInDiv("current-location-text", new Text("Daily Forecast"));

        HorizontalLayout listLayout = new HorizontalLayoutBuilder()
                .setWidth("100%")
                .setBackgroundColor("")
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .build();

        if (!dailyForecastList.isEmpty()) {
            for (DailyForecast dailyForecast : dailyForecastList) {
                VerticalLayout hourlyRainForecastLayout = createRawDailyForecastLayout();
                String dayName = dailyForecast.getDayName();
                if (dailyForecastList.indexOf(dayName) == 0) {
                    dayName = "Today";
                }
                Div dayDiv = ComponentUtils.componentInDiv(new Text(dayName), "default-text", "color-orange");
                hourlyRainForecastLayout.add(dayDiv);

                hourlyRainForecastLayout.add(ComponentUtils.createImage(Constraints.getWeatherIconMap(dayNight).get(dailyForecast.getWeatherCode()),
                        "rain",
                        "hourly rain",
                        "100px",
                        "100px",
                        null));

                String weatherCodeName = Constraints.getWeatherCodeMap().get(dailyForecast.getWeatherCode());
                String weatherType = "";
                String weatherIntensity = "";

                if (weatherCodeName.contains(":")) {
                    String[] weatherCodeParts = weatherCodeName.split(":");
                    weatherType = weatherCodeParts[0];
                    weatherIntensity = weatherCodeParts[1];
                } else {
                    weatherType = weatherCodeName;
                    weatherIntensity = ":";
                }

                Div weatherTypeDiv = ComponentUtils.componentsInDiv("default-text", new Text(weatherType));
                Div weatherIntensityDiv = ComponentUtils.componentsInDiv("default-text", new Text(weatherIntensity));
                Div dailyTempDiv = ComponentUtils.componentsInDiv("default-text", new Text(dailyForecast.getMinTemperature() + " | " + dailyForecast.getMaxTemperature()));

                hourlyRainForecastLayout.add(weatherTypeDiv, weatherIntensityDiv, dailyTempDiv);

                hourlyRainForecastLayout.addClickListener(event -> handleClickEvent(latitude, longitude, dailyForecast.getDate().toString()));
                listLayout.add(hourlyRainForecastLayout);
            }
        }
        dailyForecastLayout.add(dailyForeCastTitleDiv, listLayout);
    }

    private VerticalLayout createRawDailyForecastLayout() {
        return new VerticalLayoutBuilder()
                .setWidth("auto")
                .setPadding(true)
                .setClassNames("hourly-rain-forecast")
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setBackgroundColor("")
                .build();
    }

    private void handleClickEvent(String latitude, String longitude, String date){
        {

            RouteParameters parameters = new RouteParameters(
                    new RouteParam("country", country),
                    new RouteParam("city", city),
                    new RouteParam("latitude", String.valueOf(latitude)),
                    new RouteParam("longitude", String.valueOf(longitude)),
                    new RouteParam("date", date)
            );

            UI.getCurrent().navigate(HourlyForecastView.class, parameters);

        }
    }

}
