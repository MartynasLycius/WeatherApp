package org.weather.app.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CardHeader extends HorizontalLayout {

  public CardHeader(String imageUrl, String title) {
    setId("card-header-extends-HorizontalLayout");
    Span cardTitle = new Span(title);
    cardTitle.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.TextColor.SECONDARY);
    Image image = new Image(imageUrl, title);
    image.setHeight("24px");
    image.setWidth("24px");
    setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    setWidthFull();
    add(cardTitle);
    add(image);
  }
}
