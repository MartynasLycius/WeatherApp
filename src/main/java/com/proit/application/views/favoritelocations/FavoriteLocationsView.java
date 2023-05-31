package com.proit.application.views.favoritelocations;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.service.LocationService;
import com.proit.application.service.UserFavLocationService;
import com.proit.application.service.UserService;
import com.proit.application.service.WeatherDataService;
import com.proit.application.views.MainLayout;
import com.proit.application.views.common.AbstractLocationView;
import com.proit.application.views.common.ViewNotificationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.SortDirection;
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
public class FavoriteLocationsView extends AbstractLocationView {
    private final LocationService locationService;
    private final UserFavLocationService userFavLocationService;

    private Button previousButton;
    private Button nextButton;

    private final int PAGE_SIZE = 10;
    private int currentPage = 0;

    public FavoriteLocationsView(LocationService locationService, WeatherDataService weatherDataService, UserFavLocationService userFavLocationService, UserService userService) {
        super(weatherDataService, userService);
        this.locationService = locationService;
        this.userFavLocationService = userFavLocationService;

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
        Page<LocationDto> page = locationService.getAllFavoriteLocationOfCurrentUser(pageable, locationNameFilter);
        List<LocationDto> locations = page.getContent();
        updateGridItems(locations);
    }

    @Override
    protected void configureGridColumns() {
        grid.addClassName("favorite-locations-grid");

        grid.addColumn("name");
        grid.addColumn(createLocationDetailsRenderer())
                .setHeader("Location Details")
                .setKey("country")
                .setAutoWidth(true)
                .setSortProperty("country")
                .setSortable(true)
                .setFlexGrow(1);
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

        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by("name").ascending());
        Page<LocationDto> page = locationService.getAllFavoriteLocationOfCurrentUser(pageable, gridFilterTextField.getValue());
        boolean isLastPage = page.getNumber() >= page.getTotalPages() - 1;
        nextButton.setEnabled(!isLastPage);
        previousButton.setEnabled(currentPage > 0);

        return new HorizontalLayout(previousButton, nextButton);
    }

    private void navigateToPage(int page) {
        currentPage = page;

        Pageable pageable = PageRequest.of(currentPage, PAGE_SIZE, Sort.by(getSortOrder(grid.getSortOrder())));
        Page<LocationDto> pageData = locationService.getAllFavoriteLocationOfCurrentUser(pageable, gridFilterTextField.getValue());
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

        return orders.length > 0 ? orders : new Sort.Order[]{new Sort.Order(Sort.Direction.ASC, "name")};
    }
}
