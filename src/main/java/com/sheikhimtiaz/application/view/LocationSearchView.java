package com.sheikhimtiaz.application.view;

import com.sheikhimtiaz.application.entity.Location;
import com.sheikhimtiaz.application.model.WeatherDaily;
import com.sheikhimtiaz.application.model.WeatherData;
import com.sheikhimtiaz.application.model.WeatherHourly;
import com.sheikhimtiaz.application.repository.LocationRepository;
import com.sheikhimtiaz.application.security.AuthenticatedUser;
import com.sheikhimtiaz.application.service.FavoriteLocationService;
import com.sheikhimtiaz.application.service.LocationService;
import com.sheikhimtiaz.application.service.WeatherService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;


@Route("search")
@PermitAll
public class LocationSearchView extends VerticalLayout {

    private TextField cityNameField;
    private TextField filterTextField;
    private Grid<Location> grid;
    private Button searchButton;
    private int currentPage = 0;
    private final int pageSize = 10;
    private VerticalLayout weatherLayout;
    private VerticalLayout hourlyWeatherLayout;
    LocationService locationService;
    WeatherService weatherService;
    private ComboBox<Location> favoriteLocationsComboBox;
    private Button addToFavoritesButton;
    FavoriteLocationService favoriteService;
    LocationRepository locationRepository;
    AuthenticatedUser authenticatedUser;

    public LocationSearchView(LocationService locationService,
                              WeatherService weatherService,
                              FavoriteLocationService favoriteService,
                              AuthenticatedUser authenticatedUser,
                              LocationRepository locationRepository) {
        this.locationService = locationService;
        this.weatherService = weatherService;
        this.favoriteService = favoriteService;
        this.locationRepository = locationRepository;
        this.authenticatedUser = authenticatedUser;

        // Header layout with logout button
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.END);
        Button logoutButton = new Button("Log Out", event -> {
//            VaadinSession.getCurrent().getSession().invalidate();
//            SecurityContextHolder.clearContext();
            authenticatedUser.logout();
            getUI().ifPresent(ui -> ui.navigate("login"));
        });
        headerLayout.add(logoutButton);
        add(headerLayout);

        cityNameField = new TextField("City Name");
        filterTextField = new TextField("Filter by Location Name");
        searchButton = new Button("Search", e -> searchLocations());

        grid = new Grid<>(Location.class);
        grid.setColumns("name", "latitude", "longitude");
        grid.asSingleSelect().addValueChangeListener(event -> {
            Location selectedLocation = event.getValue();
            if (selectedLocation != null) {
                showWeather(selectedLocation);
            }
        });

        favoriteLocationsComboBox = new ComboBox<>("Favorite Locations");
        favoriteLocationsComboBox.setItemLabelGenerator(Location::getName);
        favoriteLocationsComboBox.addValueChangeListener(event -> {
            Location selectedLocation = event.getValue();
            if (selectedLocation != null) {
                showWeather(selectedLocation);
            }
        });
        addToFavoritesButton = new Button("Add to Favorites", e -> {
            Location selectedLocation = grid.asSingleSelect().getValue();
            if (selectedLocation != null) {
                String userId = getCurrentUserId();
                Optional<Location> existingLocation = locationRepository.findByLatitudeAndLongitude(
                        selectedLocation.getLatitude(), selectedLocation.getLongitude());
                if(existingLocation.isEmpty()){
                    selectedLocation = locationRepository.save(selectedLocation);
                }
                favoriteService.addFavorite(userId, selectedLocation);
                updateFavoriteLocations();
            }
        });

        Button nextPageButton = new Button("Next Page", e -> loadNextPage());
        Button previousPageButton = new Button("Previous Page", e -> loadPreviousPage());

        weatherLayout = new VerticalLayout();
        hourlyWeatherLayout = new VerticalLayout();

        HorizontalLayout searchLayout = new HorizontalLayout(cityNameField, searchButton, favoriteLocationsComboBox, addToFavoritesButton);
        searchLayout.setAlignItems(Alignment.END);
        HorizontalLayout hzForPagingButtons = new HorizontalLayout(previousPageButton, nextPageButton);
        add(searchLayout, grid, hzForPagingButtons, weatherLayout, hourlyWeatherLayout);
        updateFavoriteLocations();
    }
    private void updateFavoriteLocations() {
        String userId = getCurrentUserId();
        List<Location> favoriteLocations = favoriteService.getFavorites(userId);
        favoriteLocationsComboBox.setItems(favoriteLocations);
    }
    private String getCurrentUserId() {
        try {
            Optional<String> username = authenticatedUser.getUsername();
            Optional<Long> userId = authenticatedUser.getUserId();
            return username.orElse("UNKNOWN");
        } catch (Exception e) {
            return "anonymous";
        }
    }
    private void showWeather(Location location) {
        try {
            WeatherData weatherData = weatherService.getWeather(location.getLatitude(), location.getLongitude());
            weatherLayout.removeAll();
            weatherLayout.add(new H2("Weather Forecast for " + location.getName()));

            Grid<WeatherDaily> dailyGrid = new Grid<>(WeatherDaily.class);
            dailyGrid.setItems(weatherData.getDailyWeatherData());
//            dailyGrid.addColumn(WeatherDaily::getDate).setHeader("Date");
//            dailyGrid.addColumn(WeatherDaily::getTemperatureMax).setHeader("Max Temperature");
//            dailyGrid.addColumn(WeatherDaily::getTemperatureMin).setHeader("Min Temperature");
            dailyGrid.asSingleSelect().addValueChangeListener(event -> {
                WeatherDaily selectedDay = event.getValue();
                if (selectedDay != null) {
                    showHourlyWeather(location, selectedDay.getDate());
                }
            });

            weatherLayout.add(dailyGrid);
            weatherLayout.add(showWeatherCharts(location, weatherData));
        } catch (Exception e) {
            Notification.show("Failed to fetch weather data: " + e.getMessage());
        }
    }

    private Component showWeatherCharts(Location location, WeatherData weatherData) {
        Select<String> year = new Select<>();
        year.setItems(location.getName());
        year.setValue(location.getName());
        year.setWidth("100px");

        HorizontalLayout header = createHeader("Charts for weather data", "Next 7 days (location)");
        header.add(year);

        Chart chart = new Chart(ChartType.AREA);
        Configuration conf = chart.getConfiguration();

        XAxis xAxis = new XAxis();
        List<String> dates = weatherData.getDailyWeatherData().stream()
                .map(WeatherDaily::getDate)
                .toList();
        xAxis.setCategories(dates.toArray(new String[0]));
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Values");

        PlotOptionsArea plotOptions = new PlotOptionsArea();
        plotOptions.setPointPlacement(PointPlacement.ON);
        conf.addPlotOptions(plotOptions);

        conf.addSeries(new ListSeries(location.getName(), weatherData.getDailyTemperatureMax().toArray(new Double[0])));

        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName("p-l");
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
    }

    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames("text-xl", "m-0");

        Span span = new Span(subtitle);
        span.addClassNames("text-secondary", "text-xs");

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private void showHourlyWeather(Location location, String date) {
        try {
            hourlyWeatherLayout.removeAll();
            WeatherData weatherData = weatherService.getWeather(location.getLatitude(), location.getLongitude());
            List<WeatherHourly> hourlyWeatherForDate = weatherData.getHourlyWeatherDataForDate(date);

            Grid<WeatherHourly> hourlyGrid = new Grid<>(WeatherHourly.class);
            hourlyGrid.setItems(hourlyWeatherForDate);
//            hourlyGrid.addColumn(WeatherHourly::getTime).setHeader("Time");
//            hourlyGrid.addColumn(WeatherHourly::getTemperature).setHeader("Temperature");
//            hourlyGrid.addColumn(WeatherHourly::getWindspeed).setHeader("Wind Speed");
//            hourlyGrid.addColumn(WeatherHourly::getPrecipitation).setHeader("Precipitation");

            hourlyWeatherLayout.add(hourlyGrid);
            hourlyWeatherLayout.add(showHourlyWeatherChart(location, hourlyWeatherForDate, date));
        } catch (Exception e) {
            Notification.show("Failed to fetch hourly weather data: " + e.getMessage());
        }
    }

    private Component showHourlyWeatherChart(Location location, List<WeatherHourly> hourlyWeather, String date) {
        HorizontalLayout header = createHeader("Hourly Weather Data - " + location.getName(), "For selected day - " + date);

        Chart chart = new Chart(ChartType.LINE);
        Configuration conf = chart.getConfiguration();

        XAxis xAxis = new XAxis();
        xAxis.setCategories(hourlyWeather.stream()
                .map(WeatherHourly::getTime)
                .map(time -> time.split("T")[1])
                .toArray(String[]::new));
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Values");

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.setPointPlacement(PointPlacement.ON);
        conf.addPlotOptions(plotOptions);

        conf.addSeries(new ListSeries("Temperature", hourlyWeather.stream().map(WeatherHourly::getTemperature).toArray(Double[]::new)));
        conf.addSeries(new ListSeries("Wind Speed", hourlyWeather.stream().map(WeatherHourly::getWindspeed).toArray(Double[]::new)));
        conf.addSeries(new ListSeries("Precipitation", hourlyWeather.stream().map(WeatherHourly::getPrecipitation).toArray(Double[]::new)));

        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName("p-l");
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
    }

    private void searchLocations() {
        currentPage = 0;
        fetchAndDisplayLocations();
    }

    private void loadNextPage() {
        currentPage++;
        fetchAndDisplayLocations();
    }

    private void loadPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            fetchAndDisplayLocations();
        }
    }

    private void fetchAndDisplayLocations() {
        String cityName = cityNameField.getValue();
        try {
            List<Location> locations = locationService.getLocations(cityName, currentPage, pageSize);
            grid.setItems(locations);
        } catch (Exception e) {
            Notification.show("Failed to fetch locations: " + e.getMessage());
        }
    }
}
