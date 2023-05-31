package com.proit.application.views.common;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.service.UserService;
import com.proit.application.service.WeatherDataService;
import com.proit.application.views.forecast.DailyWeatherForecastView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;

public abstract class AbstractLocationView extends VerticalLayout {
    protected final WeatherDataService weatherDataService;
    protected final UserService userService;
    protected final Grid<LocationDto> grid;
    protected GridListDataView<LocationDto> dataView;
    protected DailyWeatherForecastView dailyWeatherForecastView;
    protected final TextField gridFilterTextField;

    public AbstractLocationView(WeatherDataService weatherDataService, UserService userService) {
        this.weatherDataService = weatherDataService;
        this.userService = userService;

        this.grid = new Grid<>(LocationDto.class, false);
        this.gridFilterTextField = new TextField();
        configureWeatherForecastView();
        configureGrid();
        setSizeFull();
    }

    protected void configureWeatherForecastView() {
        dailyWeatherForecastView = new DailyWeatherForecastView();
        dailyWeatherForecastView.setSizeFull();
    }

    protected Component getContent() {
        var content = new HorizontalLayout(grid, dailyWeatherForecastView);
        content.setWidthFull();
        content.setFlexGrow(1, grid);
        content.setFlexGrow(2, dailyWeatherForecastView);

        content.setClassName("content");
        content.setSizeFull();

        return content;
    }

    protected void configureGrid() {
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        configureGridColumns();

        configureGridFilterTextField();

        var headerRow = grid.appendHeaderRow();
//        headerRow.getCell(grid.getColumnByKey("name")).setComponent(gridFilterTextField);
        headerRow.getCell(grid.getColumnByKey("name"))
                .setComponent(gridFilterTextField);

        grid.asSingleSelect().addValueChangeListener(e -> openWeatherForecastViewFor(e.getValue()));
    }

    private void configureGridFilterTextField() {
        gridFilterTextField.setPlaceholder("Filter by name");
        gridFilterTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        gridFilterTextField.setValueChangeMode(ValueChangeMode.EAGER);
        gridFilterTextField.addValueChangeListener(e -> onGridFilterTextFieldValueChange(e.getValue()));
    }

    protected void openWeatherForecastViewFor(LocationDto location) {
        if (!userService.isUserLoggedIn()) {
            ViewNotificationUtils.showErrorNotification("Please login to view weather forecast");
            return;
        }

        if (location == null) {
            dailyWeatherForecastView.closeView();
            return;
        }

        var data = weatherDataService.getWeatherData(location.getLatitude(), location.getLongitude());
        dailyWeatherForecastView.setLocation(location);
        dailyWeatherForecastView.setVisibility(true, data);
    }


    protected void updateGridItems(List<LocationDto> locations) {
        dataView = grid.setItems(locations);
    }

    protected abstract void configureGridColumns();
    protected abstract void onGridFilterTextFieldValueChange(String value);
}
