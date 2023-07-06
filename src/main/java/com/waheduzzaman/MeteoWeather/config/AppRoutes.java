package com.waheduzzaman.MeteoWeather.config;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.server.VaadinSession;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class AppRoutes {
    public static final String SEPARATOR = "/";
    public static final String INDEX_ROUTE = "";
    public static final String LOGIN_ROUTE = SEPARATOR + "login";
    public static final String HOME_ROUTE = SEPARATOR + "home";
    private static String CURRENT_PATH = "";

    public static boolean isSameRoute(String route1, String route2) {
        return StringUtils.equals(route1.replace("/", ""), route2.replace("/", ""));
    }

    public static void setCurrentPath(String path) {
        CURRENT_PATH = path;
    }

    public static String getCurrentRoutePath() {
        return CURRENT_PATH;
    }

    public static void goBack() {
        UI.getCurrent().getPage().getHistory().back();
    }

    public static void openPage(String page) {
        UI.getCurrent().navigate(page);
    }

    public static void openPage(Class<Component> page) {
        UI.getCurrent().navigate(page);
    }

    public static void openPage(Class<Component> page, RouteParameters parameters) {
        UI.getCurrent().navigate(page, parameters);
    }

    public static void setSessionValue(String key, Object value) {
        VaadinSession.getCurrent().setAttribute(key, value);
    }

    public static void clearSessionValue(String key) {
        VaadinSession.getCurrent().setAttribute(key, null);
    }

    public static Object getSessionValue(String key) {
        return VaadinSession.getCurrent().getAttribute(key);
    }
}
