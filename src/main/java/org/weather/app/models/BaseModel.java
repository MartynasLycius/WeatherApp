package org.weather.app.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseModel {

    public String convertTime(DateTimeFormatter dateTimeFormatter, String time){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse the original time string
        LocalTime dateTime = LocalTime.parse(time, inputFormatter);

        // Format the time to the desired output format
        return dateTime.format(dateTimeFormatter);
    }
}
