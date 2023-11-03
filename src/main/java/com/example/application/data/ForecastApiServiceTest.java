//package com.example.application.data;
//
//import java.util.List;
//
//public class ForecastApiServiceTest
//{
//
//    public static void main(String[] args)
//    {
//        // create an instance of ForecastApiService
//        ForecastApiService forecastApiService = new ForecastApiService();
//
//        // create a sample location
//        Location sampleLocation = new Location();
//        sampleLocation.setLocationName("Australia");
//        sampleLocation.setLatitude("-25.0");
//        sampleLocation.setLongitude("135.0");
//        sampleLocation.setCountry("Australia");
//        sampleLocation.setTimezone("");
//
//        // fetch and store forecasts for the sample location
//        forecastApiService.fetchAndStoreForecasts(sampleLocation);
//
//        // print daily forecasts
//        List<DailyForecast> dailyForecasts = sampleLocation.getDailyForecasts();
//        System.out.println("Daily Forecasts:");
//        for (DailyForecast forecast : dailyForecasts)
//        {
//            System.out.println("Date: " + forecast.getDate() + ", Max Temp: " + forecast.getMaxTemperature() + ", Min Temp: " + forecast.getMinTemperature());
//        }
//
//        // print hourly forecasts
//        List<HourlyForecast> hourlyForecasts = sampleLocation.getHourlyForecasts();
//        System.out.println("\nHourly Forecasts:");
//        for (HourlyForecast forecast : hourlyForecasts)
//        {
//            System.out.println("Time: " + forecast.getTime() + ", Temperature: " + forecast.getTemperature() + ", Rain: " + forecast.getRain() + ", Wind Speed: " + forecast.getWindSpeed());
//        }
//    }
//}
