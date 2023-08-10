package org.weather.app.service.dto.pojo;

public class RainModel {

  private String unit;
  private Double sum;

  public RainModel(String unit, Double sum) {
    this.unit = unit;
    this.sum = sum;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getSum() {
    return sum;
  }

  public void setSum(Double sum) {
    this.sum = sum;
  }

  @Override
  public String toString() {
    return "RainModel{" + "unit='" + unit + '\'' + ", sum=" + sum + '}';
  }
}
