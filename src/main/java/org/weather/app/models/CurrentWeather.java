package org.weather.app.models;

public class CurrentWeather {
    private String temperature;
    private String apparentTemperature;
    private String windSpeed;
    private String humidity;
    private String surfacePressure;
    private String weatherCode;
    private String precipitation;
    public String rain;
    public String totalCloudCover;
    private String lastUpdateTime;
    private String isDay;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTotalCloudCover() {
        return totalCloudCover;
    }

    public void setTotalCloudCover(String totalCloudCover) {
        this.totalCloudCover = totalCloudCover;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getSurfacePressure() {
        return surfacePressure;
    }

    public void setSurfacePressure(String surfacePressure) {
        this.surfacePressure = surfacePressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(String apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }
}
