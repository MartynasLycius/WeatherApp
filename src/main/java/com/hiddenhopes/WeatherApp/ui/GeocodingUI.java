package com.hiddenhopes.WeatherApp.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiddenhopes.WeatherApp.Constant;
import com.hiddenhopes.WeatherApp.dto.*;
import com.hiddenhopes.WeatherApp.model.FavoriteLocation;
import com.hiddenhopes.WeatherApp.model.User;
import com.hiddenhopes.WeatherApp.service.LocationService;
import com.hiddenhopes.WeatherApp.service.UserService;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("")
@PageTitle("Geocoding")
@PermitAll
public class GeocodingUI extends VerticalLayout {

    private final LocationService geocodingService;
    private final UserService userService;
    private final Grid<Location> grid;
    private final H2 search;
    private final TextField searchField;
    private final Button favoriteListButton;
    private final Dialog dailyWeatherDialog;
    private final Dialog hourlyWeatherDialog;
    private final HtmlComponent dailyChart;
    private final Button logoutButton;

    @Autowired
    public GeocodingUI(LocationService geocodingService, UserService userService) {
        this.geocodingService = geocodingService;
        this.userService = userService;
        search = new H2("Search");
        searchField = new TextField();
        searchField.setPlaceholder("Enter location name");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> searchLocation(event.getValue()));
        favoriteListButton = new Button("Show my favorite Locations", e -> searchFavoriteLocationList());
        grid = new Grid<>(Location.class);
        grid.setColumns("name", "latitude", "longitude");
        grid.addComponentColumn(location -> {
            Icon icon = VaadinIcon.STAR.create();
            User user = userService.findByUsername(getUsername());
            if (userService.isLocationFavorite(user.getId(), location.getLatitude(), location.getLongitude())) {
                icon.setColor("orange");
            }
            icon.addClickListener(event -> {
                FavoriteLocation favoriteLocation = new FavoriteLocation(location.getLatitude(), location.getLongitude());
                favoriteLocation.setLocationName(location.getName());

                if (icon.getColor() != null && icon.getColor().equals("orange")) {
                    icon.setColor(null);
                    userService.removeFavoriteLocationByCoordinates(getUsername(), favoriteLocation);
                } else {
                    userService.addFavoriteLocationByCoordinates(getUsername(), favoriteLocation);
                    icon.setColor("orange");
                }
            });
            return icon;
        }).setHeader("Favorite").setKey("favorite").setWidth("100px").setFlexGrow(0);
        grid.setSizeFull();

        // Register a selection listener on the grid
        grid.addItemDoubleClickListener(event -> {
            if (event.getItem() != null) {
                Location selectedLocation = event.getItem();
                openLocationWeatherDetailsDailyPopup(selectedLocation);
            }
        });
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.add(new Text("Search"), searchField, favoriteListButton);
        dailyWeatherDialog = createDialog("90%", "90%");
        hourlyWeatherDialog = createDialog("90%", "90%");
        dailyChart = new HtmlComponent("canvas");
        dailyChart.setId("chartCanvas");
        dailyChart.setHeight("400px");
        logoutButton = new Button("Logout", e -> logout());

        add(searchLayout, grid, logoutButton);
        setWidth("100%");
        setHeight("100%");

        refreshGrid();
    }

    private void searchLocation(String name) {
        refreshGrid(name);
    }

    private void refreshGrid() {
        refreshGrid(null);
    }

    private void refreshGrid(String name) {
        List<Location> results = new ArrayList<>();
        if (StringUtils.isNoneBlank(name)) {
            results = geocodingService.searchLocations(name);
        }
        grid.setItems(results);
    }

    private void searchFavoriteLocationList() {
        User user = userService.findByUsername(getUsername());
        List<FavoriteLocation> favoriteLocations = userService.getFavoriteLocations(user.getId());
        List<Location> results = new ArrayList<>();
        favoriteLocations.forEach(i -> {
            Location location = new Location();
            location.setName(i.getLocationName());
            location.setLatitude(i.getLatitude());
            location.setLongitude(i.getLongitude());
            results.add(location);
        });
        grid.setItems(results);
    }

    private void openLocationWeatherDetailsDailyPopup(Location location) {
        WeatherForecast weatherForecast = makeApiCall(location.getLatitude(), location.getLongitude(), Constant.DAILY_PARAMS);
        WeeklyData weeklyData = weatherForecast.getDaily();
        Grid<DailyData> dailyWeatherGrid = new Grid<>(DailyData.class);
        dailyWeatherGrid.setColumns("time", "temperature2mMax", "temperature2mMin", "rainSum", "windspeed10mMax");
        List<DailyData> dailyDataList = createWeatherDailyDataList(weeklyData.getTime(), weeklyData.getTemperature2mMax(), weeklyData.getTemperature2mMin(), weeklyData.getRainSum(), weeklyData.getWindspeed10mMax());
        dailyWeatherGrid.setItems(dailyDataList);
        String chartScript = generateChart(dailyDataList);
        dailyWeatherGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                DailyData selectedDay = event.getValue();
                openLocationWeatherDetailsHourlyPopup(selectedDay.getTime(), location.getLatitude(), location.getLongitude());
                dailyWeatherDialog.close();
            }
        });

        Button closeButton = createCloseButton(dailyWeatherDialog);
        VerticalLayout dialogContent = new VerticalLayout(dailyWeatherGrid, closeButton, dailyChart);
        dailyWeatherDialog.removeAll();
        dailyWeatherDialog.getElement().executeJs(chartScript);
        dailyWeatherDialog.add(dialogContent);
        dailyWeatherDialog.open();
    }

    private void openLocationWeatherDetailsHourlyPopup(String date, double latitude, double longitude) {
        WeatherForecast weatherForecast = makeApiCall(latitude, longitude, Constant.HOURLY_PARAMS);
        HourlyData data = weatherForecast.getHourly();
        Grid<SingleHourData> hourlyWeatherGrid = new Grid<>(SingleHourData.class);
        List<SingleHourData> hourlyDataList = createWeatherHourlyDataList(data.getTime(), data.getTemperature_2m(), data.getRain(), data.getWindspeed_10m());
        List<SingleHourData> filteredList = hourlyDataList.stream().filter(o -> o.getTime().startsWith(date)).collect(Collectors.toList());
        filteredList.forEach(e -> e.setTime(e.getTime().substring(11)));

        hourlyWeatherGrid.setColumns("time", "temperature2m", "rain", "windspeed10m");
        hourlyWeatherGrid.setItems(filteredList);

        Button closeButton = createCloseButton(hourlyWeatherDialog);
        VerticalLayout dialogContent = new VerticalLayout(hourlyWeatherGrid, closeButton);
        hourlyWeatherDialog.removeAll();
        hourlyWeatherDialog.add(dialogContent);
        hourlyWeatherDialog.open();
    }

    private WeatherForecast makeApiCall(double latitude, double longitude, String params) {
        try {
            URL url = new URL(Constant.API_URL + "latitude=" + latitude + "&longitude=" + longitude + params);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.toString(), WeatherForecast.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<DailyData> createWeatherDailyDataList(List<String> time, List<Double> tempMax, List<Double> tempMin, List<Double> rainSum, List<Double> windSpeed) {
        List<DailyData> weatherDataList = new ArrayList<>();
        for (int i = 0; i < time.size(); i++) {
            DailyData data = new DailyData();
            data.setTime(time.get(i));
            data.setTemperature2mMax(tempMax.get(i));
            data.setTemperature2mMin(tempMin.get(i));
            data.setRainSum(rainSum.get(i));
            data.setWindspeed10mMax(windSpeed.get(i));
            weatherDataList.add(data);
        }
        return weatherDataList;
    }

    private List<SingleHourData> createWeatherHourlyDataList(List<String> time, List<Double> temperature2m,
                                                             List<Double> rain, List<Double> windspeed10m) {
        List<SingleHourData> hourDataList = new ArrayList<>();

        int itemCount = Math.min(time.size(), Math.min(temperature2m.size(), Math.min(rain.size(), windspeed10m.size())));

        for (int i = 0; i < itemCount; i++) {
            SingleHourData hourData = new SingleHourData();
            hourData.setTime(time.get(i));
            hourData.setTemperature2m(temperature2m.get(i));
            hourData.setRain(rain.get(i));
            hourData.setWindspeed10m(windspeed10m.get(i));
            hourDataList.add(hourData);
        }

        return hourDataList;
    }

    private Dialog createDialog(String width, String height) {
        Dialog dialog = new Dialog();
        dialog.setWidth(width);
        dialog.setHeight(height);
        return dialog;
    }

    private Button createCloseButton(Dialog dialog) {
        Button closeButton = new Button("Close");
        closeButton.getElement().getStyle().set("position", "absolute");
        closeButton.getElement().getStyle().set("top", "0");
        closeButton.getElement().getStyle().set("right", "0");
        closeButton.addClickListener(event -> dialog.close());
        return closeButton;
    }

    private String generateChart(List<DailyData> dailyDataList) {
        StringBuilder labelsBuilder = new StringBuilder();
        StringBuilder dataBuilder = new StringBuilder();

        // Iterate over the dailyDataList and build the labels and data strings
        for (DailyData dailyData : dailyDataList) {
            if (labelsBuilder.length() > 0) {
                labelsBuilder.append(",");
                dataBuilder.append(",");
            }
            labelsBuilder.append("'").append(dailyData.getTime()).append("'");
            dataBuilder.append(dailyData.getTemperature2mMax());
        }

        // Build the JavaScript code for creating the chart
        String chartScript = "var ctx = document.getElementById('chartCanvas').getContext('2d');\n" +
                "var chart = new Chart(ctx, {\n" +
                "    type: 'bar',\n" +
                "    data: {\n" +
                "        labels: [" + labelsBuilder + "],\n" +
                "        datasets: [{\n" +
                "            label: 'Temperature (Max)',\n" +
                "            data: [" + dataBuilder + "],\n" +
                "            backgroundColor: 'rgba(75, 192, 192, 0.2)',\n" +
                "            borderColor: 'rgba(75, 192, 192, 1)',\n" +
                "            borderWidth: 1\n" +
                "        }]\n" +
                "    },\n" +
                "    options: {\n" +
                "        scales: {\n" +
                "            yAxes: [{\n" +
                "                ticks: {\n" +
                "                    beginAtZero: true\n" +
                "                }\n" +
                "            }]\n" +
                "        }\n" +
                "    }\n" +
                "});";
        return chartScript;
    }

    private void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        }
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
