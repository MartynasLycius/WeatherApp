package org.vaadin.example;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.vaadin.example.service.LocationService;
import org.vaadin.example.service.UserService;
import org.vaadin.example.util.Tools;
import org.vaadin.example.view.FavoriteLocationsView;
import org.vaadin.example.view.LoginView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Route("/WeatherApp")
@PageTitle("WeatherApp")
@CdiComponent
public class MainView extends VerticalLayout {
    @Inject
    private UserService userService;
    @Inject
    private LocationService locationService;

    private HorizontalLayout buttonLayout;
    private TextField cityNameField;
    private TextField filterField;
    private Grid<JsonObject> grid;
    private Button searchButton;
    private Button resetButton;
    private Button firstButton;
    private Button nextButton;
    private Button previousButton;
    private Button lastButton;
    private Span paginationLabel;
    private FavoriteLocationsView favLocView;

    private List<JsonObject> allLocations = new ArrayList<>();
    private List<JsonObject> filteredLocations = new ArrayList<>();

    private int currentPage = 0;
    private static final int PAGE_SIZE = 10;

    private static final Logger LOGGER = Logger.getLogger(MainView.class.getName());

    @PostConstruct
    public void init() {
        initAllComponent();

        favLocView = new FavoriteLocationsView(locationService, userService);
        add(favLocView);

        //start - search panel
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setWidthFull();
        searchLayout.add(cityNameField, searchButton, resetButton);
        searchLayout.setAlignItems(Alignment.BASELINE);

        Span spacer = new Span();
        spacer.setWidth("100%");
        searchLayout.add(spacer);
        searchLayout.add(filterField);
        searchLayout.addClassName("padding-margin");
        //end - search panel

        HorizontalLayout paginationLayout = new HorizontalLayout(firstButton, previousButton, paginationLabel, nextButton, lastButton);
        paginationLayout.setWidthFull();
        paginationLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        paginationLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //start - main grid pagination
        HorizontalLayout gridLayout = new HorizontalLayout(grid);
        gridLayout.setWidthFull();
        gridLayout.addClassNames("padding-margin", "no-top-margin");
        //end - main grid pagination

        //add all panels to main layout
        add(searchLayout, gridLayout, paginationLayout);
    }

    private void initAllComponent(){
        configureHeader();
        configureCityNameField();
        configureFilterField();
        configureResetButton();
        configurePagination();

        configureSearchButton();
        configureMainGrid();
    }

    private void configureHeader() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        H3 title = new H3("Weather App");
        title.getStyle().set("margin", "0");

        Button loginButton = new Button("Login", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout header = new HorizontalLayout(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setSpacing(true);
        header.getStyle().set("padding", "var(--lumo-space-m)")
                .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)");

        HorizontalLayout menu;
        if (userService.hasSession()) {
            Span userLabel = new Span("Welcome, " + userService.getLoggedUserName());
            Button logoutButton = new Button("Logout", e -> {
                VaadinSession.getCurrent().close();
                VaadinSession.getCurrent().getSession().invalidate();
                UI.getCurrent().navigate(LoginView.class);
            });
            logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            menu = new HorizontalLayout(userLabel, logoutButton);
        } else {
            menu = new HorizontalLayout(loginButton);
        }
        menu.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        menu.setSpacing(true);

        HorizontalLayout headerLayout = new HorizontalLayout(header, menu);
        headerLayout.setWidth("100%");
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(headerLayout);
    }

    private void configureMainGrid() {
        grid = new Grid<>(JsonObject.class, false);
        if(userService.hasSession()){
            grid.setMinHeight("420px");
        }else{
            grid.setMinHeight("500px");
        }
        grid.setWidthFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        Grid.Column<JsonObject> nameColumn = grid.addColumn(location -> location.getString("name")).setHeader("Name");
        Grid.Column<JsonObject> admin1Column = grid.addColumn(location -> location.containsKey("admin1") ? location.getString("admin1") : "").setHeader("Admin1");
        Grid.Column<JsonObject> admin2Column = grid.addColumn(location -> location.containsKey("admin2") ? location.getString("admin2") : "").setHeader("Admin2");
        Grid.Column<JsonObject> admin3Column = grid.addColumn(location -> location.containsKey("admin3") ? location.getString("admin3") : "").setHeader("Admin3");
        Grid.Column<JsonObject> latitudeColumn = grid.addColumn(location -> location.getJsonNumber("latitude").toString()).setHeader("Latitude");
        Grid.Column<JsonObject> longitudeColumn = grid.addColumn(location -> location.getJsonNumber("longitude").toString()).setHeader("Longitude");

        HeaderRow headerRow = grid.prependHeaderRow();
        HeaderRow.HeaderCell locationHeader = headerRow.join(nameColumn, admin1Column, admin2Column, admin3Column);
        locationHeader.setText("Location");

        HeaderRow.HeaderCell coordinatesHeader = headerRow.join(latitudeColumn, longitudeColumn);
        coordinatesHeader.setText("Coordinates");

        //Weather forecast only applicable for logged-in users
        if(userService.hasSession()){

            // Add "Favorite" button column with star icon
            Grid.Column<JsonObject> favoriteColumn = grid.addComponentColumn(item -> {
                Button favoriteButton = new Button(new Icon(VaadinIcon.STAR_O));
                favoriteButton.addClassName("cursor-pointer");

                favoriteButton.addClickListener(event -> {
                    // Handle favorite button click
                    JsonObject location = item;
                    Map result = locationService.saveLocationToFavorites(location);
                    boolean isSuccess = Boolean.parseBoolean(result.get("isSuccess").toString());
                    if (isSuccess) {
                        Notification.show(result.get("message").toString(), 3000, Notification.Position.MIDDLE);
                        // Recreate and populate the bordered container
                        favLocView.updateFavoriteLocations();
                    } else {
                        Notification.show(result.get("message").toString(), 3000, Notification.Position.MIDDLE);
                    }

                });
                return favoriteButton;
            }).setHeader("Add to Favorite");

            // Add item click listener to the grid
            grid.addItemClickListener(event -> {
                // Check if the clicked component is the favorite button
                if (event.getColumn().equals(favoriteColumn)) {
                    return;
                }

                // If not a favorite button click, proceed with fetching weather forecast
                JsonObject location = event.getItem();
                onClickMainGridData(location);
            });
        }
        grid.addClassName("padding-margin");

    }

    private void onClickMainGridData(JsonObject location){
        String locName = locationService.getLocationString(location);
        double latitude = location.getJsonNumber("latitude").doubleValue();
        double longitude = location.getJsonNumber("longitude").doubleValue();
        List<JsonObject> dailyData = locationService.getWeatherForecastApiData(Tools.WEATHER_FORECAST_TYPE_DAILY, latitude, longitude, "");
        locationService.displayDailyForecast(dailyData, latitude, longitude, locName);
    }

    private void configureSearchButton() {
        searchButton = new Button("Search", e -> {
            filterField.setValue("");
            if (cityNameField.isEmpty()) {
                Notification.show("City name is required", 3000, Notification.Position.MIDDLE);
            } else {
                allLocations = locationService.getLocationsByCityName(cityNameField.getValue(), 100);
                filteredLocations = new ArrayList<>(allLocations);
                if (allLocations.isEmpty()) {
                    Notification.show("No data found for the provided city name", 3000, Notification.Position.MIDDLE);
                } else {
                    currentPage = 0;
                    updateMainGrid();
                }
            }
        });
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClassName("cursor-pointer");
    }

    private void configureCityNameField() {
        cityNameField = new TextField("Search Location by City Name");
        cityNameField.addThemeName("bordered");
        cityNameField.setWidth("70%");
        cityNameField.setRequired(true);
        cityNameField.setClearButtonVisible(true);
    }

    private void configureFilterField() {
        filterField = new TextField("Filter by Location");
        filterField.setWidth("70%");
        filterField.setClearButtonVisible(true);
        filterField.addValueChangeListener(event -> {
            currentPage = 0;
            updateMainGrid();
        });
    }

    private void configureResetButton() {
        resetButton = new Button("Reset");
        resetButton.addClassName("cursor-pointer");
        resetButton.addClickListener(event -> {
            grid.setItems(Collections.emptyList());
            allLocations = new ArrayList<>();
            filteredLocations = new ArrayList<>();
            cityNameField.setValue("");
            filterField.setValue("");
            currentPage = 0;
            updateMainGrid();
        });
    }

    private void configurePagination() {
        paginationLabel = new Span();

        firstButton = new Button("First", e -> {
            if (currentPage > 0) {
                currentPage = 0;
                updateMainGrid();
            }
        });

        previousButton = new Button("Previous", e -> {
            if (currentPage > 0) {
                currentPage--;
                updateMainGrid();
            }
        });

        nextButton = new Button("Next", e -> {
            if ((currentPage + 1) * PAGE_SIZE < filteredLocations.size()) {
                currentPage++;
                updateMainGrid();
            }
        });

        lastButton = new Button("Last", e -> {
            int lastPage = (filteredLocations.size() - 1) / PAGE_SIZE;
            if (currentPage < lastPage) {
                currentPage = lastPage;
                updateMainGrid();
            }
        });

        updatePaginationLabel(filteredLocations.size());
    }

    private void updatePaginationLabel(int totalItems) {
        int startItem = totalItems == 0 ? 0 : currentPage * PAGE_SIZE + 1;
        int endItem = Math.min(startItem + PAGE_SIZE - 1, totalItems);
        paginationLabel.setText(String.format("%d-%d of %d", startItem, endItem, totalItems));
    }

    private void updateMainGrid() {
        String filterText = filterField.getValue().trim().toLowerCase();
        filteredLocations.clear();

        for (JsonObject location : allLocations) {
            String name = location.getString("name").toLowerCase();
            String admin1 = location.containsKey("admin1") ? location.getString("admin1").toLowerCase() : "";
            String admin2 = location.containsKey("admin2") ? location.getString("admin2").toLowerCase() : "";
            String admin3 = location.containsKey("admin3") ? location.getString("admin3").toLowerCase() : "";

            if (name.contains(filterText)
                    || admin1.contains(filterText)
                    || admin2.contains(filterText)
                    || admin3.contains(filterText)) {
                filteredLocations.add(location);
            }
        }

        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, filteredLocations.size());

        if (start >= filteredLocations.size() && currentPage > 0) {
            currentPage = Math.max(0, (filteredLocations.size() - 1) / PAGE_SIZE);
            start = currentPage * PAGE_SIZE;
            end = Math.min(start + PAGE_SIZE, filteredLocations.size());
        }

        grid.setItems(filteredLocations.subList(start, end));
        updatePaginationLabel(filteredLocations.size());
    }

}
