package com.example.application.data.dto.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeoCodeResponse {

    private List<MeteoLocation> results;

    @JsonProperty("generationtime_ms")
    private Double generationTime;


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MeteoLocation {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private Double elevation;

        @JsonProperty(value = "feature_code")
        private String featureCode;

        @JsonProperty(value = "country_code")
        private String countryCode;

        private int population;

        @JsonProperty("country_id")
        private int countryId;

        private String timezone;
        private String country;
        private String admin1;
        private String admin2;
        private String admin3;
        private String admin4;
        public Long admin1_id;
        public Long admin2_id;
        public Long admin3_id;
        public Long admin4_id;
    }

}
