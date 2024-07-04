package com.nashid.weatherapp.services;

import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.core.utils.DBConnectionManager;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.domain.User;
import com.nashid.weatherapp.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

@ApplicationScoped
public class UserService {

    @Inject
    private DBConnectionManager dbConnectionManager;

    @Inject
    private SettingsService settingsService;

    public User createImplementerLogin() {
        try {
            User user = null;
            Settings settings = null;
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, password, first_name, last_name, role FROM user WHERE id = ?")) {
                preparedStatement.setInt(1, 1);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String role = rs.getString("role");
                    user = new User(1L, username, password, firstName, lastName, UserRole.valueOf(role));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (user == null) {
                user = new User();
                user.setId(1L);
                user.setFirstName("Implementer");
                user.setLastName("Local");
                user.setUsername("implementer.local");
                user.setPassword(Base64.getEncoder().encodeToString("implementer".getBytes(StandardCharsets.UTF_8)));
                user.setRole(UserRole.ADMIN);

                try (Connection connection = dbConnectionManager.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?)")) {
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getFirstName());
                    preparedStatement.setString(4, user.getLastName());
                    preparedStatement.setString(5, user.getRole().name());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                settings = settingsService.createOrUpdateDefaultSettings(user);
            }
            else {
                settings = settingsService.getUserSettings(user);
            }
            WeatherApi.DEFAULT_TIMEZONE = settings.getTimeZone().toString();
            return user;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public User checkUser(String username, String password) {
        try {
            User user = null;
            try (Connection connection = dbConnectionManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM user WHERE username = ? and password = ?")) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    Long id = rs.getLong("id");
                    user = new User();
                    user.setId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
