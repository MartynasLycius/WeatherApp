package com.eastnetic.task.views.pages;

import com.eastnetic.task.model.dto.LocationResults;
import com.eastnetic.task.service.LocationService;
import com.eastnetic.task.views.layout.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@PageTitle("Find Location")
@Route(value = "location", layout = MainLayout.class)
@PermitAll
@Slf4j
public class LocationView extends VerticalLayout {

    LocationService locationService;
    VerticalLayout gridLayout;

    public LocationView(LocationService locationService) {
        this.locationService = locationService;
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        TextField searchByCityField = new TextField();
        searchByCityField.setPlaceholder("Enter a City");
        searchByCityField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchByCityField.setWidth("400px");

        Button button = new Button("Search");
        button.addClickShortcut(Key.ENTER);
        horizontalLayout.add(searchByCityField, button);
        add(horizontalLayout, new VerticalLayout());

        button.addClickListener(clickEvent -> {
            gridLayout = new VerticalLayout();
            String cityName = searchByCityField.getValue().trim();
            if(!cityName.isEmpty()){
                setGridLayout(cityName);
                replace(getComponentAt(1), gridLayout);
            }
        });


    }

    private void setGridLayout(String cityName){
        gridLayout = new VerticalLayout();
        Grid<LocationResults> grid = new Grid<>(LocationResults.class, false);
        /*grid.addColumn(createLocationRenderer()).setHeader("Name").setFlexGrow(0)
                .setWidth("230px");*/
        grid.addColumn(LocationResults::getName).setHeader("City").setSortable(true);
        grid.addColumn(LocationResults::getAdmin1).setHeader("Region").setSortable(true);
        grid.addColumn(LocationResults::getCountry).setHeader("Country").setSortable(true);
        grid.addColumn(LocationResults::getTimezone).setHeader("Timezone").setSortable(true);
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, results) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.addToFavorites(results));
                    button.setIcon(new Icon(VaadinIcon.HEART));
                    button.setText("Add to Favorites");
                })).setHeader("Actions");

        List<LocationResults> locations = locationService.getLocations(cityName).getResults();

        if (locations != null && locations.size() > 0){
            GridListDataView<LocationResults> dataView = grid.setItems(locations);

            TextField searchField = new TextField();
            searchField.setWidth("30%");
            searchField.setPlaceholder("Filter from search results");
            searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
            searchField.setValueChangeMode(ValueChangeMode.EAGER);
            searchField.addValueChangeListener(e -> dataView.refreshAll());
            searchField.getStyle().set("margin-left", "auto");

            dataView.addFilter(locationResults -> {
                String searchTerm = searchField.getValue().trim();
                if (searchTerm.isEmpty())
                    return true;
                boolean matchesName = matchesTerm(locationResults.getName(), searchTerm);
                boolean matchesRegion = matchesTerm(locationResults.getAdmin1(), searchTerm);
                boolean matchesCountry = matchesTerm(locationResults.getCountry(), searchTerm);
                boolean matchesTimezone = matchesTerm(locationResults.getTimezone(), searchTerm);
                return matchesName || matchesRegion || matchesCountry || matchesTimezone;
            });
            grid.setPageSize(10);
            gridLayout.add(searchField, grid);
        } else {
            Div emptyGridMessage = new Div(new Icon(VaadinIcon.WARNING), new Text("No data found for \"" + cityName + "\""));
            gridLayout.add(emptyGridMessage);
        }
    }

    private void addToFavorites(LocationResults results) {
        log.info(results.toString());
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}
