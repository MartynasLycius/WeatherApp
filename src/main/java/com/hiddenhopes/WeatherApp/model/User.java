package com.hiddenhopes.WeatherApp.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FavoriteLocation> favoriteLocations = new HashSet<>();

    // Constructors, getters, and setters

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<FavoriteLocation> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(Set<FavoriteLocation> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public void addFavoriteLocation(FavoriteLocation favoriteLocation) {
        favoriteLocations.add(favoriteLocation);
        favoriteLocation.setUser(this);
    }

    public void removeFavoriteLocation(FavoriteLocation favoriteLocation) {
        favoriteLocations.remove(favoriteLocation);
        favoriteLocation.setUser(null);
    }

    public boolean isLocationFavorite(String locationName) {
        return favoriteLocations.contains(locationName);
    }
}
