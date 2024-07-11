package com.proit.weather.model;

import com.proit.weather.utils.StringListConverter;
import lombok.Data;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.List;

@Data
@Entity
public class LocationInfoModel {

    @Id
    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;

    private Double elevation;

    private String featureCode;

    private String countryCode;

    private String timezone;

    private Integer population;

    private Integer countryId;

    private String country;

    @Convert(converter = StringListConverter.class)
    @Lob
    private List<String> postcodes;
}
