package org.vaadin.example.repository;

import org.vaadin.example.config.DatabaseManager;
import org.vaadin.example.model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationRepository {

    public void save(Location location) {
        String sql = "INSERT INTO location (name, admin1, admin2, admin3, latitude, longitude, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, location.getName());
            statement.setString(2, location.getAdmin1());
            statement.setString(3, location.getAdmin2());
            statement.setString(4, location.getAdmin3());
            statement.setDouble(5, location.getLatitude());
            statement.setDouble(6, location.getLongitude());
            statement.setDouble(7, location.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving location", e);
        }
    }

    public Optional<Location> findByNameAndCoordinates(long userId, String name, double latitude, double longitude) {
        System.out.println(userId + " " + name + " " + latitude + " " + longitude);
        String sql = "SELECT * FROM location WHERE user_id = ? AND name = ? AND latitude = ? AND longitude = ?";

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setString(2, name);
            statement.setDouble(3, latitude);
            statement.setDouble(4, longitude);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Location location = new Location();
                location.setId(resultSet.getLong("id"));
                location.setName(resultSet.getString("name"));
                location.setAdmin1(resultSet.getString("admin1"));
                location.setAdmin2(resultSet.getString("admin2"));
                location.setAdmin3(resultSet.getString("admin3"));
                location.setLatitude(resultSet.getDouble("latitude"));
                location.setLongitude(resultSet.getDouble("longitude"));
                location.setUserId(resultSet.getLong("user_id"));

                return Optional.of(location);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding location", e);
        }
    }

    public List<Location> findAllLocationsByUserId(long userId) {
        String sql = " SELECT l.* FROM location l WHERE l.user_id = ? ";
        List<Location> locations = new ArrayList<>();

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Location location = new Location();
                location.setId(resultSet.getLong("id"));
                location.setName(resultSet.getString("name"));
                location.setAdmin1(resultSet.getString("admin1"));
                location.setAdmin2(resultSet.getString("admin2"));
                location.setAdmin3(resultSet.getString("admin3"));
                location.setLatitude(resultSet.getDouble("latitude"));
                location.setLongitude(resultSet.getDouble("longitude"));
                location.setUserId(resultSet.getLong("user_id"));
                locations.add(location);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding locations for user ID: " + userId, e);
        }

        return locations;
    }

    public boolean deleteFavoriteLocation(long id) {
        String sql = "DELETE FROM location WHERE id = ? ";

        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was affected
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting favorite location with ID: " + id, e);
        }
    }

}
