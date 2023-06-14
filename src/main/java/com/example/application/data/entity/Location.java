package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "location")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long locationId;

    private Double latitude;

    private Double longitude;

    private String address;

    private String country;

    private String countryCode;
}
