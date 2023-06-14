package com.example.application.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public final class DateTimeUtil {
    private static final String DATE_FORMAT = "EEEE, MMMM d";
    private static final String TIME_FORMAT = "h:mm a";

    private DateTimeUtil() {

    }

    public static String convertDateStringToDayAndDateString(String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }

    public static String convertDateTimeStringToTimeAmPmString(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalDateTime.parse(dateTimeString).format(formatter);
    }
}
