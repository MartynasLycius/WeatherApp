package org.weather.app.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.weather.app.config.Utils;
import org.weather.app.service.dto.Location;
import org.weather.app.service.dto.WeatherForecast;
import org.weather.app.service.dto.forecast.Daily;
import org.weather.app.service.dto.forecast.Hourly;
import org.weather.app.service.dto.pojo.HourlyModel;
import org.weather.app.service.dto.pojo.RainModel;
import org.weather.app.service.dto.pojo.SunModel;
import org.weather.app.service.dto.pojo.TemperatureModel;
import org.weather.app.service.dto.pojo.WindModel;
import org.weather.app.service.http.WeatherForecastApi;

@Service
public class WeatherForecastService {

  private WeatherForecast storedWeatherForecast;
  private final WeatherForecastApi weatherForecastApi;

  public WeatherForecastService(WeatherForecastApi weatherForecastApi) {
    this.weatherForecastApi = weatherForecastApi;
  }

  public WeatherForecast getStoredWeatherForecast() {
    return this.storedWeatherForecast;
  }

  public WeatherForecast findWeatherForecast(Location location) {
    return this.storedWeatherForecast =
        weatherForecastApi.get(location.getLongitude(), location.getLatitude());
  }

  public List<HourlyModel> findHourlyForecast(int index) {
    List<HourlyModel> list = new ArrayList<>();
    Hourly hourly = storedWeatherForecast.getHourly();
    for (int startingIndex = (index > 0) ? index * 24 : 0;
        startingIndex < ((index > 0) ? ((index * 24) + 24) : 24);
        startingIndex++) {
      HourlyModel hourlyModel = new HourlyModel();
      hourlyModel.setTime(Utils.getDateStringWithAmPm(hourly.getTime().get(startingIndex)));
      hourlyModel.setTemperature(hourly.getTemperature2m().get(startingIndex));
      hourlyModel.setRelativeHumidity(hourly.getRelativeHumidity2m().get(startingIndex));
      hourlyModel.setApparentTemperature(hourly.getApparentTemperature().get(startingIndex));
      hourlyModel.setPrecipitation(hourly.getPrecipitationProbability().get(startingIndex));
      hourlyModel.setRain(hourly.getRain().get(startingIndex));
      hourlyModel.setWindSpeed(hourly.getWindSpeed10m().get(startingIndex));
      list.add(hourlyModel);
    }
    return list;
  }

  public TemperatureModel buildTemperatureModel(int index) {
    return new TemperatureModel(
        storedWeatherForecast.getDaily().getTemperature2mMax().get(index),
        storedWeatherForecast.getDaily().getTemperature2mMin().get(index),
        storedWeatherForecast.getDailyUnits().getTemperature2mMax());
  }

  public Double findPrecipitation(int index) {
    return storedWeatherForecast.getDaily().getPrecipitationProbabilityMax().get(index);
  }

  public SunModel buildSunModel(int currentDayIndex) {
    Daily daily = storedWeatherForecast.getDaily();
    return new SunModel(
        daily.getSunrise().get(currentDayIndex), daily.getSunset().get(currentDayIndex));
  }

  public Double findUvIndex(int index) {
    return storedWeatherForecast.getDaily().getUvIndexMax().get(index);
  }

  public WindModel buildWindModel(int index) {
    return new WindModel(
        storedWeatherForecast.getDailyUnits().getWindSpeed10mMax(),
        storedWeatherForecast.getDaily().getWindSpeed10mMax().get(index));
  }

  public RainModel buildRainModel(int index) {
    return new RainModel(
        storedWeatherForecast.getDailyUnits().getRainSum(),
        storedWeatherForecast.getDaily().getRainSum().get(index));
  }
}
