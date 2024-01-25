package com.example.application.views.forecast;

import com.example.application.services.FavouritesService;
import com.example.application.data.DayForecast;
import com.example.application.data.Favourites;
import com.example.application.data.Location;
import com.example.application.data.DailyForecast;
import com.example.application.services.ForecastAPI;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.List;

public class DayView extends FormLayout {
  TextField firstName = new TextField("First name");
  Grid<DayForecast> grid;
  List<Favourites> loadedFavourites;
  Favourites selectedFavourite;
  FavouritesService favouritesService;
  Location selectedLocation;
  Checkbox save = new Checkbox("Favourite");
  Button delete = new Button("Delete");
  Button close = new Button("Close");
  ForecastAPI forecast;

  public DayView(ForecastAPI forecast, FavouritesService favService) {
    this.favouritesService = favService;
    this.forecast = forecast;
    this.grid = new Grid<>(DayForecast.class);
    addClassName("Day-view");
    this.configureGrid();
 
    Dialog dialog = new Dialog();
    HourlyView hourlyView = new HourlyView(this.forecast,dialog);
    
    this.grid.addItemClickListener(item -> {
      hourlyView.openAndLoadDialog(this.selectedLocation);
    });
    add(dialog,new VerticalLayout(this.grid,createButtonsLayout()));
  }
  private void configureGrid() {
    this.grid.setColumns();
    this.grid.addColumn(day -> day.getDate()).setHeader("Day").setAutoWidth(true).setFlexGrow(0);
    this.grid.addColumn(day -> day.getTemp()).setHeader("Temperature C").setAutoWidth(true).setFlexGrow(0);
  }
  public void setSelectedLocation(Location locationSelected) {
    this.selectedLocation = locationSelected;
    this.selectedFavourite = this.favouritesService.findOne(locationSelected.getId().toString());
    
    save.setValue(this.selectedFavourite != null); 
    DailyForecast forecastUse = this.forecast.getForecastDaily(locationSelected.getLatitude(), locationSelected.getLongitude());
    List<DayForecast> tempDays = new ArrayList<DayForecast>();
    for (Integer i=0; i < forecastUse.getDaily().getDays().size(); i++) {
      tempDays.add(new DayForecast(forecastUse.getDaily().getDays().get(i), forecastUse.getDaily().getTemperatures().get(i)));
    }
    this.grid.setItems(tempDays);
  }
  private Component createButtonsLayout() {
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);
    close.addClickListener(event -> {
      this.setVisible(false);
    });
    save.addClickListener(event -> {
      if (this.selectedFavourite != null) {
        this.favouritesService.delete(this.selectedFavourite.getId());
      } else {
        this.favouritesService.save(this.selectedLocation.getId(), this.selectedLocation.getName(), this.selectedLocation.getLatitude().toString(), this.selectedLocation.getLongitude().toString());
      }
    });
    return new HorizontalLayout(save, close);
  }
}

