package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.interfaces.AddClickListener;

public class IconTextBoxWidget extends HorizontalLayout {
    private AddClickListener clickListener;
    private String label;
    private String icon;

    Icon searchIcon;
    H6 searchLabel;

    public IconTextBoxWidget(String label, String icon, AddClickListener clickListener) {
        initObjects(label, icon, clickListener);
        initLayout();
        setIcon();
        setLabel();
        setVerticalComponentAlignment(Alignment.CENTER, searchIcon, searchLabel);
        setAlignItems(Alignment.START);
    }

    private void initLayout() {
        setWidthFull();
        addClassNames(
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Padding.MEDIUM);
    }

    private void initObjects(String label, String icon, AddClickListener clickListener) {
        this.label = label;
        this.icon = icon;
        this.clickListener = clickListener;
    }

    private void setLabel() {
        searchLabel = new H6(label);
        searchLabel.setClassName(LumoUtility.FontSize.MEDIUM);
        searchLabel.setClassName(LumoUtility.TextColor.DISABLED);
        add(searchLabel);
        addClickListener(event -> {
            clickListener.onItemClick(null);
        });
    }

    private void setIcon() {
        searchIcon = new Icon("lumo", icon);
        searchIcon.setColor("grey");
        searchIcon.setSize(LumoUtility.IconSize.SMALL);
        add(searchIcon);
    }
}
