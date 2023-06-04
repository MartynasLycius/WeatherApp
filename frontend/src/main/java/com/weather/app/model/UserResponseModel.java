package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}