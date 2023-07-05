package com.eastnetic.task.views.pages;

import com.eastnetic.task.model.entity.UserFavLocations;
import com.eastnetic.task.service.LocationService;
import com.eastnetic.task.service.UsersService;
import com.eastnetic.task.views.layout.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import java.util.Optional;

@PageTitle("Dashboard")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class DashboardView extends VerticalLayout {

    LocationService locationService;
    UsersService usersService;
    VerticalLayout favGridLayout;
    Grid<UserFavLocations> grid;
    GridListDataView<UserFavLocations> dataView;
    H2 title;

    /**
     * Main dashboard view create
     * @param locationService, usersService
     * @return
     * @throws
     */
    public DashboardView(UsersService usersService, LocationService locationService) {

        this.usersService = usersService;
        this.locationService = locationService;
        title = new H2("Welcome, " + usersService.getCurrentUser().getName());
        setAlignItems(Alignment.CENTER);
        setFavGridLayout();
        add(title, favGridLayout);
    }

    /**
     * Favorites list grid create
     * @param
     * @return
     * @throws
     */
    private void setFavGridLayout() {
        favGridLayout = new VerticalLayout();
        grid = new Grid<>(UserFavLocations.class, false);
        grid.addColumn(createFavDataRenderer()).setFlexGrow(0).setWidth("90%");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, userFavLocations) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.deleteFavorites(userFavLocations));
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                }));

        grid.addSelectionListener(selection -> {
            Optional<UserFavLocations> favResults = selection.getFirstSelectedItem();
            if (favResults.isPresent()) {
                UI.getCurrent().navigate("/location?fav="+favResults.get().getId());
            }
        });

        List<UserFavLocations> favList = locationService.getFavorites(usersService.getCurrentUser().getId());

        if (favList != null && favList.size() > 0){
            dataView = grid.setItems(favList);

            TextField searchField = new TextField();
            searchField.setWidth("30%");
            searchField.setPlaceholder("Filter by Name, Region or Country");
            searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
            searchField.setValueChangeMode(ValueChangeMode.EAGER);
            searchField.addValueChangeListener(e -> dataView.refreshAll());
            searchField.getStyle().set("margin-left", "auto");

            dataView.addFilter(favLocations -> {
                String searchTerm = searchField.getValue().trim();
                if (searchTerm.isEmpty())
                    return true;
                boolean matchesName = matchesTerm(favLocations.getName(), searchTerm);
                boolean matchesRegion = matchesTerm(favLocations.getRegion(), searchTerm);
                boolean matchesCountry = matchesTerm(favLocations.getCountry(), searchTerm);
                return matchesName || matchesCountry || matchesRegion;
            });
            grid.setPageSize(10);
            HorizontalLayout filterLayout = new HorizontalLayout(new H3("Favorites List"), searchField);
            filterLayout.setWidthFull();
            favGridLayout.add(filterLayout, grid);
        } else {
            Div emptyGridMessage = new Div(new Icon(VaadinIcon.INFO_CIRCLE), new Span(" Search a location by city name and mark as favorites for quick access."));
            favGridLayout.add(emptyGridMessage);
        }
    }

    /**
     * Match data for filtering grid
     * @param value, searchTerm
     * @return boolean
     * @throws
     */
    boolean matchesTerm(String value, String searchTerm) {
        if(value!= null && !value.isEmpty()){
            return value.toLowerCase().contains(searchTerm.toLowerCase());
        }
        return false;
    }

    /**
     * Delete data from favorites list
     * @param userFavLocations
     * @return
     * @throws
     */
    private void deleteFavorites(UserFavLocations userFavLocations) {
        Notification notification;
        if(locationService.deleteFavorites(userFavLocations)){
            notification = Notification.show("Deleted from Favorites list.");
            clearLayout();
        } else {
            notification = Notification.show("Delete from Favorites list failed.");
        }
        notification.setPosition(Notification.Position.MIDDLE);
    }

    /**
     * Data renderer for grid view
     * @param
     * @return Renderer
     * @throws
     */
    private static Renderer<UserFavLocations> createFavDataRenderer() {
        return LitRenderer.<UserFavLocations> of(
                        "<vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "<vaadin-horizontal-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "  <span>${item.name}</span> &nbsp;&nbsp;"
                                + "  <span style=\"color: var(--lumo-secondary-text-color);\"> ${item.region?item.region+', ':''} ${item.country}</span>"
                                + "</vaadin-horizontal-layout>"
                                + "  <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">Position: ${item.lat}&deg;E ${item.long}&deg;N; Timezone: ${item.timezone}</span>"
                                + "</vaadin-vertical-layout>")
                .withProperty("name", UserFavLocations::getName)
                .withProperty("region", UserFavLocations::getRegion)
                .withProperty("country", UserFavLocations::getCountry)
                .withProperty("lat", UserFavLocations::getLatitude)
                .withProperty("long", UserFavLocations::getLongitude)
                .withProperty("timezone", UserFavLocations::getTimezone);
    }

    /**
     * Layout clear to main view
     * @param
     * @return
     * @throws
     */
    private void clearLayout(){
        removeAll();
        setFavGridLayout();
        add(title, favGridLayout);
    }

}
