package com.proit.application.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherIconProperties {
    private static String iconRootPath;

    public WeatherIconProperties(@Value("${icon.root.path}") String iconRootPath) {
        WeatherIconProperties.iconRootPath = iconRootPath;
    }

    public static String getIconRootPath() {
        return iconRootPath;
    }
}
