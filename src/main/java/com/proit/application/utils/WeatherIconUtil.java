package com.proit.application.utils;

import java.util.List;

public final class WeatherIconUtil {
    private static final String ICON_ROOT_PATH = "icons/animated/";
    private WeatherIconUtil() {
    }

    public static String getWeatherIcon(int weatherCode) {
        List<String> weatherIcons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);
        String icon = weatherIcons.get(0);
        return ICON_ROOT_PATH + icon;
    }

    public static String getWeatherIconBasedOnTime(int weatherCode, String time, String sunrise, String sunset) {
        List<String> weatherIcons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);

        String icon = weatherIcons.get(0);
        if(weatherIcons.size() < 2) {
            return ICON_ROOT_PATH + icon;
        }

        if (!DateTimeUtil.isDateInBetweenTwoDates(time, sunrise, sunset)) {
            icon = weatherIcons.get(1);
        }

        return ICON_ROOT_PATH + icon;
    }
}
