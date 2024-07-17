package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.weather.app.Utilities.Constraints;
import org.weather.app.Utilities.Utils;
import org.weather.app.models.CurrentWeather;
import org.weather.app.views.CurrentForecast;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrentForecastComponent {

    private HorizontalLayout currentForecastLayout;
    private VerticalLayout currentForecastInfoLayout;
    private VerticalLayout currentForecastTemparatureLayout;
    private VerticalLayout currentForecastIconLayout;
    private VerticalLayout favoriteLayout;
    private String dayNight = "";
    private CurrentForecast currentForecast;
    private Image favoriteCityImage;
    private String country;
    private String city;

    public CurrentForecastComponent(CurrentForecast currentForecast) {
        this.currentForecast = currentForecast;
        currentForecastLayout = new HorizontalLayoutBuilder().build();

        currentForecastInfoLayout = new VerticalLayoutBuilder()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setAlignItems(FlexComponent.Alignment.START)
                .setBackgroundColor("")
                .build();

        currentForecastTemparatureLayout = new VerticalLayoutBuilder()
                .setBackgroundColor("")
                .build();

        currentForecastIconLayout = new VerticalLayoutBuilder()
                .setBackgroundColor("")
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .build();

        favoriteLayout = new VerticalLayoutBuilder()
                .setBackgroundColor("")
                .setWidth("10%")
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .build();
    }

    public HorizontalLayout getCurrentForecastLayout() {
        return currentForecastLayout;
    }

    public void fillCurrentForecast(String country, String city, CurrentWeather currentWeather) {
        dayNight = currentWeather.getIsDay();
        this.country = country;
        this.city = city;
        Div currentLocationDiv = ComponentUtils.componentsInDiv("current-location-text", new Text(city+", "+country));
        Div lastUpdateDiv = ComponentUtils.componentsInDiv("last-update-text", new Text("Updated on "+ Utils.convertDate(currentWeather.getLastUpdateTime(), DateTimeFormatter.ofPattern("EEE, MMM d, h:mm a", Locale.ENGLISH))));

        Div windDirectionDiv = ComponentUtils.componentsInDiv("", new Text("Precipitation: "+currentWeather.getPrecipitation()+" mm"));
        Div windSpeedDiv = ComponentUtils.componentsInDiv("", new Text("Wind Speed: "+currentWeather.getWindSpeed()+" km/h"));
        Div surfacePressureDiv = ComponentUtils.componentsInDiv("", new Text("Surface Pressure: "+currentWeather.getWindSpeed()+" hPa"));
        Div othersDive = ComponentUtils.componentsInDiv("other-info-text", windDirectionDiv, windSpeedDiv, surfacePressureDiv);

        currentForecastInfoLayout.add(currentLocationDiv, lastUpdateDiv, othersDive);


        Div temparatureDiv = ComponentUtils.componentsInDiv("temperature-text", new Text(currentWeather.getTemperature() + "°C"));
        Div feelTemperatureTitleDiv = ComponentUtils.componentsInDiv("default-text", new Text("Feel like"));
        Div temperatureFeelDiv = ComponentUtils.componentsInDiv("temperature-feel-text", new Text(currentWeather.getApparentTemperature() + "°C"));
        currentForecastTemparatureLayout.add(temparatureDiv, feelTemperatureTitleDiv, temperatureFeelDiv);


        Image currentWeatherImage = ComponentUtils.createImage(Constraints.getWeatherIconMap(dayNight).get(currentWeather.getWeatherCode()),
                "current weather",
                "current weather",
                "64px",
                "64px",
                null
                );

        Div cloudDiv = ComponentUtils.componentInDiv(new Text(Constraints.getWeatherCodeMap().get(currentWeather.getWeatherCode())), "default-text","font-size-20", "font-weight-bold");
        Div rainDiv = ComponentUtils.componentsInDiv("default-text", new Text("Rain: "+currentWeather.getRain()+" mm"));
        Div totalCloudDiv = ComponentUtils.componentsInDiv("default-text", new Text("Cloud Cover: "+currentWeather.getTotalCloudCover()+" °C"));
        Div humidityDiv = ComponentUtils.componentsInDiv("default-text", new Text("Humidity: "+currentWeather.getHumidity()+" %"));

        currentForecastIconLayout.add(currentWeatherImage, cloudDiv, rainDiv, humidityDiv, totalCloudDiv);

        favoriteCityImage = ComponentUtils.createImage(Constraints.FAVORITE_IMAGE_REMOVE,
                "favourte",
                "favorite",
                "32px",
                "32px",
                null);

        Div favoriteDiv = ComponentUtils.componentsInDiv("favorite-forecast", favoriteCityImage);
        // favoriteImage.setSrc("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAAB70lEQVR4nO2Yv0sdQRSFvydGicFCiUIKwTaJ8a9IoRHSJY19EGxEAum1sbaKgZSaQKzsFAUhahOIGn9AgggWARWxM+EJccLALR6LqzM7s+9dYT44zbLvzjlvZ3ZmLyQSiUQikfDmHjAIvAe2gWOgChwBX4EJoM+hTp/cuya/rUqtLak9IGNFpwK8Bn4BxkHLwONr6jwBVhxr/AReydhRuA98chy8VpfAWE2dcbnmW2dWPASH2CgweK3sFJoMrLEeEsY+0rlAAzH1peg0G1Zg3mRk16kXLcChAuMmowPx5sxLBaZNjoZ8gnxUYNjk6INPkB0Fhk2O7KbpzJkCwyZHpz5Bimxc9dKlT5BzBYZNjuxscWZPgWGTI7t+nfmswLDJkT1tODOqwLDJ0YhPkF7gSoFpk9E/oAdPlhUYNxktUoAXCoybjOyXYyHWFJg3olUCeKZkc6wCTwnknYIgb4lApeA3eyzNx2xAPAA2GxBiE2gjMt3AjzqG2AceURIdwLc6PYmHlEynvArLCrEif1hdaAamSwgxU1ar9DbsAe5PhAC2xhsajO3xfg8IsQv0o4RWYEpOp64BrmQqBfd1y+A58NshxIlvf6oRdAELN4RYKnN/iE1FFu9FTYC/cm5r4g7SL42MPU0LuijtokQikSCY/wtr6za2rYeWAAAAAElFTkSuQmCC");
        favoriteDiv.addClickListener(divClickEvent -> this.currentForecast.clickOnFavorite());
        favoriteLayout.add(favoriteDiv);

        currentForecastLayout.add(currentForecastInfoLayout, currentForecastTemparatureLayout, currentForecastIconLayout, favoriteLayout);

    }

    public void addToFavorite() {
        favoriteCityImage.setSrc(Constraints.FAVORITE_IMAGE_ADD);
    }
    public void removeFavorite() {
        favoriteCityImage.setSrc(Constraints.FAVORITE_IMAGE_REMOVE);

    }
}
