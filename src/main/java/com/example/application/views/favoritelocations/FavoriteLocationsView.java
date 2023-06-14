package com.example.application.views.favoritelocations;

import com.example.application.data.dto.LocationDto;
import com.example.application.service.LocationService;
import com.example.application.service.UserFavLocationService;
import com.example.application.service.UserService;
import com.example.application.service.WeatherDataService;
import com.example.application.views.MainLayout;
import com.example.application.views.common.ViewNotificationUtils;
import com.example.application.views.forecast.DailyWeatherForecastView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@PageTitle("Favorite Locations")
@Route(value = "favorite", layout = MainLayout.class)
@PermitAll
public class FavoriteLocationsView extends VerticalLayout {
    private final UserFavLocationService userFavLocationService;

    private Button previousButton;
    private Button nextButton;

    private final int PAGE_SIZE = 10;
    private int currentPage = 0;

    protected final WeatherDataService weatherDataService;
    protected final UserService userService;
    protected final Grid<LocationDto> grid;
    protected GridListDataView<LocationDto> dataView;
    protected DailyWeatherForecastView dailyWeatherForecastView = new DailyWeatherForecastView();
    ;
    protected final TextField gridFilterTextField;

    public FavoriteLocationsView(WeatherDataService weatherDataService, UserFavLocationService userFavLocationService, UserService userService) {

        this.userFavLocationService = userFavLocationService;
        this.weatherDataService = weatherDataService;
        this.userService = userService;

        this.grid = new Grid<>(LocationDto.class, false);
        this.gridFilterTextField = new TextField();
        configureGrid();
        setSizeFull();

        add(
                getContent(),
                createPaginationButtons()
        );

        String filterValue = gridFilterTextField.getValue();
        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("name").ascending());
        addData(pageable, filterValue);

        dailyWeatherForecastView.setVisibility(false, null);
    }

    private void addData(Pageable pageable, String locationNameFilter) {
        Page<LocationDto> page = userFavLocationService.getAllFavoriteLocationOfCurrentUser(pageable, locationNameFilter);
        List<LocationDto> locations = page.getContent();
        updateGridItems(locations);
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

        grid.addClassName("favorite-locations-grid");

        grid.addColumn(createLocationDetailsRenderer())
                .setHeader("Location Details")
                .setKey("name")
                .setAutoWidth(true)
                .setFlexGrow(1);
        grid.addColumn("country");
        grid.addComponentColumn(this::createDeleteButton)
                .setHeader("Actions")
                .setFlexGrow(0);

        grid.addSortListener(e -> {
            String filterValue = gridFilterTextField.getValue();
            Pageable pageable = PageRequest.of(
                    currentPage,
                    PAGE_SIZE,
                    Sort.by(getSortOrder(e.getSortOrder()))
            );
            addData(pageable, filterValue);
        });

        gridFilterTextField.setPlaceholder("Filter by name");
        gridFilterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        gridFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        gridFilterTextField.addValueChangeListener(e -> onGridFilterTextFieldValueChange(e.getValue()));

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

        var data = weatherDataService.getWeatherData(location.getLatitude(), location.getLongitude());
        dailyWeatherForecastView.setLocation(location);
        dailyWeatherForecastView.setVisibility(true, data);
    }


    protected void updateGridItems(List<LocationDto> locations) {
        dataView = grid.setItems(locations);
    }

    protected void onGridFilterTextFieldValueChange(String value) {
        currentPage = 0;
        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("name").ascending());
        addData(pageable, value);
    }

    private Button createDeleteButton(LocationDto locationDto) {
        Button deleteButton = new Button(VaadinIcon.TRASH.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(e -> removeFromFavorite(locationDto));
        return deleteButton;
    }

    public static Renderer<LocationDto> createLocationDetailsRenderer() {
        return LitRenderer.<LocationDto>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<span class=\"fi fi-${item.countryCode}\" title=\"${item.country}\"></span>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.name} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.address}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>"
                )
                .withProperty("countryCode", locationDto -> locationDto.getCountryCode().toLowerCase())
                .withProperty("country", LocationDto::getCountry)
                .withProperty("name", LocationDto::getName)
                .withProperty("address", LocationDto::getAddress);
    }

    private void removeFromFavorite(LocationDto locationDto) {
        userFavLocationService.removeFromFavoriteLocation(locationDto.getId());

        String filterValue = gridFilterTextField.getValue();
        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("name").ascending());
        addData(pageable, filterValue);

        ViewNotificationUtils.showNotification(
                String.format("Location %s(%s) successfully removed from your favorite list", locationDto.getName(), locationDto.getAddress()),
                NotificationVariant.LUMO_PRIMARY
        );
    }

    private Component createPaginationButtons() {
        previousButton = new Button("Previous", VaadinIcon.ARROW_LEFT.create());
        previousButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        nextButton = new Button("Next", VaadinIcon.ARROW_RIGHT.create());
        nextButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        nextButton.setAutofocus(true);

        previousButton.addClickListener(e -> navigateToPage(currentPage - 1));
        nextButton.addClickListener(e -> navigateToPage(currentPage + 1));

        // Disable the previous button if on the first page
        previousButton.setEnabled(currentPage > 0);

        // Disable the next button if on the last page
        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("name").ascending());
        Page<LocationDto> page = userFavLocationService.getAllFavoriteLocationOfCurrentUser(pageable, gridFilterTextField.getValue());
        boolean isLastPage = page.getNumber() >= page.getTotalPages() - 1;
        nextButton.setEnabled(!isLastPage);

        return new HorizontalLayout(previousButton, nextButton);
    }

    private void navigateToPage(int page) {
        currentPage = page;

        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by(getSortOrder(grid.getSortOrder())));
        Page<LocationDto> pageData = userFavLocationService.getAllFavoriteLocationOfCurrentUser(pageable, gridFilterTextField.getValue());
        List<LocationDto> locations = pageData.getContent();
        updateGridItems(locations);

        // Update pagination buttons state
        previousButton.setEnabled(currentPage > 0);
        boolean isLastPage = pageData.getNumber() >= pageData.getTotalPages() - 1;
        nextButton.setEnabled(!isLastPage);
    }

    private Sort.Order[] getSortOrder(List<GridSortOrder<LocationDto>> sortOrder) {
        Sort.Order[] orders = sortOrder.stream()
                .map(order -> {
                    String propertyName = order.getSorted().getKey();
                    Sort.Direction direction = order.getDirection().equals(SortDirection.ASCENDING)
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC;
                    return new Sort.Order(direction, propertyName);
                })
                .toArray(Sort.Order[]::new);

        return orders.length > 0 ? orders : new Sort.Order[]{new Sort.Order(Sort.Direction.ASC, "country")};
    }
}
