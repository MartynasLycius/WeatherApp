package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.weather.app.Utilities.Constraints;
import org.weather.app.models.HourlyRain;

import java.util.List;

public class CurrentRainForecastComponent {

    private VerticalLayout rainForecastLayout;

    public CurrentRainForecastComponent() {
        rainForecastLayout = new VerticalLayoutBuilder()
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START)
                .setAlignItems(FlexComponent.Alignment.START)
                .build();
    }

    public void fillRainForecast(List<HourlyRain> hourlyRainList, String dayNight) {

        Div currentRainForeCastTitleDiv = ComponentUtils.componentsInDiv("current-location-text", new Text("Forecast Rain Hourly"));

        HorizontalLayout contentLayout = createContentLayout();

        try {
            if (null != hourlyRainList && !hourlyRainList.isEmpty()) {
                for (HourlyRain hourlyRain : hourlyRainList) {
                    VerticalLayout hourlyRainForecastLayout = createRawForecastLayout();

                    //day-night
                    String iconSrc = Constraints.rainForecastIconMap(hourlyRain.getRain(), dayNight);
                    Image rainImage = ComponentUtils.createImage(iconSrc, "rain", "total rain", "100px", "100px", null);

                    Div quantityDiv = ComponentUtils.componentsInDiv("default-text", new Text(hourlyRain.getRain() + ""));
                    Div timeDiv = ComponentUtils.componentInDiv(new Text(hourlyRain.getTime()), "default-text", "color-orange", "padding-20");
                    hourlyRainForecastLayout.add(rainImage, quantityDiv, timeDiv);

                    contentLayout.add(hourlyRainForecastLayout);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        rainForecastLayout.add(currentRainForeCastTitleDiv, contentLayout);
    }

    public VerticalLayout getRainForecastLayout() {
        return rainForecastLayout;
    }

    private HorizontalLayout createContentLayout(){
        HorizontalLayout layout = new HorizontalLayoutBuilder()
                .setBackgroundColor("")
                .setWidth("100%")
                .build();
        layout.getElement().getStyle().set("overflow-x", "auto");
        layout.getElement().getStyle().set("white-space", "nowrap");
        return layout;
    }

    private VerticalLayout createRawForecastLayout(){
        return new VerticalLayoutBuilder()
                .setClassNames("hourly-rain-forecast", "padding-20")
                .setBackgroundColor("")
                .setAlignItems(FlexComponent.Alignment.CENTER)
                .setWidth("auto")
                .build();
    }
}
