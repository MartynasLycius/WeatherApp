package org.weather.app.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
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
import org.weather.app.config.Constants;
import org.weather.app.config.Routes;
import org.weather.app.service.DomainUserDetailsService;
import org.weather.app.service.FavouriteLocationService;
import org.weather.app.service.LocationService;
import org.weather.app.service.dto.Location;
import org.weather.app.service.dto.SearchFilter;
import org.weather.app.service.provider.LocationDataProvider;

@AnonymousAllowed
@PageTitle(Constants.LOCATION_SEARCH)
@Route(value = Routes.DEFAULT_ROUTE, layout = ApplicationLayout.class)
public class DefaultView extends VerticalLayout {
  // Location Search without LOGIN/ before login

  Div mainLayout = new Div();
  TextField search = new TextField();
  TextField filter = new TextField();
  Grid<Location> locationGrid = new Grid<>(Location.class, false);

  private final DomainUserDetailsService domainUserDetailsService;
  private final FavouriteLocationService favouriteLocationService;
  private final SearchFilter locationFilter = new SearchFilter();
  private ConfigurableFilterDataProvider<Location, Void, SearchFilter> filterDataProvider;

  public DefaultView(
      LocationService locationService,
      DomainUserDetailsService domainUserDetailsService,
      FavouriteLocationService favouriteLocationService) {
    Routes.setCurrentPath(Routes.DEFAULT_ROUTE);
    this.domainUserDetailsService = domainUserDetailsService;
    this.favouriteLocationService = favouriteLocationService;
    this.filterDataProvider = new LocationDataProvider(locationService).withConfigurableFilter();

    initMainLayout();
    applySearchAndFilter();
    populateLocationGrid();
    applyMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("DefaultView-extends-VerticalLayout");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);
  }

  private void applySearchAndFilter() {
    search.setWidthFull();
    search.setPlaceholder(Constants.LOCATION_SEARCH_PLACEHOLDER);
    search.setClearButtonVisible(true);
    search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
    search.setValueChangeMode(ValueChangeMode.LAZY);
    search.addValueChangeListener(event -> searchLocations(event.getValue()));

    filter.setWidthFull();
    filter.setPlaceholder(Constants.LOCATION_FILTER_PLACEHOLDER);
    filter.setClearButtonVisible(true);
    filter.setPrefixComponent(new Icon(VaadinIcon.FILTER));
    filter.setValueChangeMode(ValueChangeMode.LAZY);
    filter.addValueChangeListener(e -> filterLocations(e.getValue()));
  }

  private void populateLocationGrid() {
    locationGrid.setSizeFull();
    locationGrid.addColumn(Location::getName, Constants.FILED_NAME).setHeader("Name");
    locationGrid.addColumn(Location::getCountry, Constants.FILED_COUNTRY).setHeader("Country");
    locationGrid.setItems(filterDataProvider);
    locationGrid.addColumn(renderFavoriteIcons()).setHeader("Favorite");
    locationGrid.getColumns().forEach(col -> col.setAutoWidth(true));

    // Single row item clicked and redirect to profile page
    locationGrid
        .asSingleSelect()
        .addValueChangeListener(
            event -> {
              // By kipping location object in vaadin session object passes to the next page
              // (profile page we can get it from vaadin session)
              Routes.setSessionValue(Constants.SESSION_KEY, event.getValue());
              Routes.openPage(Routes.PROFILE_ROUTE);
            });
  }

  private void applyMainLayout() {
    mainLayout.setWidth("35em");
    mainLayout.setId("location-container");
    mainLayout.setHeightFull();
    mainLayout.addClassNames(
        LumoUtility.TextColor.ERROR,
        LumoUtility.Padding.SMALL,
        LumoUtility.Background.BASE,
        LumoUtility.BoxShadow.XLARGE,
        LumoUtility.BorderRadius.LARGE);
    mainLayout.add(buildLocationGridLayout());
  }

  private VerticalLayout buildLocationGridLayout() {
    VerticalLayout gridLayout = new VerticalLayout();
    gridLayout.setId("location-grid");
    gridLayout.setSizeFull();
    gridLayout.setAlignItems(Alignment.CENTER);

    gridLayout.add(new H3(Constants.LOCATION_SEARCH));
    gridLayout.add(search);
    gridLayout.add(filter);
    gridLayout.add(locationGrid);
    return gridLayout;
  }

  private ComponentRenderer<Icon, Location> renderFavoriteIcons() {
    return new ComponentRenderer<>(
        location -> {
          final Icon icon = new Icon(VaadinIcon.THUMBS_UP_O);
          if (isFavourite(location)) {
            icon.setColor("blue");
          } else {
            icon.setColor("grey");
          }
          icon.getElement()
              .addEventListener("click", event -> onClickFavouriteIcon(location, icon))
              .addEventData("event.stopPropagation()");
          return icon;
        });
  }

  private void onClickFavouriteIcon(Location location, Icon icon) {
    if (domainUserDetailsService.isUserLoggedIn()) {
      if (isFavourite(location)) {
        favouriteLocationService.removeFavouriteLocationOfLoggedInUser(location);
        icon.setColor("grey");
      } else {
        favouriteLocationService.applyFavouriteLocationToLoggedInUser(location);
        icon.setColor("blue");
      }
      return;
    }
    Notification.show(Constants.FAVORITE_NOTIFICATION, 2000, Notification.Position.TOP_END)
        .addThemeVariants(NotificationVariant.LUMO_ERROR);
  }

  private boolean isFavourite(Location location) {
    return (domainUserDetailsService.isUserLoggedIn())
        && favouriteLocationService.alreadyFavouriteLocationOfLoggedInUser(location);
  }

  private void searchLocations(String searchKey) {
    locationFilter.setSearchTerm(searchKey);
    filterDataProvider.setFilter(locationFilter);
  }

  private void filterLocations(String filterKey) {
    locationFilter.setFilterTerm(filterKey);
    filterDataProvider.setFilter(locationFilter);
  }
}
