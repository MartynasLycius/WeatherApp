package com.nashid.weatherapp.services;

import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.core.utils.DBConnectionManager;
import com.nashid.weatherapp.domain.FavouriteLocation;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.domain.User;
import com.nashid.weatherapp.enums.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.VaadinSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FavouriteLocationService {
    @Inject
    private DBConnectionManager dbConnectionManager;
    public FavouriteLocation createFavouriteLocation(Long userId, String latitude, String longitude, String location, String country) {
        try {
            FavouriteLocation favouriteLocation = null;
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM favourite_location WHERE latitude = ? and longitude = ? and location = ? and country = ? and user_id = ?")) {
                preparedStatement.setString(1, latitude);
                preparedStatement.setString(2, longitude);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, country);
                preparedStatement.setLong(5, userId);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    Long id = rs.getLong("id");
                    favouriteLocation = new FavouriteLocation();
                    favouriteLocation.setId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (favouriteLocation == null) {
                favouriteLocation = new FavouriteLocation();
                favouriteLocation.setLatitude(latitude);
                favouriteLocation.setLongitude(longitude);
                favouriteLocation.setLocation(location);
                favouriteLocation.setCountry(country);
                favouriteLocation.setUserId(userId);

                try (Connection connection = dbConnectionManager.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO favourite_location (latitude, longitude, location, country, user_id) VALUES (?, ?, ?, ?, ?)")) {
                    preparedStatement.setString(1, favouriteLocation.getLatitude());
                    preparedStatement.setString(2, favouriteLocation.getLongitude());
                    preparedStatement.setString(3, favouriteLocation.getLocation());
                    preparedStatement.setString(4, favouriteLocation.getCountry());
                    preparedStatement.setLong(5, favouriteLocation.getUserId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return favouriteLocation;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Boolean isLocationFavourite(Long userId, String latitude, String longitude, String location, String country) {
        try {
            FavouriteLocation favouriteLocation = null;
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM favourite_location WHERE latitude = ? and longitude = ? and location = ? and country = ? and user_id = ?")) {
                preparedStatement.setString(1, latitude);
                preparedStatement.setString(2, longitude);
                preparedStatement.setString(3, location);
                preparedStatement.setString(4, country);
                preparedStatement.setLong(5, userId);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    Long id = rs.getLong("id");
                    favouriteLocation = new FavouriteLocation();
                    favouriteLocation.setId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (favouriteLocation == null) {
                return false;
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Boolean removeFavouriteLocation(Long userId, String latitude, String longitude, String location, String country) {
        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM favourite_location WHERE latitude = ? and longitude = ? and location = ? and country = ? and user_id = ?")) {
            preparedStatement.setString(1, latitude);
            preparedStatement.setString(2, longitude);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, country);
            preparedStatement.setLong(5, userId);
            preparedStatement.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public List<FavouriteLocation> getCurrentUserFavouriteLocations() {
        try {
            List<FavouriteLocation> favouriteLocationList = new ArrayList<>();
            if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
                return new ArrayList<>();
            }
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT latitude, longitude, location, country FROM favourite_location WHERE user_id = ?")) {
                preparedStatement.setLong(1, Integer.valueOf(VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID").toString()));
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    String latitude = rs.getString("latitude");
                    String longitude = rs.getString("longitude");
                    String location = rs.getString("location");
                    String country = rs.getString("country");
                    FavouriteLocation favouriteLocation = new FavouriteLocation();
                    favouriteLocation.setLatitude(latitude);
                    favouriteLocation.setLongitude(longitude);
                    favouriteLocation.setLocation(location);
                    favouriteLocation.setCountry(country);
                    favouriteLocationList.add(favouriteLocation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return favouriteLocationList;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
