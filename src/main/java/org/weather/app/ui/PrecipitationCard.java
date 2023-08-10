package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;

public class PrecipitationCard extends Div {

  Double precipitation;
  VerticalLayout mainLayout = new VerticalLayout();

  public PrecipitationCard(Double precipitation) {
    this.precipitation = precipitation;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("PrecipitationCard-extends-Div");
    mainLayout.setId("PrecipitationCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.PRECIPITATION, "Precipitation"));
    buildPrecipitationBody();
  }

  private void buildPrecipitationBody() {
    ProgressBar progressBar = new ProgressBar();
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(precipitation);
    Div progressBarLabelText = new Div();
    progressBarLabelText.setText(String.format("Precipitation is %s %%", precipitation));
    FlexLayout progressBarLabel = new FlexLayout();
    progressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    progressBarLabel.add(progressBarLabelText);
    mainLayout.add(progressBarLabel, progressBar);
  }

  private void initStyles() {
    addClassNames(LumoUtility.Background.BASE, LumoUtility.BorderRadius.LARGE);
    setHeight("400px");
    getStyle().set("margin", "5px");
  }
}
