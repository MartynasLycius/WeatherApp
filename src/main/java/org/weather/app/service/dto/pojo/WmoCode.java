package org.weather.app.service.dto.pojo;

public class WmoCode {

  private DayNight day;
  private DayNight night;

  public DayNight getDay() {
    return day;
  }

  public void setDay(DayNight day) {
    this.day = day;
  }

  public DayNight getNight() {
    return night;
  }

  public void setNight(DayNight night) {
    this.night = night;
  }

  @Override
  public String toString() {
    return "WmoCode{" + "day=" + day + ", night=" + night + '}';
  }
}
