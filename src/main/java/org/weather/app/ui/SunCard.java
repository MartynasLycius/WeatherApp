package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.weather.app.config.Constants;
import org.weather.app.config.Utils;
import org.weather.app.service.dto.pojo.SunModel;

public class SunCard extends Div {

  private final VerticalLayout mainLayout = new VerticalLayout();
  private final SunModel sunModel;

  public SunCard(SunModel sunModel) {
    this.sunModel = sunModel;
    Utils.applyCommonStyles(this);
    initMainLayout();
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("SunCard-extends-Div");
    mainLayout.setId("SunCard-main");
    mainLayout.setHeightFull();
    mainLayout.setAlignItems(FlexComponent.Alignment.START);
    mainLayout.add(new CardHeader(Constants.SUN, "Sun rise and set time"));
    mainLayout.add(buildSunBody());
  }

  private VerticalLayout buildSunBody() {
    VerticalLayout body = new VerticalLayout();
    Image raiseIcon = Utils.buildIcon(Constants.SUN_RAISE, "sun raise");
    Image setIcon = Utils.buildIcon(Constants.SUN_SET, "sun set");
    HorizontalLayout rise =
        buildRow(raiseIcon, Utils.getDateStringWithAmPm(sunModel.getSunRiseAt()));
    HorizontalLayout set = buildRow(setIcon, Utils.getDateStringWithAmPm(sunModel.getSunSetAt()));

    body.add(rise, set);
    return body;
  }

  private HorizontalLayout buildRow(Image image, String time) {
    HorizontalLayout row = new HorizontalLayout();
    row.add(image, new H4(time));
    return row;
  }
}
