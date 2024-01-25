package com.example.application.views.favourites;

import com.example.application.views.forecast.DailyView;
import com.example.application.services.FavouritesService;
import com.example.application.services.ForecastAPI;
import com.example.application.services.GeocodingAPI;
import com.example.application.views.MainLayout;
import com.example.application.data.Favourites;
import com.example.application.data.Location;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.Component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@PermitAll
@Route(value = "favourites", layout = MainLayout.class) // <1>
@PageTitle("Favourites | Vaadin CRM")
public class FavouritesView extends DailyView {

    public FavouritesView(@Autowired GeocodingAPI geocoding,@Autowired ForecastAPI forecast,@Autowired FavouritesService favServ) {
        super(geocoding, forecast, favServ);
        this.updateList();
    }
    
    @Override
    public void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setPageSize(5);
        grid.setColumns();
        grid.addColumn(contact -> contact.getName()).setHeader("City");
        grid.addColumn(contact -> contact.getLatitude()).setHeader("Latitude");
        grid.addColumn(contact -> contact.getLongitude()).setHeader("Longitude");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addItemClickListener(item -> {
            this.openEditor(item.getItem());
            infoGo.setText(item.getItem().getName());
        });
    }
    @Override
    public void updateList() {
        List<Favourites> all = this.favouriteService.getAll();
        if (all != null) {
            List<Location> setLocations = new ArrayList<Location>();
            all.forEach(item -> {
                setLocations.add(new Location().setMainAttributes(Integer.parseInt(item.getLocationId()),item.getName(),Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())));
            });
            this.loadedLocations = setLocations;
            this.loadedItemsSize = this.loadedLocations.size();
            this.updateListWithPaging(0);
            this.currentPage = 0;
            this.rightButton.setEnabled(this.loadedItemsSize > PER_PAGE_ITEMS);
            this.leftButton.setEnabled(false);
        } else {
            this.grid.setItems();
        }
    }
    @Override
    public void updateListWithPaging(Integer pageNr) {
        if (this.loadedItemsSize > DailyView.PER_PAGE_ITEMS) {
            Double allPages = Math.ceil(Double.parseDouble(this.loadedItemsSize+".0")/DailyView.PER_PAGE_ITEMS);
            Integer allPagesInt = allPages.intValue();
            this.infoText.setText("Current page: "+(pageNr+1)+". All pages: "+allPagesInt); 
            List<Location> use = new ArrayList<Location>();
            for (Integer i=pageNr*DailyView.PER_PAGE_ITEMS; i < (pageNr*DailyView.PER_PAGE_ITEMS)+DailyView.PER_PAGE_ITEMS; i++) {
                if (this.loadedItemsSize > i) {
                    use.add(this.loadedLocations.get(i));
                }
            }
            if (this.loadedItemsSize <= (pageNr*DailyView.PER_PAGE_ITEMS)+DailyView.PER_PAGE_ITEMS) {
                this.rightButton.setEnabled(false);
            }
            this.grid.setItems(use);
        }
    }
    @Override
    public Component getToolbar() {
        this.leftButton = new Button("<");
        this.leftButton.addClickListener(click -> this.moveLeft());
        this.leftButton.setEnabled(false);
        this.rightButton = new Button(">");
        this.rightButton.addClickListener(click -> this.moveRight());
        this.rightButton.setEnabled(false);
        Button refresh = new Button("Refresh");
        refresh.addClickListener(click -> this.updateList());

        HorizontalLayout toolbar = new HorizontalLayout(this.leftButton, this.rightButton,refresh,this.infoText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}
