package com.proit.application.views.searchlocation;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.service.UserFavLocationService;
import com.proit.application.service.UserService;
import com.proit.application.service.WeatherDataService;
import com.proit.application.views.MainLayout;
import com.proit.application.views.common.AbstractLocationView;
import com.proit.application.views.common.ViewNotificationUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@PageTitle("Search Location")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class SearchLocationView extends AbstractLocationView {
    private final UserFavLocationService userFavLocationService;
    private final TextField searchLocationTextField;

    public SearchLocationView(UserFavLocationService userFavLocationService, WeatherDataService weatherDataService, UserService userService) {
        super(weatherDataService, userService);
        this.userFavLocationService = userFavLocationService;

        dataView = grid.setItems();
        searchLocationTextField = new TextField();
        configureWeatherForecastView();

        addClassNames("list-view");

        add(
                getToolbar(),
                getContent()
        );

        dailyWeatherForecastView.setVisibility(false, null);
    }

    private Component getToolbar() {
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

        updateGridItems(weatherDataService.getLocations(searchLocationTextField.getValue()));
    }

    protected void updateGridItems(List<LocationDto> locations) {
        super.updateGridItems(locations);

        dataView.addFilter(locationDto -> {
            String searchTerm = gridFilterTextField.getValue();
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return true;
            }

            return locationDto.getName().toLowerCase().contains(searchTerm.toLowerCase());
        });
    }

    @Override
    protected void configureGridColumns() {
        grid.addClassName("location-grid");

        grid.addColumn("name")
                .setSortable(false);
        grid.addColumn(createLocationDetailsRenderer())
                .setHeader("Location Details")
                .setKey("country")
                .setAutoWidth(true)
                .setSortProperty("country")
                .setSortable(false)
                .setFlexGrow(1);

        grid.addColumn(getFavIconRenderer())
                .setHeader("Favorite");

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

        ViewNotificationUtils.showNotification(
                String.format("%s added to favorites!", locationDto.getName())
        );

        Icon clickedIcon = iconClickEvent.getSource();
        clickedIcon.getElement().setVisible(false);
    }

    protected void onGridFilterTextFieldValueChange(String value) {
        dataView.refreshAll();
    }

    private Renderer<LocationDto> getFavIconRenderer() {
        return new ComponentRenderer<>(locationDto -> {
            if (locationDto.isFavourite()) {
                return new Div();
            }

            Icon iconComponent = new Icon(VaadinIcon.STAR);

            iconComponent.getElement().addEventListener("click", event -> {})
                    .addEventData("event.stopPropagation()");
            iconComponent.addClickListener(iconClickEvent -> iconClickListener(iconClickEvent, locationDto));

            return iconComponent;
        });
    }
}
