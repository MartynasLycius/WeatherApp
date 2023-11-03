//package com.example.application.data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GeolocationApiServiceTest
//{
//
//    public static void main(String[] args)
//    {
//        long startTime = System.currentTimeMillis();
//        // create an instance of ForecastApiService (assuming its ready to use)
//        ForecastApiService forecastApiService = new ForecastApiService();
//
//        // create an instance of GeolocationApiService
//        GeolocationApiService geolocationApiService = new GeolocationApiService(forecastApiService);
//
//        // hardcoded search term for testing
//        String searchTerm = "vilnius";
//
//        // fetch data and store in list
//        geolocationApiService.fetchDataAndStoreInList(searchTerm);
//
//        // retrieve the list of locations
//        List<Location> locationList = geolocationApiService.getLocationList();
//        System.out.println("locationList size: " + locationList.size());
//        // print the results
//        System.out.println("Locations fetched for the search term '" + searchTerm + "':");
////        for (Location location : locationList)
////        {
////            System.out.println("Name: " + location.getLocationName());
////            System.out.println("Latitude: " + location.getLatitude());
////            System.out.println("Longitude: " + location.getLongitude());
////            System.out.println("Country: " + location.getCountry());
////            System.out.println("Timezone: " + location.getTimezone());
////            System.out.println("Daily Forecasts: " + location.getDailyForecasts());
////            System.out.println("Hourly Forecasts: " + location.getHourlyForecasts());
////            System.out.println("------------------------------------------------");
////        }
//        int totalNodes = locationList.size();
//        int totalSubnodes = 0;
//        for (Location location : locationList)
//        {
//            totalSubnodes += location.getDailyForecasts().size();
//            if (location.getHourlyForecasts() == null)
//            {
//                location.setHourlyForecasts(new ArrayList<>());
//            }
//            totalSubnodes += location.getHourlyForecasts().size();
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        // print results
//        System.out.println("Total time taken: " + (endTime - startTime) + "ms");
//        System.out.println("Total locations: " + totalNodes);
//        System.out.println("Total subnodes (daily + hourly forecasts): " + totalSubnodes);
//    }
//
//
//}
