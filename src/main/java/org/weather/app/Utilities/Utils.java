package org.weather.app.Utilities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static String convertDate(String date, DateTimeFormatter dateTimeFormatter){
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return dateTime.format(dateTimeFormatter);
    }

    public static String getDayOrNight(String date) {
        LocalTime DAY_START = LocalTime.of(6, 0);
        LocalTime NIGHT_START = LocalTime.of(18, 0);
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalTime time = dateTime.toLocalTime();
        if (time.isAfter(DAY_START) && time.isBefore(NIGHT_START)) {
            return "1";
        } else {
            return "0";
        }
    }
}
