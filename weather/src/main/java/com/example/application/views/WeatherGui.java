package com.example.application.views;

import com.example.application.data.model.WeatherApi;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.client.RestTemplate;


@Route(value = "", layout = MainLayout.class)
@PermitAll
public class WeatherGui extends VerticalLayout {
    private String city;
    private String units = "metric";
    private final String apiKey = "9f1c9d5001a81c74d30f19a75218c891";

    public WeatherGui() {
        TextField textFieldSetCity = new TextField("Enter a city");
        Button buttonCheckWeather = new Button("Check weather", VaadinIcon.SEARCH.create());
        Text text = new Text("After Checking Please Reload the tab ");

        buttonCheckWeather.addClickListener(e -> {
            String city = textFieldSetCity.getValue();
            if (!city.isEmpty()) {
                WeatherApi weatherApi = connectToApi(city);
                if (weatherApi != null) {
                    updateWeatherInfo(weatherApi);
                } else {
                    // Handle error case when unable to fetch weather data
                    // For example, display an error message to the user
                }
            }
        });

        add(
                textFieldSetCity,
                buttonCheckWeather,
                text
        );
    }

    private void updateWeatherInfo(WeatherApi weatherApi) {
        //show data on normal text
        removeAll();

        // Example weather info texts
        String cityAndCountry = weatherApi.getName() + ", " + weatherApi.getSys().getCountry();
        String temperature = "Temperature: " + weatherApi.getMain().getTemp().intValue() + "°C";
        String pressure = "Pressure: " + weatherApi.getMain().getPressure() + " hPa";
        String windSpeed = "Wind Speed: " + weatherApi.getWind().getSpeed() + " m/s";
        String windDegrees = "Wind Degrees: " + weatherApi.getWind().getDeg() + "°";
        String locationLongitude = "Longitude: " + weatherApi.getCoord().getLon();
        String locationLatitude = "Latitude: " + weatherApi.getCoord().getLat();

        // Create texts for weather info
        Text textCityAndCountry = new Text(cityAndCountry);
        Text textTemperature = new Text(temperature);
        Text textPressure = new Text(pressure);
        Text textWindSpeed = new Text(windSpeed);
        Text textWindDegrees = new Text(windDegrees);
        Text textLocationLongitude = new Text(locationLongitude);
        Text textLocationLatitude = new Text(locationLatitude);

        // Add texts to the layout
        add(
                textCityAndCountry,
                textTemperature,
                textPressure,
                textWindSpeed,
                textWindDegrees,
                textLocationLongitude,
                textLocationLatitude
        );
    }


    private WeatherApi connectToApi(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, WeatherApi.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}