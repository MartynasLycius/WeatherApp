package com.waheduzzaman.MeteoWeather.views.location;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.config.AppRoutes;
import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.filter.LocationFilter;
import com.waheduzzaman.MeteoWeather.data.provider.LocationDataProvider;
import com.waheduzzaman.MeteoWeather.security.UserDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.service.impl.FavouriteLocationServiceImpl;
import com.waheduzzaman.MeteoWeather.service.impl.LocationServiceImpl;
import com.waheduzzaman.MeteoWeather.views.MainLayout;

@AnonymousAllowed
@PageTitle("Search For Location | Meteo Weather")
@Route(value = AppRoutes.INDEX_ROUTE, layout = MainLayout.class)
public class LocationSearchView extends VerticalLayout {

    private UserDetailsServiceImpl userDetailsService;
    private FavouriteLocationServiceImpl favouriteLocationService;
    private final LocationFilter locationFilter = new LocationFilter();
    private ConfigurableFilterDataProvider<Location, Void, LocationFilter> filterDataProvider;
    Div locationContainer = new Div();
    TextField searchLocationTextField = new TextField();
    TextField filterTextField = new TextField();
    Grid<Location> locationGrid = new Grid<>(Location.class, false);

    public LocationSearchView(LocationServiceImpl locationService, UserDetailsServiceImpl userDetailsService, FavouriteLocationServiceImpl favouriteLocationService) {
        AppRoutes.setCurrentPath(AppRoutes.INDEX_ROUTE);
        initObjects(locationService, userDetailsService, favouriteLocationService);
        configureLayout();
        configureTextFields();
        configureGrid();
        createLocationContainer();
        add(locationContainer);
    }

    private void initObjects(LocationServiceImpl locationService, UserDetailsServiceImpl userDetailsService, FavouriteLocationServiceImpl favouriteLocationService) {
        this.filterDataProvider = new LocationDataProvider(locationService).withConfigurableFilter();
        this.userDetailsService = userDetailsService;
        this.favouriteLocationService = favouriteLocationService;
    }

    private void configureTextFields() {
        searchLocationTextField.setWidthFull();
        searchLocationTextField.setPlaceholder("enter city name");
        searchLocationTextField.setClearButtonVisible(true);
        searchLocationTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchLocationTextField.setValueChangeMode(ValueChangeMode.LAZY);
        searchLocationTextField.addValueChangeListener(event -> updateCountryList(event.getValue()));

        filterTextField.setWidthFull();
        filterTextField.setPlaceholder("filter result");
        filterTextField.setClearButtonVisible(true);
        filterTextField.setPrefixComponent(new Icon(VaadinIcon.FILTER));
        filterTextField.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextField.addValueChangeListener(e -> filterCountryList(e.getValue()));
    }

    private void createLocationContainer() {
        locationContainer.setWidth("35em");
        locationContainer.setHeightFull();
        locationContainer.addClassNames(
                LumoUtility.TextColor.ERROR,
                LumoUtility.Padding.SMALL,
                LumoUtility.Background.BASE,
                LumoUtility.BoxShadow.XLARGE,
                LumoUtility.BorderRadius.LARGE
        );

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(Alignment.CENTER);
        locationContainer.add(verticalLayout);

        layoutViews(verticalLayout);
    }

    private void layoutViews(VerticalLayout verticalLayout) {
        verticalLayout.add(new H3("Search For Location"));
        verticalLayout.add(searchLocationTextField);
        verticalLayout.add(filterTextField);
        verticalLayout.add(locationGrid);
    }

    private void configureLayout() {
        Span span = new Span();
        span.addClassNames(
                LumoUtility.Padding.LARGE,
                LumoUtility.Background.BASE,
                LumoUtility.BoxShadow.MEDIUM,
                LumoUtility.BorderRadius.LARGE
        );

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configureGrid() {
        locationGrid.setSizeFull();
        locationGrid.addColumn(Location::getName, "name").setHeader("Name");
        locationGrid.addColumn(Location::getCountry, "country").setHeader("Country");
        locationGrid.setItems(filterDataProvider);
        locationGrid.addColumn(getActionsRenderer()).setHeader("Bookmark");

        locationGrid.getColumns().forEach(col ->
                col.setAutoWidth(true)
        );

        locationGrid.asSingleSelect().addValueChangeListener(event ->
                {
                    AppRoutes.setSessionValue("location", event.getValue());
                    AppRoutes.openPage(AppRoutes.HOME_ROUTE);
                }
        );

    }

    private ComponentRenderer<Icon, Location> getActionsRenderer() {
        return new ComponentRenderer<>(location -> {
            final Icon icon = new Icon(VaadinIcon.BOOKMARK);
            if (isFavourite(location)) {
                icon.setColor("blue");
            } else {
                icon.setColor("grey");
            }
            icon.getElement().addEventListener("click",
                            event -> handleFavouriteItemClick(location, icon))
                    .addEventData("event.stopPropagation()");
            return icon;
        });
    }

    private void handleFavouriteItemClick(Location location, Icon icon) {
        if (!userDetailsService.isUserLoggedIn()) {
            Notification.show("Login first").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            if (isFavourite(location)) {
                favouriteLocationService.removeFavouriteLocationByLocation(location);
                icon.setColor("grey");
            } else {
                favouriteLocationService.addNewFavouriteLocation(location);
                icon.setColor("blue");
            }
        }
    }

    private boolean isFavourite(Location location) {
        return (userDetailsService.isUserLoggedIn()) && favouriteLocationService.doesRecordExist(location);
    }

    private void updateCountryList(String searchKey) {
        locationFilter.setSearchTerm(searchKey);
        filterDataProvider.setFilter(locationFilter);
    }

    private void filterCountryList(String filterKey) {
        locationFilter.setFilterTerm(filterKey);
        filterDataProvider.setFilter(locationFilter);
    }

}
