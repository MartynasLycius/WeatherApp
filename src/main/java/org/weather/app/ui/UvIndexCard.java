package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;

public class UvIndexCard extends Div {

  private final Double uvIndex;
  VerticalLayout mainLayout = new VerticalLayout();

  public UvIndexCard(Double uvIndex) {
    this.uvIndex = uvIndex;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("UvIndexCard-extends-Div");
    mainLayout.setId("UvIndexCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.UV_INDEX, "UV Index"));
    buildUvIndexBody();
  }

  private void buildUvIndexBody() {
    ProgressBar progressBar = new ProgressBar();
    progressBar.setMin(0);
    progressBar.setMax(50);
    progressBar.setValue(uvIndex);
    Div progressBarLabelText = new Div();
    progressBarLabelText.setText(String.format("UV Index is %s %%", uvIndex));
    FlexLayout progressBarLabel = new FlexLayout();
    progressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    progressBarLabel.add(progressBarLabelText);
    mainLayout.add(progressBarLabel, progressBar);
  }
}
