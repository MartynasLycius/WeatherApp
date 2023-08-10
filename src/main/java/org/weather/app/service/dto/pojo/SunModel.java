package org.weather.app.service.dto.pojo;

public class SunModel {

    private String sunRiseAt;
    private String sunSetAt;

    public SunModel(String sunRiseAt, String sunSetAt) {
        this.sunRiseAt = sunRiseAt;
        this.sunSetAt = sunSetAt;
    }

    public String getSunRiseAt() {
        return sunRiseAt;
    }

    public void setSunRiseAt(String sunRiseAt) {
        this.sunRiseAt = sunRiseAt;
    }

    public String getSunSetAt() {
        return sunSetAt;
    }

    public void setSunSetAt(String sunSetAt) {
        this.sunSetAt = sunSetAt;
    }

    @Override
    public String toString() {
        return "SunModel{" +
                "sunRiseAt='" + sunRiseAt + '\'' +
                ", sunSetAt='" + sunSetAt + '\'' +
                '}';
    }
}
