package com.weather.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favouriteLocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long locationId;
    private String location;
    private String country;
}
