package com.nashid.weatherapp.services;

import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.core.utils.DBConnectionManager;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.domain.User;
import com.nashid.weatherapp.enums.*;
import com.vaadin.flow.server.VaadinSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class SettingsService {

    @Inject
    private DBConnectionManager dbConnectionManager;
    public Settings createOrUpdateDefaultSettings(User user) {
        try {
            Settings settings = null;

            if (settings == null) {
                settings = new Settings();
                settings.setTemperatureUnit(TemperatureUnit.celsius);
                settings.setTimeZone(TimeZone.UTC);
                settings.setWindSpeedUnit(WindSpeedUnit.kmh);
                settings.setPrecipitationUnit(PrecipitationUnit.mm);
                settings.setUser(user);

                System.out.println("Value check: " + settings.getUser().getId().longValue());
                try (Connection connection = dbConnectionManager.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO settings (temperature_unit, time_zone, wind_speed_unit, precipitation_unit, user_id) VALUES (?, ?, ?, ?, ?)")) {
                    preparedStatement.setString(1, settings.getTemperatureUnit().name());
                    preparedStatement.setString(2, settings.getTimeZone().name());
                    preparedStatement.setString(3, settings.getWindSpeedUnit().name());
                    preparedStatement.setString(4, settings.getPrecipitationUnit().name());
                    preparedStatement.setLong(5, settings.getUser().getId().longValue());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return settings;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Boolean updateCurrentUserSettings(String timeZone, String temperature, String wind, String precipitation) {
        try {
            if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
                return false;
            }
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE settings SET temperature_unit = ?, time_zone = ?, wind_speed_unit = ?, precipitation_unit = ? WHERE user_id=?")) {
                preparedStatement.setString(1, temperature);
                preparedStatement.setString(2, timeZone);
                preparedStatement.setString(3, wind);
                preparedStatement.setString(4, precipitation);
                preparedStatement.setLong(5, Integer.valueOf(VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID").toString()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public Settings getCurrentUserSettings() {
        try {
            Settings settings = null;
            if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
                return null;
            }
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT temperature_unit, time_zone, wind_speed_unit, precipitation_unit FROM settings WHERE user_id = ?")) {
                preparedStatement.setLong(1, Integer.valueOf(VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID").toString()));
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    String temperature_unit = rs.getString("temperature_unit");
                    String time_zone = rs.getString("time_zone");
                    String wind_speed_unit = rs.getString("wind_speed_unit");
                    String precipitation_unit = rs.getString("precipitation_unit");
                    settings = new Settings();
                    settings.setTemperatureUnit(TemperatureUnit.valueOf(temperature_unit));
                    settings.setTimeZone(TimeZone.valueOf(time_zone));
                    settings.setWindSpeedUnit(WindSpeedUnit.valueOf(wind_speed_unit));
                    settings.setPrecipitationUnit(PrecipitationUnit.valueOf(precipitation_unit));
                    WeatherApi.DEFAULT_TIMEZONE = settings.getTimeZone().toString();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return settings;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Settings getUserSettings(User user) {
        try {
            Settings settings = null;
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT temperature_unit, time_zone, wind_speed_unit, precipitation_unit FROM settings WHERE user_id = ?")) {
                preparedStatement.setLong(1, user.getId());
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    String temperature_unit = rs.getString("temperature_unit");
                    String time_zone = rs.getString("time_zone");
                    String wind_speed_unit = rs.getString("wind_speed_unit");
                    String precipitation_unit = rs.getString("precipitation_unit");
                    settings = new Settings();
                    settings.setTemperatureUnit(TemperatureUnit.valueOf(temperature_unit));
                    settings.setTimeZone(TimeZone.valueOf(time_zone));
                    settings.setWindSpeedUnit(WindSpeedUnit.valueOf(wind_speed_unit));
                    settings.setPrecipitationUnit(PrecipitationUnit.valueOf(precipitation_unit));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return settings;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
