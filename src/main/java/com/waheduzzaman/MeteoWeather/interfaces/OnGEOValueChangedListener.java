package com.waheduzzaman.MeteoWeather.interfaces;

import org.vaadin.elmot.flow.sensors.Position;
import org.vaadin.elmot.flow.sensors.PositionErrorEvent;

public interface OnGEOValueChangedListener {
    void onSuccess(Position position);
    void onError(PositionErrorEvent pee);
}
