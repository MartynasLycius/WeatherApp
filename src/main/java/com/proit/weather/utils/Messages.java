package com.proit.weather.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle bundle;

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("messages", locale);
    }

    public static String get(String key) {
        if (bundle == null) {
            setLocale(Locale.getDefault());
        }
        return bundle.getString(key);
    }
}
