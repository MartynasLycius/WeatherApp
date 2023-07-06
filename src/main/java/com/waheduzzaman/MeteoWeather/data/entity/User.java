package com.waheduzzaman.MeteoWeather.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role;
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    private List<FavouriteLocation> favouriteLocations;

    public User(String name, String role, String password) {
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public List<FavouriteLocation> getFavouriteLocations() {
        return (favouriteLocations.isEmpty()) ? List.of() : favouriteLocations;
    }
}
