package com.eastnetic.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

public class FavoriteIconButton extends Button {

    private boolean isFavorite = false;

    public FavoriteIconButton() {
        setIcon(VaadinIcon.STAR.create());
        addClickListener(event -> toggleFavorite());
        updateFavoriteIconStyle();
    }

    private void toggleFavorite() {

        isFavorite = !isFavorite;

        if (isFavorite()) {
            deleteFavorite();
        } else {
            markFavorite();
        }
        updateFavoriteIconStyle();
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void markFavorite() {

    }

    public void deleteFavorite() {

    }

    private void updateFavoriteIconStyle() {

        boolean isFavorite = isFavorite();

        getStyle().set("padding", "5px");
        getStyle().set("border", "2px solid black");
        getStyle().set("color", isFavorite ? "orange" : "black");
    }
}