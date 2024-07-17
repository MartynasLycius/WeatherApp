package org.weather.app.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.weather.app.entities.User;
import org.weather.app.repositories.UserRepository;

import java.util.Optional;

@Stateless
public class AuthService {
    @Inject
    private UserRepository userRepository;

    public Optional<User> authenticate(String username, String password) {
        return userRepository.authenticate(username, password);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
