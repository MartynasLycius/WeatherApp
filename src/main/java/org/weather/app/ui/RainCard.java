package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;
import org.weather.app.service.dto.pojo.RainModel;

public class RainCard extends Div {

  private final RainModel rainModel;
  private final VerticalLayout mainLayout = new VerticalLayout();

  public RainCard(RainModel rainModel) {
    this.rainModel = rainModel;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("RainCard-extends-Div");
    mainLayout.setId("RainCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.RAIN, "Rain fall"));
    mainLayout.add(buildRainBody());
  }

  private VerticalLayout buildRainBody() {
    VerticalLayout body = new VerticalLayout();
    Span rainFallLabel = new Span(String.format("%s %s", rainModel.getSum(), rainModel.getUnit()));
    rainFallLabel.addClassNames(LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.EXTRABOLD);
    body.add(rainFallLabel);
    return body;
  }
}
