package org.weather.app.Utilities;

import java.util.HashMap;
import java.util.Map;

public class Constraints {

    public static final String FAVORITE_IMAGE_ADD = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAACRklEQVR4nO2YP2sUURTFL7xzdplCDQgWaiXaCaJGRPyDiKD4AWzVTr+BWGlpoSi2Nn4FtUinJBZqYUwjiEZQUGJ2595ZUiQWZuTJorubNTuzO9l5kfnBhWWZve+ce+977BuRioqKioqKjChwUsm7CswquaDkTyUXjZwx8lZSr+8dlKNVq+3zz/rfKLnYzrHQznlHgRNSNAl52MjnRqbrhRdjwKMlkR29ORoiWxR4+PuZwXmeGXmoEPFGXlFyZdCiXQF8TYDJPwUAJhWYz5NDyRUlL40sPpfwbgGm5AEjDxqZDJvHyMtDiU+AI0r+GGHhVIGPeStvfTrhR3iY6r8YZeGCYzqfeOdOByA67enmqTzVf1C2YFsb97IbAOYC7MBsZgNKxsEZIBt5DCwHaGA5zwh9DnCEPmU3QD4tW7CtjSfZRwi4FoDgtDNi4GpmA02RrUpq2aLtbyQmMpHZQLsL1wMQnvpQ4IbkJRWBkq8CEP8mFaEMQ1Kv71Hye2niyUaWS9K6tICjRrZKMLAUA8dGEt9pYsybuhUDxwsR33WXBd6NYebnY3K/bAT+eDXg8QYamO53ny6UVMQpeVvJ1QI366r/u+xPPhkXiXNn/euQAgwkCXlRyqAZRbt920eo/Gt/VEuZ+JEy8maW9z09I3M/FalJKJhzZ4z8lkF8I3bugoRII4p2GvD2nwaAuWYU7ZKQMZGJfvtCyZctke2yGfgiEik51SF+yn8nmwkV2abABwXe+8+yGYmdO584d65sHRUVFRUV/y+/ACpYZ3MzGqbBAAAAAElFTkSuQmCC";
    public static final String FAVORITE_IMAGE_REMOVE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAAD5UlEQVR4nO1YSYgdVRS9SfqdU3/oRHDoKE4o6sK4cFqIIARcOOHGCUE0QgioQQSFgAqKG9ciOCAqBCMOG9FVQARFQV01ajpOccgmiWm7u96r/2OIseRW32o+EtJVv/rnV0sdePCpX+cOde+7774r0qBBgwYNGjQoh1TEJeRNAXjZk9MeOOjJowH4NQCfeuCZGLhkOTn6TiCfDcBnylUZJmtaZasO1TUKB9YE4K5A/hjItMD6aJ688L9y9Jn+V1DGDx64cyWdaHly14CCGQ881ZuYuCZ0OlOZk53OWYHcHIBXAuntvSSJogdyOTG5xZM9+y+2dzcr12RM9SYmrvXA04Hcm+vzwFupSFTZiQB8YULn1ZhUZN3JOIdFJs3I1JP/BPIRXfY71dQ5JNJdRu+6mHwwkAvG+VxtGdaJNZ58xwT9HAOXluFrKnrymDpg65gH7igjIwYuC8A++yhvq02lHQnO3WuRmCvrRA5Pbl1KEfLhYWTE6gw5l8lx7u5S5FQE+ZdIouh+qYAAvK6rioyY3GKZsU9tK04EbrOv+G0qsraKEbMi63VVkZGKrA3kHrUpBm4tTAzAG1YxdkhN4IEnLSqvFSeR32Rp5dxVUhP0tNwvZsl0YVIAZrMwTk6eLjWB73bPsIgcLk7StoNMS22sESPVArQYkaOFSQH4IyN1u2dKTRA6nSnbtweLk6xCJM5dKTVBz7mr80pamJT3Vp7cJjWBJx+yiOwsTArAo+bIu1ITBPJ96xK2FyZpu239UV+bQBkz5kQ2ePKIJ4/3o+i8UuTs0qP7BHhCxgwP7LC0+qQ0OQZuN/KhcUZlVmR9XkUT4JZh2/ivzZkXZUwIwEt2EH5ZqS2wO8XxQN4opxgJcLPqVhsqHwWBfD5vDU50Dx8VFsiLAvmnVarnKgvUa6cHdluK/daPogtkxOi1Wucu3QyBj5e7XheGtio61bCv833Sbm+UESFpt88emNbsXfHGVSPhgf2mYE8/is5fUQWmI5AzFonfS58ZpRQBP+V7xkfRDSslO46i6zxwwGT/skBeLKNEr9U6Z6ksk0dCFN1XVabOvzz5l0X7K00vORVIRRiANweGaDsPiHSGkBN58oWBScuuoedXVRCAxz35txnxXXBuU2Guc1fk1wU9JwLwmIwTPoquz4bYiwb1dap4skFaNh4lt2dpaftB94fUAbOL/dCrA6m2+0R5bmV8cIj93rzIaVI3BOfuyQcXeiUdbPL0tz7LK56OVKXOSNrtjYH8MB9ia8OZrXyITX6g929ZLfDkNtszeUXq6yxYViOCc5vspJ4Jzl0uqxlzIht0jduOBg0ayP8D/wLWY1YwDKR/aQAAAABJRU5ErkJggg==";

    public static Map<String, String> getWeatherCodeMap(){
        Map<String, String> weatherCodeMap = new HashMap<>();

        weatherCodeMap.put("0", "Clear sky");
        weatherCodeMap.put("1", "Mainly clear");
        weatherCodeMap.put("2", "Partly cloudy");
        weatherCodeMap.put("3", "Overcast");
        weatherCodeMap.put("45", "Fog");
        weatherCodeMap.put("48", "Depositing rime fog");
        weatherCodeMap.put("51", "Drizzle: Light");
        weatherCodeMap.put("53", "Drizzle: Moderate");
        weatherCodeMap.put("55", "Drizzle: Dense");
        weatherCodeMap.put("56", "Freezing Drizzle: Light");
        weatherCodeMap.put("57", "Freezing Drizzle: Dense");
        weatherCodeMap.put("61", "Rain: Slight");
        weatherCodeMap.put("63", "Rain: Moderate");
        weatherCodeMap.put("65", "Rain: Heavy");
        weatherCodeMap.put("66", "Freezing Rain: Light");
        weatherCodeMap.put("67", "Freezing Rain: Heavy");
        weatherCodeMap.put("71", "Snow fall: Slight");
        weatherCodeMap.put("73", "Snow fall: Moderate");
        weatherCodeMap.put("75", "Snow fall: Heavy");
        weatherCodeMap.put("77", "Snow grains");
        weatherCodeMap.put("80", "Rain showers: Slight");
        weatherCodeMap.put("81", "Rain showers: Moderate");
        weatherCodeMap.put("82", "Rain showers: Violent");
        weatherCodeMap.put("85", "Snow showers: Slight");
        weatherCodeMap.put("86", "Snow showers: Heavy");
        weatherCodeMap.put("95", "Thunderstorm: Moderate");
        weatherCodeMap.put("96", "Thunderstorm with slight hail");
        weatherCodeMap.put("99", "Thunderstorm with heavy hail");

        return weatherCodeMap;
    }

    public static Map<String, String> getDayNightIconMap(){
        Map<String, String> dayNightIconMap = new HashMap<>();
        dayNightIconMap.put("1", "images/animated/day.svg");
        dayNightIconMap.put("0", "images/animated/night.svg");
        return dayNightIconMap;
    }

    public static Map<String, String> getWeatherIconMap(String dayNight){
        if(dayNight.equalsIgnoreCase("1") || dayNight.isEmpty()){
            return getWeatherIconMap();
        }else {
            return getWeatherIconMapNight();
        }
    }

    public static Map<String, String> getWeatherIconMap(){
        Map<String, String> weatherIconMap = new HashMap<>();
        weatherIconMap.put("0", "images/animated/0-clear-day-day.svg");
        weatherIconMap.put("1", "images/animated/0-clear-day-day.svg");
        weatherIconMap.put("2", "images/animated/partly-cloudy-day.svg");
        weatherIconMap.put("3", "images/animated/overcast-day.svg");
        weatherIconMap.put("45", "images/animated/fog-day.svg");    //fog
        weatherIconMap.put("48", "images/animated/rime-fog-day.svg");    //Depositing rime fog
        weatherIconMap.put("51", "images/animated/53-drizzle-light.day.svg");
        weatherIconMap.put("53", "images/animated/53-drizzle-light.day.svg");
        weatherIconMap.put("55", "images/animated/53-drizzle-light.day.svg");
        weatherIconMap.put("56", "animated/day-drizzle.svg");    //Freezing Drizzle: Light
        weatherIconMap.put("57", "animated/day-drizzle.svg");    //Freezing Drizzle: Dense
        weatherIconMap.put("61", "images/animated/day-rain.svg");       //Rain: Slight
        weatherIconMap.put("63", "images/animated/day-rain-moderate.svg");       //Rain: Moderate
        weatherIconMap.put("65", "images/animated/day-rain-heavy.svg");       //Rain: Heavy
        weatherIconMap.put("66", "images/animated/freezing-rain.svg");  //Freezing Rain: Light
        weatherIconMap.put("67", "images/animated/freezing-rain.svg");  //Freezing Rain: Heavy
        weatherIconMap.put("71", "images/animated/day-snow.svg");
        weatherIconMap.put("73", "images/animated/day-snow-moderate.svg");
        weatherIconMap.put("75", "images/animated/day-snow-heavy.svg");
        weatherIconMap.put("77", "images/animated/snow-grains.svg");    //Snow grains
        weatherIconMap.put("80", "images/animated/snowy-1.svg");
        weatherIconMap.put("81", "images/animated/snowy-2.svg");
        weatherIconMap.put("82", "images/animated/snowy-2.svg");
        weatherIconMap.put("85", "images/animated/snowy-3.svg");
        weatherIconMap.put("86", "images/animated/snowy-4.svg");
        weatherIconMap.put("95", "images/animated/thunderstorms.svg");
        weatherIconMap.put("96", "images/animated/thunderstorms-hail.svg");
        weatherIconMap.put("99", "images/animated/thunderstorms-hail.svg");

        return weatherIconMap;
    }

    public static Map<String, String> getWeatherIconMapNight(){
        Map<String, String> weatherIconMapNight = new HashMap<>();
        weatherIconMapNight.put("0", "images/animated/clear-night.svg");
        weatherIconMapNight.put("1", "images/animated/clear-night.svg");
        weatherIconMapNight.put("2", "images/animated/partly-cloudy-night.svg");
        weatherIconMapNight.put("3", "images/animated/overcast-night.svg");
        weatherIconMapNight.put("45", "images/animated/fog-night.svg");    //fog
        weatherIconMapNight.put("48", "images/animated/rime-fog-night.svg");    //Depositing rime fog
        weatherIconMapNight.put("51", "images/animated/53-drizzle-light.day.svg");
        weatherIconMapNight.put("53", "images/animated/53-drizzle-light.day.svg");
        weatherIconMapNight.put("55", "images/animated/53-drizzle-light.day.svg");
        weatherIconMapNight.put("56", "images/animated/night-drizzle.svg");    //Freezing Drizzle: Light
        weatherIconMapNight.put("57", "images/animated/night-drizzle.svg");    //Freezing Drizzle: Dense
        weatherIconMapNight.put("61", "images/animated/night-rain.svg");       //Rain: Slight
        weatherIconMapNight.put("63", "images/animated/night-rain-moderate.svg");       //Rain: Moderate
        weatherIconMapNight.put("65", "images/animated/night-rain-heavy.svg");       //Rain: Heavy
        weatherIconMapNight.put("66", "images/animated/freezing-rain-night.svg");  //Freezing Rain: Light
        weatherIconMapNight.put("67", "images/animated/freezing-rain-night.svg");  //Freezing Rain: Heavy
        weatherIconMapNight.put("71", "images/animated/night-snow.svg");
        weatherIconMapNight.put("73", "images/animated/night-snow-moderate.svg");
        weatherIconMapNight.put("75", "images/animated/night-snow-heavy.svg");
        weatherIconMapNight.put("77", "images/animated/snow-grains-night.svg");    //Snow grains
        weatherIconMapNight.put("80", "images/animated/snowy-4.svg");
        weatherIconMapNight.put("81", "images/animated/snowy-5.svg");
        weatherIconMapNight.put("82", "images/animated/snowy-5.svg");
        weatherIconMapNight.put("85", "images/animated/snowy-6.svg");
        weatherIconMapNight.put("86", "images/animated/snowy-6.svg");
        weatherIconMapNight.put("95", "images/animated/thunderstorms-night.svg");
        weatherIconMapNight.put("96", "images/animated/thunderstorms-night-hail.svg");
        weatherIconMapNight.put("99", "images/animated/thunderstorms-night-hail.svg");

        return weatherIconMapNight;
    }


    public static String rainForecastIconMap(double totalRain, String isDay){

        if((totalRain == 0.0)){
            return "images/animated/cloudy.svg";
        }
        if(isDay.equalsIgnoreCase("1" )){
            if(totalRain >= 0.0 && totalRain <= 0.50){
                return "images/animated/rainy-slight-day.svg";
            }else if(totalRain >= 0.50 && totalRain <= 1.00){
                return "images/animated/rainy-moderate-day.svg";
            }else {
                return "images/animated/rainy-heavy-day.svg";
            }
        }else {
            if(totalRain >= 0.0 && totalRain <= 0.50){
                return "images/animated/rainy-slight-night.svg";
            }else if(totalRain >= 0.50 && totalRain <= 1.00){
                return "images/animated/rainy-moderate-night.svg";
            }else {
                return "images/animated/rainy-heavy-night.svg";
            }
        }

    }
}
