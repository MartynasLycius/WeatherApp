package com.hiddenhopes.WeatherApp.serviceImpl;

import com.hiddenhopes.WeatherApp.model.FavoriteLocation;
import com.hiddenhopes.WeatherApp.model.User;
import com.hiddenhopes.WeatherApp.repository.FavoriteLocationRepository;
import com.hiddenhopes.WeatherApp.repository.UserRepository;
import com.hiddenhopes.WeatherApp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FavoriteLocationRepository favoriteLocationRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, FavoriteLocationRepository favoriteLocationRepository) {
        this.userRepository = userRepository;
        this.favoriteLocationRepository = favoriteLocationRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void addFavoriteLocationByCoordinates(String username, FavoriteLocation favoriteLocation) {
        User user = userRepository.findByUsername(username);
        user.addFavoriteLocation(favoriteLocation);
        userRepository.save(user);
    }

    public void removeFavoriteLocationByCoordinates(String username, FavoriteLocation favoriteLocation) {
        User user = userRepository.findByUsername(username);
        FavoriteLocation removedLocation = favoriteLocationRepository.findByCoordinates(favoriteLocation.getLatitude(), favoriteLocation.getLongitude())
                .orElseThrow(() -> new IllegalArgumentException("Favorite location not found"));
        user.removeFavoriteLocation(removedLocation);
        userRepository.save(user);
    }

    public boolean isLocationFavorite(Long userId, double latitude, double longitude) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        FavoriteLocation favoriteLocation = favoriteLocationRepository.findByCoordinates(latitude, longitude)
                .orElse(null);
        return favoriteLocation != null && user.getFavoriteLocations().contains(favoriteLocation);
    }

    public List<FavoriteLocation> getFavoriteLocations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new ArrayList<>(user.getFavoriteLocations());
    }
}
