package com.eastnetic.task.views.pages;

import com.eastnetic.task.model.dto.LocationResults;
import com.eastnetic.task.service.LocationService;
import com.eastnetic.task.views.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.List;

@PageTitle("Find Location")
@Route(value = "location", layout = MainLayout.class)
@PermitAll
public class LocationView extends VerticalLayout {

    /*public LocationView(LocationService locationService) {
        H2 title = new H2("Find desired city");
        add(locationService.getLocations("Dhaka").toString());
    }*/

    LocationService locationService;

    public LocationView(LocationService locationService) {
        this.locationService = locationService;
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField searchByCityField = new TextField();
        searchByCityField.setPlaceholder("Enter a City");
        searchByCityField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchByCityField.setWidth("50%");

        Button button = new Button("Search");
        button.addClickListener(clickEvent -> {
            String cityName = searchByCityField.getValue().trim();
            if(!cityName.isEmpty()){
                setGridLayout(cityName);
            }
        });

        horizontalLayout.add(searchByCityField, button);
        add(horizontalLayout);
    }

    private void setGridLayout(String cityName){
        Grid<LocationResults> grid = new Grid<>(LocationResults.class, false);
        /*grid.addColumn(createLocationRenderer()).setHeader("Name").setFlexGrow(0)
                .setWidth("230px");*/
        grid.addColumn(LocationResults::getName).setHeader("Name").setSortable(true);;
        grid.addColumn(LocationResults::getCountry).setHeader("Country").setSortable(true);;

        List<LocationResults> locations = locationService.getLocations(cityName).getResults();
        GridListDataView<LocationResults> dataView = grid.setItems(locations);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Filter from search results");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(locationResults -> {
            String searchTerm = searchField.getValue().trim();
            if (searchTerm.isEmpty())
                return true;
            boolean matchesName = matchesTerm(locationResults.getName(),
                    searchTerm);
            boolean matchesCountry = matchesTerm(locationResults.getCountry(), searchTerm);
            return matchesName || matchesCountry;
        });
        grid.setPageSize(10);

        add(searchField, grid);
    }

    private static Renderer<LocationResults> createLocationRenderer() {
        return LitRenderer.<LocationResults> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <span> ${item.name} </span>"
                                + "  <span> ${item.country} </span>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("name", LocationResults::getName)
                .withProperty("country", LocationResults::getCountry);
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}
