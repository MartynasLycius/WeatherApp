package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.config.AppRoutes;
import com.waheduzzaman.MeteoWeather.data.entity.FavouriteLocation;
import com.waheduzzaman.MeteoWeather.interfaces.AddClickListener;
import com.waheduzzaman.MeteoWeather.service.impl.FavouriteLocationServiceImpl;

public class FavouriteLocationGridWidget extends Div {
    private FavouriteLocationServiceImpl favouriteLocationService;
    private Grid<FavouriteLocation> favouriteLocationGrid;
    private AddClickListener clickListener;

    public FavouriteLocationGridWidget(FavouriteLocationServiceImpl favouriteLocationService, AddClickListener clickListener) {
        initObjects(favouriteLocationService, clickListener);
        setSizeFull();
        initLayout();
        bindData();
    }

    public void reloadData() {
        bindData();
    }

    public void assertUserCanContinue() {
        if (favouriteLocationService.countOfFavouriteLocations() == 0) {
            AppDialog dialog = new AppDialog();
            dialog.setTitleLabel("Oops, Nothing to Show!");
            dialog.setMessage("Location permission is required to show current location weather or search and save locations to view details");
            dialog.setCancelable(false);
            dialog.setShowConfirmButton(true);
            dialog.setConfirmButtonLabel("Okay");
            dialog.setConfirmClickListener(new AddClickListener() {
                @Override
                public <T> void onItemClick(T item) {
                    System.out.println("going back");
                    AppRoutes.goBack();
                }
            });
            dialog.show();
        }
    }

    private void initObjects(FavouriteLocationServiceImpl favouriteLocationService, AddClickListener clickListener) {
        this.favouriteLocationGrid = new Grid<>(FavouriteLocation.class);
        this.favouriteLocationService = favouriteLocationService;
        this.clickListener = clickListener;
    }

    private void initLayout() {
        favouriteLocationGrid = new Grid<>(FavouriteLocation.class);
        favouriteLocationGrid.setSizeFull();
        favouriteLocationGrid.setColumns();
        favouriteLocationGrid.addColumn(renderDoubleLineRowItem()).setHeader("Favourite Locations");
        favouriteLocationGrid.addColumn(getDeleteIconRenderer()).setHeader("");
        favouriteLocationGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        favouriteLocationGrid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        favouriteLocationGrid.setClassName(LumoUtility.Border.NONE);
        favouriteLocationGrid.getColumns().forEach(col -> {
            col.setAutoWidth(true);
        });
        favouriteLocationGrid.getElement().setAttribute("hide-header-row", true);
        favouriteLocationGrid.asSingleSelect().addValueChangeListener(event -> {
            clickListener.onItemClick(event.getValue());
        });
        add(favouriteLocationGrid);
    }

    private void bindData() {
        favouriteLocationGrid.setItems(favouriteLocationService.findAllFavouriteLocationsForLoggedInUser());
    }

    private static Renderer<FavouriteLocation> renderDoubleLineRowItem() {
        return LitRenderer.<FavouriteLocation>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.name} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.country}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("name", FavouriteLocation::getName)
                .withProperty("country", FavouriteLocation::getCountry);
    }

    private ComponentRenderer<Icon, FavouriteLocation> getDeleteIconRenderer() {
        return new ComponentRenderer<>(favouriteLocation -> {
            if (!favouriteLocation.getName().equalsIgnoreCase("current location")) {
                final Icon delIcon = new Icon("lumo", "cross");
                delIcon.getElement().addEventListener("click",
                                event -> removeFavouriteLocation(favouriteLocation))
                        .addEventData("event.stopPropagation()");
                return delIcon;
            }
            return null;
        });
    }

    private void removeFavouriteLocation(FavouriteLocation location) {
        favouriteLocationService.removeFavouriteLocationById(location.getId());
        assertUserCanContinue();
        favouriteLocationGrid.getDataProvider().refreshAll();
        favouriteLocationGrid.select(favouriteLocationGrid.getGenericDataView().getItem(0));
    }
}
