package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.proit.weather.utils.LocalDateDeserializer;
import com.proit.weather.utils.LocalDateSerializer;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DailyUnitDetails {
    @JsonProperty("time")
    @JsonDeserialize(contentUsing = LocalDateDeserializer.class)
    @JsonSerialize(contentUsing = LocalDateSerializer.class)
    private List<LocalDate> time;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperature2m;

    @JsonProperty("rain_sum")
    private List<Double> rain;

    @JsonProperty("wind_speed_10m_max")
    private List<Double> windSpeed10m;

    @JsonIgnore
    public List<DailyDetails> getDailyDetails() {
        List<DailyDetails> detailList = new ArrayList<>();
        DailyDetails dailyDetails;
        for (int i = 0; i < this.time.size(); i++) {
            dailyDetails = DailyDetails.builder()
                    .date(this.time.get(i))
                    .temperature(this.temperature2m.get(i))
                    .rain(this.rain.get(i))
                    .windSpeed(this.windSpeed10m.get(i))
                    .build();
            detailList.add(dailyDetails);
        }
        return detailList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyDetails {
        private LocalDate date;
        private Double temperature;
        private Double rain;
        private Double windSpeed;
    }
}
