package com.example.application.data;

import com.example.application.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class LocationForm extends FormLayout implements TemperatureButtonClickListener, SelectUpdateListener
{

    private Location currentLocation;

    String currentDate;

    public boolean isHourlyViewVisible()
    {
        return hourlyViewVisible;
    }

    public void setHourlyViewVisible(boolean hourlyViewVisible)
    {
        this.hourlyViewVisible = hourlyViewVisible;
    }

    private boolean hourlyViewVisible = false;

    CrmService service;


    private FavoriteLocationUpdateListener updateListener;

    public void setFavoriteLocationUpdateListener(FavoriteLocationUpdateListener listener)
    {
        this.updateListener = listener;
    }

    Button saveLocation = new Button("SAVE AS FAVORITE");

    Button deleteLocation = new Button("REMOVE FROM FAVORITES");

    @Override
    public void onTemperatureButtonClick(Location location, String date)
    {
        // click event, maybe update the form
    }

    public LocationForm(CrmService service)
    {
        addSaveLocationListener();
        addDeleteLocationListener();
        this.service = service;

    }

    private Component createDailyButtons()
    {

        HorizontalLayout dailyButtonLayout = new HorizontalLayout();
        for (int dayIndex = 0; dayIndex < 7; dayIndex++)
        {
            if (currentLocation.getDailyForecasts() != null && currentLocation.getDailyForecasts().size() > dayIndex)
            {
                VerticalLayout customButton = getDailyButtonLayout(dayIndex);
                customButton.setAlignItems(FlexComponent.Alignment.CENTER); // center the text horizontally
                customButton.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); //center the text vertically
                customButton.setSpacing(false);

                dailyButtonLayout.add(customButton);
            }


        }
        return dailyButtonLayout;
    }

    private VerticalLayout getDailyButtonLayout(int dayIndex)
    {
        DailyForecast forecast = currentLocation.getDailyForecasts().get(dayIndex);
        VerticalLayout customButton = new VerticalLayout();
        String currentDate = currentLocation.getDailyForecasts().get(dayIndex).getDate();
        Span textAbove = new Span(currentDate.substring(5));
        textAbove.addClassName("date-text");
        Span textBelow = new Span(forecast.getMaxTemperature() + "°, " + forecast.getMinTemperature() + "°");
        textBelow.addClassName("temperature-text");
        customButton.add(textAbove, textBelow);
        customButton.addClickListener(event ->
        {
            formHourlyView(currentLocation, currentDate);
        });
        customButton.addClassName("custom-button");
        return customButton;
    }

    private void formHourlyView(Location location, String date)
    {
        this.currentDate = date;
        hourlyViewVisible = true;
        updateForm(location);

    }


    public void updateForm(Location location)
    {
        this.currentLocation = location;
        removeAll();
        addClassName("location-form");
        Span regularText = new Span("Results for");
        regularText.addClassName("regular-text");
        Span boldText = new Span(currentLocation.getLocationName() + ", " + currentLocation.getCountry());
        boldText.addClassName("bold-text");
        HorizontalLayout locationNameLayout = new HorizontalLayout(regularText, boldText);
        locationNameLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
        locationNameLayout.setSpacing(false);
        locationNameLayout.addClassName("location-name-layout");

        HorizontalLayout locationNameAndButtons = new HorizontalLayout(locationNameLayout, createButtonLayout());
        locationNameAndButtons.addClassName("location-name-and-buttons");
        locationNameAndButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        locationNameAndButtons.setSpacing(true);
        VerticalLayout detailsLayout = new VerticalLayout(locationNameAndButtons, createDailyButtons());
        detailsLayout.addClassName("details-layout");

        if (hourlyViewVisible == true)
        {
            Span dateSpan = new Span("Showing hourly forecast for " + currentDate);
            detailsLayout.add(dateSpan, addHourlyForecastsForDate(location, currentDate));
        }

        add(detailsLayout);
    }

    private Component addHourlyForecastsForDate(Location location, String selectedDate)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        List<String> hoursToInclude =
                Arrays.asList("02:00", "05:00", "08:00", "11:00", "14:00", "17:00", "20:00", "23:00");
        HorizontalLayout hourlyLayout = new HorizontalLayout();

        for (HourlyForecast hourlyForecast : location.getHourlyForecasts())
        {
            LocalDateTime forecastTime = LocalDateTime.parse(hourlyForecast.getTime(), formatter);

            // checking if the selected date matches forecast date
            if (forecastTime.toLocalDate().toString().equals(selectedDate) &&
                    hoursToInclude.contains(forecastTime.toLocalTime().toString().substring(0, 5)))
            {
                // creating separate spans for time and details in order to make time appear different
                Span timeSpan = new Span(forecastTime.toLocalTime().toString());
                timeSpan.addClassName("forecast-time");
//                System.out.println(hourlyForecast.getRain());
                String detailsText = String.format("%s°C  Rain: %smm, Wind: %sm/s",
                        hourlyForecast.getTemperature(),
                        hourlyForecast.getRain(),
                        hourlyForecast.getWindSpeed());
                Span detailsSpan = new Span(detailsText);
                detailsSpan.addClassName("forecast-details");

                // creating layout for forecast and adding spans
                VerticalLayout forecastLayout = new VerticalLayout(timeSpan, detailsSpan);
                forecastLayout.addClassName("hourly-forecast");
                forecastLayout.setWidth(null);

                hourlyLayout.add(forecastLayout);
            }
        }

        return hourlyLayout;
    }


    private Component createButtonLayout()
    {
        removeAll();
        saveLocation.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        System.out.println("updateListener debug test1");
        saveLocation.addClassName("small-button");
        deleteLocation.addClassName("small-button");

        deleteLocation.addThemeVariants(ButtonVariant.LUMO_ERROR); //need to add listener


        return new HorizontalLayout(saveLocation, deleteLocation);
    }

    private void addSaveLocationListener()
    {
        saveLocation.addClickListener(event ->
        {
            if (currentLocation != null)
            {
                service.saveFavoriteLocation(currentLocation); // save the current location as a favorite
                Notification.show(currentLocation.getLocationName() + " saved as favorite!");
//                System.out.println("updateListener debug test2");
                // notify the listener that a favorite location has been updated
                if (updateListener != null)
                {
                    updateListener.onFavoriteLocationUpdated();
                }
            }
//            System.out.println("saveLocation didnt work, current location was null");
        });
    }

    private void addDeleteLocationListener()
    {
        deleteLocation.addClickListener(event ->
        {
            if (currentLocation != null)
            {
                service.deleteFavoriteLocation(currentLocation); // delete location from favorites
                Notification.show(currentLocation.getLocationName() + " removed from favorites.");
//                System.out.println("updateListener debug test2");
//                 notify the listener that a favorite location has been updated
                if (updateListener != null)
                {
                    updateListener.onFavoriteLocationUpdated();
                }
            }
            System.out.println("deleteLocation didnt work, current location was null");
        });
    }

    @Override
    public void onSelectUpdated(Location location)
    {
        hourlyViewVisible = false;
        updateForm(location);
    }
}
