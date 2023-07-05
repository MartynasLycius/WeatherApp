package com.eastnetic.task.utils;

import com.eastnetic.task.model.dto.Day;
import com.eastnetic.task.model.dto.Night;
import com.eastnetic.task.model.dto.WeatherCodeDetailsDTO;

import java.util.HashMap;
import java.util.Map;

public class WeatherCodeMappingUtils {

    public static Map<Integer, WeatherCodeDetailsDTO> weatherMap;

    static {
        weatherMap = new HashMap<>();
        weatherMap.put(0,new WeatherCodeDetailsDTO(
                new Day("Sunny","http://openweathermap.org/img/wn/01d@2x.png"),
                new Night("Clear","http://openweathermap.org/img/wn/01n@2x.png")));
        weatherMap.put(1,new WeatherCodeDetailsDTO(
                new Day("Mainly Sunny","http://openweathermap.org/img/wn/01d@2x.png"),
                new Night("Mainly Clear","http://openweathermap.org/img/wn/01n@2x.png")));
        weatherMap.put(2,new WeatherCodeDetailsDTO(
                new Day("Partly Cloudy","http://openweathermap.org/img/wn/02d@2x.png"),
                new Night("Partly Cloudy","http://openweathermap.org/img/wn/02n@2x.png")));
        weatherMap.put(3,new WeatherCodeDetailsDTO(
                new Day("Cloudy","http://openweathermap.org/img/wn/03d@2x.png"),
                new Night("Cloudy","http://openweathermap.org/img/wn/03n@2x.png")));
        weatherMap.put(45,new WeatherCodeDetailsDTO(
                new Day("Foggy","http://openweathermap.org/img/wn/50d@2x.png"),
                new Night("Foggy","http://openweathermap.org/img/wn/50n@2x.png")));
        weatherMap.put(48,new WeatherCodeDetailsDTO(
                new Day("Rime Fog","http://openweathermap.org/img/wn/50d@2x.png"),
                new Night("Rime Fog","http://openweathermap.org/img/wn/50n@2x.png")));
        weatherMap.put(51,new WeatherCodeDetailsDTO(
                new Day("Light Drizzle","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Light Drizzle","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(53,new WeatherCodeDetailsDTO(
                new Day("Drizzle","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Drizzle","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(55,new WeatherCodeDetailsDTO(
                new Day("Heavy Drizzle","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Heavy Drizzle","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(56,new WeatherCodeDetailsDTO(
                new Day("Light Freezing Drizzle","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Light Freezing Drizzle","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(57,new WeatherCodeDetailsDTO(
                new Day("Freezing Drizzle","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Freezing Drizzle","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(61,new WeatherCodeDetailsDTO(
                new Day("Light Rain","http://openweathermap.org/img/wn/10d@2x.png"),
                new Night("Light Rain","http://openweathermap.org/img/wn/10n@2x.png")));
        weatherMap.put(63,new WeatherCodeDetailsDTO(
                new Day("Rain","http://openweathermap.org/img/wn/10d@2x.png"),
                new Night("Rain","http://openweathermap.org/img/wn/10n@2x.png")));
        weatherMap.put(65,new WeatherCodeDetailsDTO(
                new Day("Heavy Rain","http://openweathermap.org/img/wn/10d@2x.png"),
                new Night("Heavy Rain","http://openweathermap.org/img/wn/10n@2x.png")));
        weatherMap.put(66,new WeatherCodeDetailsDTO(
                new Day("Light Freezing Rain","http://openweathermap.org/img/wn/10d@2x.png"),
                new Night("Light Freezing Rain","http://openweathermap.org/img/wn/10n@2x.png")));
        weatherMap.put(67,new WeatherCodeDetailsDTO(
                new Day("Heavy Freezing Rain","http://openweathermap.org/img/wn/10d@2x.png"),
                new Night("Heavy Freezing Rain","http://openweathermap.org/img/wn/10n@2x.png")));
        weatherMap.put(71,new WeatherCodeDetailsDTO(
                new Day("Light Snow","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Light Snow","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(73,new WeatherCodeDetailsDTO(
                new Day("Snow","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Snow","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(75,new WeatherCodeDetailsDTO(
                new Day("Heavy Snow","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Heavy Snow","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(77,new WeatherCodeDetailsDTO(
                new Day("Snow Grains","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Snow Grains","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(80,new WeatherCodeDetailsDTO(
                new Day("Light Showers","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Light Showers","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(82,new WeatherCodeDetailsDTO(
                new Day("Heavy Showers","http://openweathermap.org/img/wn/09d@2x.png"),
                new Night("Heavy Showers","http://openweathermap.org/img/wn/09n@2x.png")));
        weatherMap.put(85,new WeatherCodeDetailsDTO(
                new Day("Light Snow Showers","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Light Snow Showers","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(86,new WeatherCodeDetailsDTO(
                new Day("Heavy Snow Showers","http://openweathermap.org/img/wn/13d@2x.png"),
                new Night("Heavy Snow Showers","http://openweathermap.org/img/wn/13n@2x.png")));
        weatherMap.put(95,new WeatherCodeDetailsDTO(
                new Day("Thunderstorm","http://openweathermap.org/img/wn/11d@2x.png"),
                new Night("Thunderstorm","http://openweathermap.org/img/wn/11n@2x.png")));
        weatherMap.put(96,new WeatherCodeDetailsDTO(
                new Day("Thunderstorm With Slight Hail","http://openweathermap.org/img/wn/11d@2x.png"),
                new Night("Thunderstorm With Slight Hail","http://openweathermap.org/img/wn/11n@2x.png")));
        weatherMap.put(99,new WeatherCodeDetailsDTO(
                new Day("Thunderstorm With Heavy Hail","http://openweathermap.org/img/wn/11d@2x.png"),
                new Night("Thunderstorm With Heavy Hail","http://openweathermap.org/img/wn/11n@2x.png")));

    }

    public static String getDailyWeatherCodeDescription(int code){
        return weatherMap.get(code).getDay().getDescription();
    }

    public static String getWeatherCodeDescription(int code, int hour){
        if(hour >= 6 && hour <= 18){
            return weatherMap.get(code).getDay().getDescription();
        } else {
            return weatherMap.get(code).getNight().getDescription();
        }
    }

    public static String getWeatherCodeImage(int code, int hour){
        if(hour >= 6 && hour <= 18){
            return weatherMap.get(code).getDay().getImage();
        } else {
            return weatherMap.get(code).getNight().getImage();
        }
    }

    public static String getCurrentWeatherCodeDescription(int code){
        int currentHour = Integer.parseInt(DateUtils.getCurrentHourString());
        if(currentHour >= 6 && currentHour <= 18){
            return weatherMap.get(code).getDay().getDescription();
        } else {
            return weatherMap.get(code).getNight().getDescription();
        }
    }

    public static String getCurrentWeatherCodeImage(int code){
        int currentHour = Integer.parseInt(DateUtils.getCurrentHourString());
        if(currentHour >= 6 && currentHour <= 18){
            return weatherMap.get(code).getDay().getImage();
        } else {
            return weatherMap.get(code).getNight().getImage();
        }
    }

}
