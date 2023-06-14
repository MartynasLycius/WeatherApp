package com.example.application.views.searchlocation;

import com.example.application.data.dto.LocationDto;
import com.example.application.service.UserFavLocationService;
import com.example.application.service.UserService;
import com.example.application.service.WeatherDataService;
import com.example.application.views.MainLayout;
import com.example.application.views.common.ViewNotificationUtils;
import com.example.application.views.forecast.DailyWeatherForecastView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import static com.example.application.views.favoritelocations.FavoriteLocationsView.createLocationDetailsRenderer;

@PageTitle("Search Location")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class SearchLocationView extends VerticalLayout {
    private final UserFavLocationService userFavLocationService;
    private final TextField searchLocationTextField;

    protected final WeatherDataService weatherDataService;
    protected final UserService userService;

    protected final Grid<LocationDto> grid;
    protected GridListDataView<LocationDto> dataView;
    protected DailyWeatherForecastView dailyWeatherForecastView = new DailyWeatherForecastView();

    protected final TextField gridFilterTextField;

    public SearchLocationView(UserFavLocationService userFavLocationService, WeatherDataService weatherDataService, UserService userService) {

        this.userFavLocationService = userFavLocationService;
        this.weatherDataService = weatherDataService;
        this.userService = userService;

        this.grid = new Grid<>(LocationDto.class, false);
        this.gridFilterTextField = new TextField();

        configureGrid();
        setSizeFull();

        dataView = grid.setItems();
        searchLocationTextField = new TextField();


        addClassNames("list-view");

        add(getLocationSearchBar(), getContent());

        dailyWeatherForecastView.setVisibility(false, null);
    }

    private Component getLocationSearchBar() {
        searchLocationTextField.setPlaceholder("Search by location name...");
        searchLocationTextField.setClearButtonVisible(true);
        searchLocationTextField.setValueChangeMode(ValueChangeMode.LAZY);

        var searchButton = new Button("Search");
        searchButton.addClickShortcut(Key.ENTER);
        searchButton.addClickListener(e -> updateLocations());

        var toolbar = new HorizontalLayout(searchLocationTextField, searchButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    protected void updateLocations() {
        if (searchLocationTextField.getValue().length() < 3) {
            return;
        }

        var locations = weatherDataService.getLocations(searchLocationTextField.getValue());
        dataView = grid.setItems(locations);

        dataView.addFilter(locationDto -> {
            String searchTerm = gridFilterTextField.getValue();
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return true;
            }

            return locationDto.getName().toLowerCase().contains(searchTerm.toLowerCase());
        });
    }


    protected void configureGridColumns() {
        grid.addClassName("location-grid");


        grid.addColumn(createLocationDetailsRenderer())
                .setHeader("Location Details")
                .setKey("name")
                .setAutoWidth(true)
                .setFlexGrow(1);

        grid.addColumns("country", "address");
        Renderer<LocationDto> iconRenderer = new ComponentRenderer<>(locationDto -> {
            if (locationDto.isFavourite()) {
                return new Div();
            }
            Icon iconComponent = new Icon(VaadinIcon.STAR);
            iconComponent.getElement().addEventListener("click", event -> {
            }).addEventData("event.stopPropagation()");
            iconComponent.addClickListener(iconClickEvent -> iconClickListener(iconClickEvent, locationDto));
            return iconComponent;
        });

        grid.addColumn(iconRenderer).setHeader("Favorite");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setPageSize(10);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void iconClickListener(ClickEvent<Icon> iconClickEvent, LocationDto locationDto) {
        if (!userService.isUserLoggedIn()) {
            ViewNotificationUtils.showErrorNotification("Please login to add favorite locations!");
            return;
        }

        if (locationDto.isFavourite()) {
            return;
        }

        userFavLocationService.addNewUserFavoriteLocation(locationDto);
        locationDto.setFavourite(true);

        ViewNotificationUtils.showNotification(locationDto.getName() + " added to favorites!");

        Icon clickedIcon = iconClickEvent.getSource();
        clickedIcon.getElement().setVisible(false);
    }


    protected Component getContent() {
        var content = new HorizontalLayout(grid, dailyWeatherForecastView);
        content.setWidthFull();
        content.setFlexGrow(1, grid);
        content.setFlexGrow(2, dailyWeatherForecastView);

        content.setClassName("content");
        content.setSizeFull();

        return content;
    }

    protected void configureGrid() {

        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        configureGridColumns();

        gridFilterTextField.setPlaceholder("Filter by name");
        gridFilterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        gridFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        gridFilterTextField.addValueChangeListener(e -> dataView.refreshAll());

        var headerRow = grid.appendHeaderRow();
        headerRow.getCell(grid.getColumnByKey("name")).setComponent(gridFilterTextField);
        headerRow.getCell(grid.getColumnByKey("name")).setComponent(gridFilterTextField);
        grid.asSingleSelect().addValueChangeListener(e -> openWeatherForecastViewFor(e.getValue()));
    }


    protected void openWeatherForecastViewFor(LocationDto location) {
        dailyWeatherForecastView.setSizeFull();

        if (!userService.isUserLoggedIn()) {
            ViewNotificationUtils.showErrorNotification("Please login to view weather forecast");
            return;
        }

        if (location == null) {
            dailyWeatherForecastView.closeView();
            return;
        }

        dailyWeatherForecastView.setLocation(location);
        dailyWeatherForecastView.setVisibility(true, weatherDataService.getWeatherData(location.getLatitude(), location.getLongitude()));
    }
}
