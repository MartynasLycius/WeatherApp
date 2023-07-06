package com.waheduzzaman.MeteoWeather.data.model.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TemperatureCardViewModel {
    Double temperatureHigh, temperatureLow;
    String unit;
}
