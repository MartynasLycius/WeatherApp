package com.example.application.data.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserFavLocationDto implements Serializable {
    private Long id;
    private Long locationId;
}
