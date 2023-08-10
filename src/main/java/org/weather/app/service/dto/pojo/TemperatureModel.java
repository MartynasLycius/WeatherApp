package org.weather.app.service.dto.pojo;

public class TemperatureModel {

    private Double high;
    private Double low;
    private String unit;

    public TemperatureModel(Double high, Double low, String unit) {
        this.high = high;
        this.low = low;
        this.unit = unit;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "TemperatureModel{" +
                "high=" + high +
                ", low=" + low +
                ", unit='" + unit + '\'' +
                '}';
    }
}
