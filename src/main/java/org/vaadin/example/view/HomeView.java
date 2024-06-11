package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.vaadin.example.SecuredLayout;
import org.vaadin.example.entity.Location;
import org.vaadin.example.service.FavoriteLocationService;
import org.vaadin.example.service.LocationService;
import org.vaadin.tatu.BeanTable;

import java.util.List;

@Route(value = "", layout = SecuredLayout.class)
public class HomeView extends VerticalLayout implements SecuredView{

    @Inject
    LocationService locationService;

    @Inject
    FavoriteLocationService favoriteLocationService;

    private BeanTable<Location> beanTable;
    private TextField locationField = new TextField("Location");
    private Button searchButton = new Button("Search");
    private List<Location> locations;


    @PostConstruct
    public void HomeView() {
        H3 pageTitle = new H3("Home");

        HorizontalLayout searchFilterLayout = new HorizontalLayout();
        searchButton.addClickListener(e -> searchLocations());
        searchButton.getStyle().setMarginTop("auto");


        searchFilterLayout.add(locationField, searchButton);

        beanTable = new BeanTable<>(Location.class, false, 10);
        beanTable.addColumn("Location", Location::getName);
        beanTable.addColumn("Country", Location::getCountry);
        beanTable.addColumn("Latitude", Location::getLatitude);
        beanTable.addColumn("Longitude", Location::getLongitude);

        beanTable.addComponentColumn("Status", location -> {
           Button favoriteButton = new Button("Add as favorite");
           favoriteButton.addClickListener(event -> {
              favoriteLocationService.addFavoriteLocation(location);
              favoriteButton.setText("Added as favorite");
              favoriteButton.setEnabled(false);
           });
           return favoriteButton;
        }).setHeader("Status");

        beanTable.addComponentColumn("Action", location -> {
            Button viewButton = new Button("View Details");
            viewButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("location/" + location.getId() + "/forecast"));
            });
            return viewButton;
        }).setHeader("Action");

        beanTable.setWidthFull();
        searchLocations("Dhaka");

        add(pageTitle, searchFilterLayout, beanTable);
    }


    private void searchLocations() {
        String locationName = locationField.getValue();
        searchLocations(locationName);
    }

    private void searchLocations(String locationName) {
        locations = locationService.getLocations(locationName);
        updateGrid();
    }

    private void updateGrid() {
        beanTable.setItems(locations);
    }
}
