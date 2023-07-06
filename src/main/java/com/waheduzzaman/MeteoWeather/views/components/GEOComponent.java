package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.waheduzzaman.MeteoWeather.interfaces.OnGEOValueChangedListener;
import lombok.Data;
import org.vaadin.elmot.flow.sensors.GeoLocation;

@Data
@SpringComponent
public class GEOComponent {
    private Double longitude;
    private Double latitude;
    private GeoLocation geoLocation;
    private boolean locationAcquired = false;

    public void init() {
        geoLocation = new GeoLocation();
        geoLocation.setWatch(true);
        geoLocation.setHighAccuracy(true);
        geoLocation.setTimeout(100000);
        geoLocation.setMaxAge(200000);
    }

    public GeoLocation getGEOView(OnGEOValueChangedListener listener) {
        init();
        geoLocation.addValueChangeListener(e -> {
            this.locationAcquired = true;
            this.latitude = e.getValue().getLatitude();
            this.longitude = e.getValue().getLongitude();
//            Notification.show("latitude: " + this.latitude).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
//            Notification.show("longitude: " + this.longitude).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            geoLocation.setWatch(false);
            listener.onSuccess(e.getValue());
        });
        geoLocation.addErrorListener(e -> {
            this.locationAcquired = false;
            listener.onError(e);
        });
        return geoLocation;
    }
}
