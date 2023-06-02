package com.proit.application.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WeatherCodeLookupUtilTest {
    @Test
    void testGetWeatherMessage() {
        int weatherCode = 0;
        String expectedMessage = "Clear Sky";

        String message = WeatherCodeLookupUtil.getWeatherMessage(weatherCode);

        assertEquals(expectedMessage, message);
    }

    @Test
    void testGetWeatherMessage_NonExistentCode() {
        int weatherCode = 999;
        String expectedMessage = null;

        String message = WeatherCodeLookupUtil.getWeatherMessage(weatherCode);

        assertEquals(expectedMessage, message);
    }

    @Test
    void testGetWeatherIconsForWeatherCode() {
        int weatherCode = 0;
        List<String> expectedIcons = List.of("day.svg", "night.svg");

        List<String> icons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);

        assertEquals(expectedIcons, icons);
    }

    @Test
    void testGetWeatherIconsForWeatherCode_NonExistentCode() {
        int weatherCode = 999;
        List<String> expectedIcons = null;

        List<String> icons = WeatherCodeLookupUtil.getWeatherIconsForWeatherCode(weatherCode);

        assertEquals(expectedIcons, icons);
    }
}