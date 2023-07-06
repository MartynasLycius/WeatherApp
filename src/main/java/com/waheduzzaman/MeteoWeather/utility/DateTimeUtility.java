package com.waheduzzaman.MeteoWeather.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeUtility {

    public String getCurrentTimeZoneString() {
        TimeZone tmz = TimeZone.getDefault();
        return tmz.getID();
    }

    public static String getAMPMTimeFromDateString(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return formatter.format(LocalDateTime.parse(date));
    }

    public String getWeekDayNameFromDateString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
        return formatter.format(LocalDate.parse(date));
    }
}
