package com.eastnetic.task.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtils {

    private static final String FULL_DATE_PATTERN = "EE MMMM dd, hh:mm a";
    private static final String DAY_PATTERN = "EE";
    private static final String MONTH_DAY_PATTERN = "MMMM dd";
    private static final String TIME_FORMAT = "hh:mm a";
    private static final String HOUR_FORMAT = "h a";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

    private static final String HOUR_24_FORMAT = "H";

    public static String getCurrentFullDateString() {
        Calendar currentCal = Calendar.getInstance();
        return new SimpleDateFormat(FULL_DATE_PATTERN).format(currentCal.getTime());
    }

    public static String getCurrentHourString() {
        Calendar currentCal = Calendar.getInstance();
        return new SimpleDateFormat(HOUR_24_FORMAT).format(currentCal.getTime());
    }

    public static String getDayFromDateToString(Date date) {
        return new SimpleDateFormat(DAY_PATTERN).format(date);
    }

    public static String getMonthDayFromDateToString(Date date) {
        return new SimpleDateFormat(MONTH_DAY_PATTERN).format(date);
    }

    public static String getTimeFromDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        return sdf.format(date);
    }

    public static String getHourFromDateToString(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = sdf.parse(time);
        return new SimpleDateFormat(HOUR_FORMAT).format(date);
    }

    public static int getHour24FromStringToInt(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = sdf.parse(time);
        return Integer.parseInt(new SimpleDateFormat(HOUR_24_FORMAT).format(date));
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static String convertTimeToString(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date dateHourly = sdf.parse(time);
        return sdf.format(dateHourly);
    }

}
