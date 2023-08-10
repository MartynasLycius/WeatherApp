package org.weather.app.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import org.weather.app.service.dto.pojo.WmoCode;

/** Utility class. */
public final class Utils {

  public static String getCurrentTimeZoneId() {
    TimeZone tmz = TimeZone.getDefault();
    return tmz.getID();
  }

  public static String getDateStringWithAmPm(String date) {
    return DateTimeFormatter.ofPattern("hh:mm a").format(LocalDateTime.parse(date));
  }

  public static String getDateStringWithWeekDayName(String date) {
    // return DateTimeFormatter.ofPattern("EEEE").format(LocalDate.parse(date));
    return DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(LocalDate.parse(date));
  }

  public static String readFileToString(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  public static String asString(Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static String findStaticImageUrl(Integer weatherCode, Map<Integer, WmoCode> wmoCodeMap) {
    return wmoCodeMap.get(weatherCode).getDay().getImageUrl();
  }

  public static Image buildIcon(String imageUrl, String title) {
    Image image = new Image(imageUrl, title);
    image.setHeight("24px");
    image.setWidth("24px");
    return image;
  }

  public static void applyCommonStyles(Component component) {
    component.addClassNames(LumoUtility.Background.BASE, LumoUtility.BorderRadius.LARGE);
    component.getStyle().set("height", "200px");
    component.getStyle().set("margin", "5px");
  }
}
