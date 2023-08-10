package org.weather.app.service.dto.pojo;

public class WindModel {

    private String unit;
    private Double speed;

    public WindModel(String unit, Double speed) {
        this.unit = unit;
        this.speed = speed;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "WindModel{" +
                "unit='" + unit + '\'' +
                ", speed=" + speed +
                '}';
    }
}
