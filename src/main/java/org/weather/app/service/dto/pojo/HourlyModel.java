package org.weather.app.service.dto.pojo;

public class HourlyModel {

  private String time;
  private Double temperature;
  private Double relativeHumidity;
  private Double apparentTemperature;
  private Double precipitation;
  private Double rain;
  private Double windSpeed;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  public Double getRelativeHumidity() {
    return relativeHumidity;
  }

  public void setRelativeHumidity(Double relativeHumidity) {
    this.relativeHumidity = relativeHumidity;
  }

  public Double getApparentTemperature() {
    return apparentTemperature;
  }

  public void setApparentTemperature(Double apparentTemperature) {
    this.apparentTemperature = apparentTemperature;
  }

  public Double getPrecipitation() {
    return precipitation;
  }

  public void setPrecipitation(Double precipitation) {
    this.precipitation = precipitation;
  }

  public Double getRain() {
    return rain;
  }

  public void setRain(Double rain) {
    this.rain = rain;
  }

  public Double getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(Double windSpeed) {
    this.windSpeed = windSpeed;
  }

  @Override
  public String toString() {
    return "HourlyModel{"
        + "time='"
        + time
        + '\''
        + ", temperature="
        + temperature
        + ", relativeHumidity="
        + relativeHumidity
        + ", apparentTemperature="
        + apparentTemperature
        + ", precipitation="
        + precipitation
        + ", rain="
        + rain
        + ", windSpeed="
        + windSpeed
        + '}';
  }
}
