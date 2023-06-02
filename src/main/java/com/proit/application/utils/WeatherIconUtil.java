package com.proit.application.utils;

import java.util.List;

public final class WeatherIconUtil {
    private WeatherIconUtil() {
    }

    public static String getWeatherIcon(int weatherCode) {
        List<String> weatherIcons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);
        String icon = weatherIcons.get(0);
        return getIconFullPath(icon);
    }

    public static String getWeatherIconBasedOnTime(int weatherCode, String time, String sunrise, String sunset) {
        List<String> weatherIcons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);

        String icon = weatherIcons.get(0);
        if(weatherIcons.size() < 2) {
            return getIconFullPath(icon);
        }

        if (!DateTimeUtil.isDateInBetweenTwoDates(time, sunrise, sunset)) {
            icon = weatherIcons.get(1);
        }

        return getIconFullPath(icon);
    }

    private static String getIconFullPath(String icon) {
        return String.format("%s/%s", WeatherIconProperties.getIconRootPath(), icon);
    }
}
