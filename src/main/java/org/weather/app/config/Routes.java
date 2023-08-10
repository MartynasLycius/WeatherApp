package org.weather.app.config;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public final class Routes {

  public static final String FRONT_SLASH = "/";
  public static final String DEFAULT_ROUTE = "";
  public static final String LOGIN_ROUTE = FRONT_SLASH + "login";
  public static final String PROFILE_ROUTE = FRONT_SLASH + "profile";
  private static String CURRENT_PATH = "";

  public static void setCurrentPath(String path) {
    CURRENT_PATH = path;
  }

  public static void openPage(String page) {
    UI.getCurrent().navigate(page);
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

  private Routes() {}
}
