package com.example.application.views.forecast;

import com.example.application.data.Location;
import com.example.application.data.LocationResults;
import com.example.application.services.GeocodingAPI;
import com.example.application.services.FavouritesService;
import com.example.application.services.ForecastAPI;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.html.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Forecast | Vaadin CRM")
public class DailyView extends VerticalLayout {
    public static final int PER_PAGE_ITEMS = 10;
    public final ForecastAPI forecast;
    public final GeocodingAPI geocoding;
    public final FavouritesService favouriteService;

    public Grid<Location> grid = new Grid<>(Location.class);
    public List<Location> loadedLocations;
    public Span infoText = new Span();
    public Span infoGo = new Span();
    public TextField filterText = new TextField();
    DayView dayView;
    public Integer currentPage;
    public Integer loadedItemsSize;
    public Button leftButton;
    public Button rightButton;
    
    public DailyView(@Autowired GeocodingAPI geocoding,@Autowired ForecastAPI forecast,@Autowired FavouritesService favServ) {
        this.forecast = forecast;
        this.geocoding = geocoding;
        this.favouriteService = favServ;
        this.currentPage = 0;

        addClassName("Cities-view");
        setSizeFull();
        configureGrid();
        configureDayView();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, dayView);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, dayView);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureDayView() {
        dayView = new DayView(this.forecast,this.favouriteService);
        dayView.setWidth("25em");
    }

    public void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setPageSize(5);
        grid.setColumns();
        grid.addColumn(contact -> contact.getName()).setHeader("City");
        grid.addColumn(contact -> contact.getCountry()).setHeader("Country");
        grid.addColumn(contact -> contact.getTimezone()).setHeader("Timezone");
        grid.addColumn(contact -> contact.getLatitude()).setHeader("Latitude");
        grid.addColumn(contact -> contact.getLongitude()).setHeader("Longitude");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.addItemClickListener(item -> {
            this.openEditor(item.getItem());
            infoGo.setText(item.getItem().getName());
        });
    }

    public Component getToolbar() {
        filterText.setPlaceholder("Search by city name");
        filterText.setClearButtonVisible(true);

        Button addContactButton = new Button("Search");
        addContactButton.addClickListener(click -> updateList());

        leftButton = new Button("<");
        leftButton.addClickListener(click -> moveLeft());
        leftButton.setEnabled(false);
        rightButton = new Button(">");
        rightButton.addClickListener(click -> moveRight());
        rightButton.setEnabled(false);

        var toolbar = new HorizontalLayout(filterText, addContactButton, leftButton, rightButton,infoText,infoGo);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void closeEditor() {
        dayView.setVisible(false);
        removeClassName("editing");
    }
    public void openEditor(Location locationSelected) {
        dayView.setSelectedLocation(locationSelected);
        dayView.setVisible(true);
        addClassName("editing");
    }
    public void moveRight() {
        if (this.currentPage > -1) {
            this.currentPage += 1;
            this.leftButton.setEnabled(true);
            this.updateListWithPaging(this.currentPage);
        }
    }
    public void moveLeft() {
        if (this.currentPage > 0) {
            this.currentPage -= 1;
            this.rightButton.setEnabled(true);
            if (this.currentPage == 0) {
                this.leftButton.setEnabled(false);
            }
            this.updateListWithPaging(this.currentPage);
        }
    }
    public void updateList() {
        LocationResults locations = this.geocoding.getLocations(filterText.getValue());
        if (locations != null) {
            loadedLocations = locations.getLocations();
            this.loadedItemsSize = loadedLocations.size();
            this.updateListWithPaging(0);
            this.currentPage = 0;
            this.rightButton.setEnabled(this.loadedItemsSize > PER_PAGE_ITEMS);
            this.leftButton.setEnabled(false);
        } else {
            this.grid.setItems();
        }
    }
    public void updateListWithPaging(Integer pageNr) {
        if (this.loadedItemsSize > PER_PAGE_ITEMS) {
            Double allPages = Math.ceil(Double.parseDouble(this.loadedItemsSize+".0")/DailyView.PER_PAGE_ITEMS);
            Integer allPagesInt = allPages.intValue(); 
            this.infoText.setText("Current page: "+(pageNr+1)+". All pages: "+allPagesInt); 
            List<Location> use = new ArrayList<Location>();
            for (Integer i=pageNr*PER_PAGE_ITEMS; i < (pageNr*PER_PAGE_ITEMS)+PER_PAGE_ITEMS; i++) {
                if (this.loadedItemsSize > i) {
                    use.add(this.loadedLocations.get(i));
                }
            }
            if (this.loadedItemsSize <= (pageNr*PER_PAGE_ITEMS)+PER_PAGE_ITEMS) {
                this.rightButton.setEnabled(false);
            }
            this.grid.setItems(use);
        }
    }
}
