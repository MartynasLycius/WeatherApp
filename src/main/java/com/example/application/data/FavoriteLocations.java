package com.example.application.data;

import com.example.application.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;


public class FavoriteLocations extends Div
{
    private SelectUpdateListener selectUpdateListener;
    private final CrmService service;

    public FavoriteLocations(CrmService service)
    {

        this.service = service;
        addClassName("favorite-locations");
        add(createFavoriteLocationsSelectLabel());
    }


    private Component createFavoriteLocationsSelectLabel()
    {
        removeAll();
        Select<Location> select = new Select<>();
        select.addValueChangeListener(event ->
        {
            Location selectedLocation = event.getValue();
            if (selectUpdateListener != null && selectedLocation != null)
            {
                selectUpdateListener.onSelectUpdated(selectedLocation);
                System.out.println("select listener debug");
            }


        });
        select.setEmptySelectionAllowed(true);
        select.setEmptySelectionCaption("Select a location");
        if (service.findAllFavoriteLocations().isEmpty())
        {
            // the list is empty
            select.setEnabled(false); // disable the select
            select.setEmptySelectionCaption("No favorite locations available");
        } else
        {
            // the list has items
            select.setItems(service.findAllFavoriteLocations());
            select.setItemLabelGenerator(location ->
            {
                if (location == null)
                {
                    return "Favorite locations";
                }
                return location.getLocationName() + ", " + location.getCountry();
            });
        }
        return select;
    }

    public void updateSelectComponent()
    {
        add(createFavoriteLocationsSelectLabel());
    }

    public void setSelectUpdateListener(SelectUpdateListener listener)
    {
        this.selectUpdateListener = listener;
    }
}
