package com.eastnetic.task.views.pages;

import com.eastnetic.task.model.entity.UserFavLocations;
import com.eastnetic.task.model.dto.*;
import com.eastnetic.task.service.ForecastService;
import com.eastnetic.task.service.LocationService;
import com.eastnetic.task.service.UsersService;
import com.eastnetic.task.utils.DateUtils;
import com.eastnetic.task.utils.WeatherCodeMappingUtils;
import com.eastnetic.task.views.layout.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

@PageTitle("Weather Forecast")
@Route(value = "location", layout = MainLayout.class)
@PermitAll
@Slf4j
public class LocationView extends VerticalLayout implements HasUrlParameter<Integer> {

    LocationService locationService;
    ForecastService forecastService;
    UsersService usersService;
    VerticalLayout gridLayout;
    Dialog dialog;
    HorizontalLayout searchHorizontalLayout;
    Button favButton;
    Button clearButton;
    String favIdFromUrl;
    TextField searchByCityField;

    /**
     * Main forecast page view create
     * @param locationService, usersService, forecastService
     * @return
     * @throws
     */
    public LocationView(UsersService usersService, LocationService locationService, ForecastService forecastService) {
        this.usersService = usersService;
        this.locationService = locationService;
        this.forecastService = forecastService;
        searchHorizontalLayout = new HorizontalLayout();

        createSearchLayout();
    }

    private void createSearchLayout() {
        dialog = new Dialog();

        searchByCityField = new TextField();
        searchByCityField.setPlaceholder("Enter a City");
        searchByCityField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchByCityField.setWidth("400px");
        searchByCityField.setClearButtonVisible(true);
        searchByCityField.addValueChangeListener(event->{
            if(searchByCityField.getValue().trim().isEmpty()){
                clearLayout();
            }
        });

        Button button = new Button("Search");
        button.addClickShortcut(Key.ENTER);
        button.addClickListener(clickEvent -> {
            gridLayout = new VerticalLayout();
            String cityName = searchByCityField.getValue().trim();
            if (!cityName.isEmpty()) {
                dialog.close();
                dialog = new Dialog();
                try{
                    createGridLayout(cityName);
                } catch (Exception e) {
                    showErrorDialog();
                }

                dialog.add(gridLayout);
                dialog.setHeaderTitle("Please select a city from search results: ");
                Button closeButton = new Button(new Icon("lumo", "cross"),
                        (e) -> dialog.close());
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                dialog.getHeader().add(closeButton);
                dialog.setWidth("40%");
                dialog.setResizable(true);
                dialog.setDraggable(true);
                dialog.open();
                add(dialog);
            }
        });

        searchHorizontalLayout.add(searchByCityField, button);
        add(searchHorizontalLayout);
    }

    /**
     * grid layout create for location search data
     * @param cityName
     * @return
     * @throws
     */
    private void createGridLayout(String cityName){
        gridLayout = new VerticalLayout();
        Grid<LocationResults> grid = new Grid<>(LocationResults.class, false);
        grid.addColumn(createDataRenderer()).setFlexGrow(0).setWidth("100%");

        grid.addSelectionListener(selection -> {
            Optional<LocationResults> optionalLocationResults = selection.getFirstSelectedItem();
            if (optionalLocationResults.isPresent()) {
                dialog.close();
                callWeatherForecast(optionalLocationResults.get());
            }
        });

        List<LocationResults> locations = getLocations(cityName);

        if (locations != null && locations.size() > 0){
            GridListDataView<LocationResults> dataView = grid.setItems(locations);

            TextField searchField = new TextField();
            searchField.setWidth("60%");
            searchField.setPlaceholder("Filter by Name, Region or Country");
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
                return matchesName || matchesRegion || matchesCountry;
            });
            grid.setPageSize(10);
            gridLayout.add(searchField, grid);
        } else {
            Div emptyGridMessage = new Div(new Icon(VaadinIcon.WARNING), new Text("No data found for \"" + cityName + "\""));
            gridLayout.add(emptyGridMessage);
        }
    }

    /**
     * Get location search data
     * @param cityName
     * @return List<LocationResults>
     * @throws
     */
    private List<LocationResults> getLocations(String cityName){
        try {
            return  locationService.getLocations(cityName).getResults();
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * data renderer for location grid view
     * @param
     * @return Renderer
     * @throws
     */
    private static Renderer<LocationResults> createDataRenderer() {
        return LitRenderer.<LocationResults> of(
                "<vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "<vaadin-horizontal-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "  <span>${item.name}</span> &nbsp;&nbsp;"
                                + "  <span style=\"color: var(--lumo-secondary-text-color);\"> ${item.region?item.region+', ':''} ${item.country}</span>"
                                + "</vaadin-horizontal-layout>"
                                + "  <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">${item.lat}&deg;E ${item.long}&deg;N ${item.elev}m asl; Timezone: ${item.timezone}</span>"
                        + "</vaadin-vertical-layout>")
                .withProperty("name", LocationResults::getName)
                .withProperty("region", LocationResults::getAdmin1)
                .withProperty("country", LocationResults::getCountry)
                .withProperty("lat", LocationResults::getLatitude)
                .withProperty("long", LocationResults::getLongitude)
                .withProperty("elev", LocationResults::getElevation)
                .withProperty("timezone", LocationResults::getTimezone);
    }

    /**
     * forecast details layout create
     * @param results
     * @return
     * @throws
     */
    void callWeatherForecast(LocationResults results) {
        if(results != null){
            ForecastDTO forecastDTO = this.getForecast(results);
            if (forecastDTO != null) {
                VerticalLayout forecastVerticalLayout = new VerticalLayout();
                HorizontalLayout forecastHorizontalLayout = new HorizontalLayout();
                H3 selectedCitySpan = new H3(results.getName() + ", " + results.getCountry());
                favButton = new Button("Mark as Favorite", new Icon(VaadinIcon.HEART));
                favButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
                favButton.addClickListener(e -> this.addToFavorites(results));
                clearButton = new Button("Clear Result", new Icon(VaadinIcon.CLOSE));
                clearButton.getElement().getStyle().set("margin-left", "auto");
                clearButton.addClickListener(e -> this.clearLayout());
                if(this.isSavedLocation(results)){
                    favButton.setEnabled(false);
                }

                HorizontalLayout selectedCityLayout = new HorizontalLayout(selectedCitySpan, clearButton, favButton);
                selectedCityLayout.setWidthFull();

                HorizontalLayout currentTimeLayout = new HorizontalLayout();
                currentTimeLayout.setWidth("60%");
                currentTimeLayout.setPadding(false);
                Span currentTimeSpan = new Span(DateUtils.getCurrentFullDateString());
                VerticalLayout currentTimeVerticalLayout = new VerticalLayout(currentTimeSpan);
                currentTimeVerticalLayout.setPadding(false);
                H4 currentMsg = new H4("Current Weather");
                currentMsg.getElement().getThemeList().add("badge contrast");
                VerticalLayout currentTextVerticalLayout = new VerticalLayout(currentMsg);
                currentTextVerticalLayout.setPadding(false);
                currentTimeLayout.add(currentTimeVerticalLayout, currentTextVerticalLayout);
                Hourly hourly = forecastDTO.getHourly();
                HourlyUnits hourlyUnits = forecastDTO.getHourlyUnits();

                int timeIndex = 0;
                for (String hours: hourly.getTime()) {
                    LocalDateTime currTime = LocalDateTime.now();
                    LocalDateTime getTime = LocalDateTime.parse(hours);
                    if(currTime.isBefore(getTime)){
                        timeIndex = hourly.getTime().indexOf(hours);
                        break;
                    }
                }

                HorizontalLayout currentTempLayout = new HorizontalLayout();
                Span currentTemp = new Span(hourly.getTemperature2m().get(timeIndex).toString() + hourlyUnits.getTemperature2m());
                VerticalLayout weatherCodeLayout = new VerticalLayout();
                H5 weatherCond = new H5(WeatherCodeMappingUtils.getCurrentWeatherCodeDescription(hourly.getWeathercode().get(timeIndex)));
                weatherCond.getElement().getThemeList().add("badge");
                weatherCodeLayout.add(new Html("<img src=\"" + WeatherCodeMappingUtils.getCurrentWeatherCodeImage(hourly.getWeathercode().get(timeIndex)) + "\" alt=\"\">"),
                        weatherCond);
                weatherCodeLayout.setPadding(false);
                weatherCodeLayout.setSpacing(false);
                currentTempLayout.add(weatherCodeLayout, currentTemp);
                currentTempLayout.addClassName("current-temp-style");
                currentTempLayout.setAlignItems(Alignment.CENTER);
                forecastVerticalLayout.add(currentTempLayout);
                forecastVerticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
                forecastVerticalLayout.setPadding(false);
                VerticalLayout dailyForecastDetailsLayout = showCurrentForecastDetails(forecastDTO, timeIndex);
                dailyForecastDetailsLayout.setPadding(false);
                forecastHorizontalLayout.add(forecastVerticalLayout, dailyForecastDetailsLayout);
                forecastHorizontalLayout.setWidth("60%");
                forecastHorizontalLayout.setPadding(false);
                HorizontalLayout dailyDataLayout = showAllDayForecastDetails(forecastDTO);
                removeAll();
                add(searchHorizontalLayout, selectedCityLayout, currentTimeLayout, forecastHorizontalLayout, dailyDataLayout);
            }
        }
    }

    /**
     * Get forecast data from service
     * @param results
     * @return ForecastDTO
     * @throws
     */
    private ForecastDTO getForecast(LocationResults results){
        try {
            return this.forecastService.getWeatherForecasts(results.getLatitude(), results.getLongitude(), results.getTimezone());
        } catch(Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * checks if location is saved to favorites list
     * @param results
     * @return boolean
     * @throws
     */
    private boolean isSavedLocation(LocationResults results){
        try {
            return locationService.isSavedLocation(results, this.usersService.getCurrentUser().getId());
        } catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * Clear layout to main view
     * @param
     * @return
     * @throws
     */
    private void clearLayout() {
        removeAll();
        searchByCityField.clear();
        add(searchHorizontalLayout);
    }

    /**
     * Layout create for current forecast
     * @param forecastDTO
     * @return Component
     * @throws
     */
    private VerticalLayout showCurrentForecastDetails(ForecastDTO forecastDTO, int timeIndex) {
        VerticalLayout detailsLayout = new VerticalLayout();
        Hourly hourly = forecastDTO.getHourly();
        HourlyUnits hourlyUnits = forecastDTO.getHourlyUnits();
        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.add(new Span("Humidity"),
                new Span("Rain"),
                new Span("Wind Speed"));
        VerticalLayout valueLayout = new VerticalLayout();
        valueLayout.add(new Span(hourly.getRelativehumidity2m().get(timeIndex).toString() + hourlyUnits.getRelativehumidity2m()),
                new Span(hourly.getRain().get(timeIndex).toString() + " " + hourlyUnits.getRain()),
                new Span(hourly.getWindspeed10m().get(timeIndex).toString() + " " + hourlyUnits.getWindspeed10m())
        );
        HorizontalLayout horizontalLayout = new HorizontalLayout(labelLayout, valueLayout);
        horizontalLayout.setWidth("80%");
        horizontalLayout.setPadding(false);
        detailsLayout.add(horizontalLayout);
        detailsLayout.addClassName("daily-details-style");
        return detailsLayout;
    }

    /**
     * Layout create for current day forecast
     * @param forecastDTO
     * @return Component
     * @throws
     */
    private HorizontalLayout showAllDayForecastDetails(ForecastDTO forecastDTO) {
        TabSheet tabs = new TabSheet();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        int index = 0;
        for (Date date: forecastDTO.getDaily().getTime()){
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.add(new Span(DateUtils.getDayFromDateToString(date)),
                    new Span(DateUtils.getMonthDayFromDateToString(date)));
            verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            VerticalLayout contentLayout = new VerticalLayout(showDailyForecastDetails(forecastDTO, index), getHourlyData(forecastDTO, date));
            tabs.add(verticalLayout, contentLayout);
            index++;
        }
        horizontalLayout.add(tabs);
        horizontalLayout.setWidthFull();
        return horizontalLayout;
    }

    /**
     * Layout create for daily forecast
     * @param forecastDTO
     * @return Component
     * @throws
     */
    private VerticalLayout showDailyForecastDetails(ForecastDTO forecastDTO, int dateIndex) {
        VerticalLayout detailsLayout = new VerticalLayout();
        Daily daily = forecastDTO.getDaily();
        DailyUnits dailyUnits = forecastDTO.getDailyUnits();
        VerticalLayout labelLayout = new VerticalLayout();
        labelLayout.add(new Span("Condition"),
                new Span("Temperature (Max/Min)"),
                new Span("Rain"),
                new Span("Max Precipitation")
                );
        VerticalLayout labelLayout2 = new VerticalLayout();
        labelLayout2.add(
                new Span("Wind Speed"),
                new Span("Wind Gusts"),
                new Span("Sunrise"),
                new Span("Sunset")
        );
        VerticalLayout valueLayout = new VerticalLayout();
        Span weatherCond = new Span(new Span(WeatherCodeMappingUtils.getDailyWeatherCodeDescription(daily.getWeathercode().get(dateIndex))));
        weatherCond.getElement().getThemeList().add("badge");
        weatherCond.getStyle().set("margin-bottom", "-10px");
        valueLayout.add(weatherCond,
                new Span(daily.getTemperature2mMax().get(dateIndex).toString() + dailyUnits.getTemperature2mMax() + " / "
                        + daily.getTemperature2mMin().get(dateIndex).toString() + dailyUnits.getTemperature2mMin()),
                new Span(daily.getRainSum().get(dateIndex).toString() + " " + dailyUnits.getRainSum()),
                new Span(daily.getPrecipitationProbabilityMax().get(dateIndex).toString() + dailyUnits.getPrecipitationProbabilityMax())
        );
        VerticalLayout valueLayout2 = new VerticalLayout();
        valueLayout2.add(
                new Span(daily.getWindspeed10mMax().get(dateIndex).toString() + " " + dailyUnits.getWindspeed10mMax()),
                new Span(daily.getWindgusts10mMax().get(dateIndex).toString() + " " + dailyUnits.getWindgusts10mMax()),
                new Span(DateUtils.getTimeFromDateToString(daily.getSunrise().get(dateIndex))),
                new Span(DateUtils.getTimeFromDateToString(daily.getSunset().get(dateIndex)))
        );
        HorizontalLayout horizontalLayout = new HorizontalLayout(labelLayout, valueLayout, labelLayout2, valueLayout2);
        horizontalLayout.setWidthFull();
        detailsLayout.add(horizontalLayout);
        detailsLayout.addClassName("daily-details-style");
        return detailsLayout;
    }

    /**
     * Layout create for hourly forecast
     * @param forecastDTO, Date
     * @return Component
     * @throws
     */
    private Component getHourlyData(ForecastDTO forecastDTO, Date dateDaily) {

        Hourly hourly = forecastDTO.getHourly();
        HourlyUnits hourlyUnits =forecastDTO.getHourlyUnits();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("1100px");
        VerticalLayout hourlyLabelLayout = new VerticalLayout();
        Span imageLabel = new Span("");
        imageLabel.setHeight("110px");
        hourlyLabelLayout.add(new Span("Time"), imageLabel, new Span("Temp"), new Span("Rain"), new Span("Wind"));
        hourlyLabelLayout.setWidth("70px");
        hourlyLabelLayout.addClassName("hourly-details-style");
        horizontalLayout.add(hourlyLabelLayout);
        horizontalLayout.getStyle().set("border", "1px solid");
        int index = 0;
        HorizontalLayout hourlyHorizontalLayout = new HorizontalLayout();
        hourlyHorizontalLayout.getStyle().set("overflow", "auto");
        try {
            for(String time: hourly.getTime()){
                String dateDailyStr = DateUtils.convertDateToString(dateDaily);
                String dateHourlyStr = DateUtils.convertTimeToString(time);
                if(dateDailyStr.equals(dateHourlyStr)){
                    Span timeSpan = new Span(DateUtils.getHourFromDateToString(time));
                    timeSpan.setWidth("60px");
                    timeSpan.getElement().getThemeList().add("badge contrast");

                    VerticalLayout hourlyDataLayout = new VerticalLayout();
                    hourlyDataLayout.addClassName("hourly-details-style");

                    //Span condSpan = new Span(WeatherCodeMappingUtils.getWeatherCodeDescription(hourly.getWeathercode().get(index), DateUtils.getHour24FromStringToInt(time)));
                    Span imageSpan = new Span(new Html("<img src=\"" + WeatherCodeMappingUtils.getWeatherCodeImage(hourly.getWeathercode().get(index), DateUtils.getHour24FromStringToInt(time)) + "\" alt=\"\">"));
                    imageSpan.setWidth("60px");
                    imageSpan.getStyle().set("margin-left","-25px");
                    Span tempSpan = new Span(hourly.getTemperature2m().get(index).toString() + hourlyUnits.getTemperature2m());
                    tempSpan.setWidth("60px");
                    Span rainSpan = new Span(hourly.getRain().get(index).toString() + " " + hourlyUnits.getRain());
                    rainSpan.setWidth("60px");
                    Span windpan = new Span(hourly.getWindspeed10m().get(index).toString() + " " + hourlyUnits.getWindspeed10m());
                    windpan.setWidth("60px");
                    hourlyDataLayout.add(timeSpan, imageSpan, tempSpan, rainSpan, windpan);
                    hourlyHorizontalLayout.add(hourlyDataLayout);
                }
                index++;
            }
            horizontalLayout.add(hourlyHorizontalLayout);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return horizontalLayout;
    }

    /**
     * Add to favorites list through service
     * @param results
     * @return
     * @throws
     */
    private void addToFavorites(LocationResults results) {
        Notification notification;
        try{
            locationService.saveFavorites(results);
            favButton.setEnabled(false);
            notification = Notification.show("Added to Favorites list.");
            notification.setPosition(Notification.Position.MIDDLE);
        } catch (Exception e){
            if(locationService.isSavedLocation(results, this.usersService.getCurrentUser().getId())){
                notification = Notification.show("Already added to Favorites list.");
            } else {
                notification = Notification.show("Failed to add to Favorites list. Please try again.");
            }
            notification.setPosition(Notification.Position.MIDDLE);
        }
    }

    /**
     * Match data for grid filtering
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
     * Get query param using before enter event for favorite location's forecast show
     * @param event, id
     * @return
     * @throws
     */
    @Override // HasUrlParameter interface
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Integer id) {
        if(event.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("fav")) {
            this.favIdFromUrl = event.getLocation()
                    .getQueryParameters()
                    .getParameters().get("fav").get(0);
            if(this.favIdFromUrl != null){
                UserFavLocations favResults = locationService.getFavoritesById(this.favIdFromUrl);
                if (favResults != null){
                    LocationResults locationResults = new LocationResults();
                    locationResults.setName(favResults.getName());
                    locationResults.setAdmin1(favResults.getRegion());
                    locationResults.setCountry(favResults.getCountry());
                    locationResults.setLatitude(Double.parseDouble(favResults.getLatitude()));
                    locationResults.setLongitude(Double.parseDouble(favResults.getLongitude()));
                    locationResults.setTimezone(favResults.getTimezone());
                    clearLayout();
                    try{
                        callWeatherForecast(locationResults);
                    } catch (Exception e) {
                        showErrorDialog();
                    }
                }
            } else {
                add(searchHorizontalLayout);
            }

        }
    }


    public void showErrorDialog() {
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label",
                "System Error");

        VerticalLayout dialogLayout = createErrorDialogLayout(dialog);
        dialog.add(dialogLayout);
        dialog.open();
        add(dialog);
    }

    private static VerticalLayout createErrorDialogLayout(Dialog dialog) {
        H2 headline = new H2("Error");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");

        Paragraph paragraph = new Paragraph(
                "Service not responding. Please check your internet connection or contact administrator.");

        Button closeButton = new Button("Close");
        closeButton.addClickListener(e -> dialog.close());

        VerticalLayout dialogLayout = new VerticalLayout(headline, paragraph,
                closeButton);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);

        return dialogLayout;
    }

}
