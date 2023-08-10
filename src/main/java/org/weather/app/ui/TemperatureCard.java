package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;
import org.weather.app.service.dto.pojo.TemperatureModel;

public class TemperatureCard extends Div {

  private final TemperatureModel temperatureModel;
  private final VerticalLayout mainLayout = new VerticalLayout();

  public TemperatureCard(TemperatureModel temperatureModel) {
    this.temperatureModel = temperatureModel;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("TemperatureCard-extends-Div");
    mainLayout.setId("TemperatureCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.TEMPERATURE, "Temperature"));
    mainLayout.add(buildTemperatureBody());
  }

  private VerticalLayout buildTemperatureBody() {
    VerticalLayout body = new VerticalLayout();
    body.setId("temperature-body");
    body.setHeightFull();
    body.setMargin(false);
    body.setPadding(false);
    body.setSpacing(false);
    body.setWidthFull();
    body.add(buildTemperatureRow());
    body.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    return body;
  }

  private HorizontalLayout buildTemperatureRow() {
    HorizontalLayout row = new HorizontalLayout();
    row.setId("temperature-row");
    row.setAlignItems(FlexComponent.Alignment.CENTER);
    row.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

    Image upIcon = Utils.buildIcon(Constants.HIGH, "up");
    Image downIcon = Utils.buildIcon(Constants.LOW, "down");

    HorizontalLayout leftSideHighValue =
        buildTemperature(
            temperatureModel.getHigh(),
            upIcon,
            LumoUtility.FontSize.XXXLARGE,
            LumoUtility.FontWeight.EXTRABOLD);

    HorizontalLayout rightSideLowValue =
        buildTemperature(
            temperatureModel.getLow(),
            downIcon,
            LumoUtility.FontSize.XLARGE,
            LumoUtility.FontWeight.BOLD);

    leftSideHighValue.setId("temperature-high");
    rightSideLowValue.setId("temperature-low");
    row.add(leftSideHighValue, rightSideLowValue);
    row.setWidthFull();
    return row;
  }

  private HorizontalLayout buildTemperature(
      Double temperatureValue, Image image, String... styles) {
    HorizontalLayout temperature = new HorizontalLayout();
    Span valueSpan =
        new Span(String.format("%s%s", temperatureValue.toString(), temperatureModel.getUnit()));
    valueSpan.addClassNames(styles);
    temperature.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, image, valueSpan);
    temperature.add(image, valueSpan);
    return temperature;
  }
}
        /*
        div
          vertical-layout
            horizontalLayout
              span + image
            verticalLayout
              horizontalLayout
                horizontalLayout
                   icon + span
                horizontalLayout
                   icon + span

        */
