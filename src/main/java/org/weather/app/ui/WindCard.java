package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;
import org.weather.app.service.dto.pojo.WindModel;

public class WindCard extends Div {

  private final WindModel windModel;
  private final VerticalLayout mainLayout = new VerticalLayout();

  public WindCard(WindModel windModel) {
    this.windModel = windModel;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("WindCard-extends-Div");
    mainLayout.setId("WindCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.WIND, "Wind speed"));
    mainLayout.add(buildWindBody());
  }

  private VerticalLayout buildWindBody() {
    VerticalLayout body = new VerticalLayout();
    Span speedLabel = new Span(String.format("%s %s", windModel.getSpeed(), windModel.getUnit()));
    speedLabel.addClassNames(LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.EXTRABOLD);
    body.add(speedLabel);
    return body;
  }
}
