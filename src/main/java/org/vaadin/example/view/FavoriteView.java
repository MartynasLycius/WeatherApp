package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.vaadin.example.SecuredLayout;
import org.vaadin.example.entity.Location;
import org.vaadin.example.service.FavoriteLocationService;
import org.vaadin.tatu.BeanTable;

import java.util.List;

@Route(value="favorites", layout = SecuredLayout.class)
public class FavoriteView extends VerticalLayout implements SecuredView  {

    @Inject
    FavoriteLocationService favoriteLocationService;

    @PostConstruct
    public void FavoriteView() {
        H3 pageTitle = new H3("Favorites");

        BeanTable<Location> beanTable = new BeanTable<>(Location.class, false, 10);

        beanTable.addColumn("Location", Location::getName);
        beanTable.addColumn("Country", Location::getCountry);
        beanTable.addColumn("Latitude", Location::getLatitude);
        beanTable.addColumn("Longitude", Location::getLongitude);

        beanTable.addComponentColumn("Action", location -> {
            Button viewButton = new Button("View Details");
            viewButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("location/" + location.getId() + "/forecast"));
            });
            return viewButton;
        }).setHeader("Action");

        List<Location> locations = favoriteLocationService.getFavoriteLocations();

        beanTable.setItems(locations);
        beanTable.setWidthFull();

        add(pageTitle, beanTable);
    }
}
