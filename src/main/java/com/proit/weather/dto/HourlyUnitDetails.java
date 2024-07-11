package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.proit.weather.utils.LocalDateTimeDeserializer;
import com.proit.weather.utils.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class HourlyUnitDetails {
    @JsonProperty("time")
    @JsonSerialize(contentUsing = LocalDateTimeSerializer.class)
    @JsonDeserialize(contentUsing = LocalDateTimeDeserializer.class)
    private List<LocalDateTime> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature2m;

    @JsonProperty("wind_speed_10m")
    private List<Double> windSpeed10m;

    @JsonProperty("rain")
    private List<Double> rain;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HourlyDetails {
        private LocalDateTime dateTime;
        private Double temperature;
        private Double rain;
        private Double windSpeed;
    }

    @JsonIgnore
    public List<HourlyDetails> getHourlyDetails(DailyUnitDetails.DailyDetails dailyDetails) {
        List<HourlyDetails> detailList = new ArrayList<>();
        HourlyDetails hourlyDetails;
        for (int i = 0; i < this.time.size(); i++) {
            LocalDateTime dateTime = this.time.get(i);
            if (!dailyDetails.getDate().isEqual(dateTime.toLocalDate())) {
                continue;
            }
            hourlyDetails = HourlyDetails.builder()
                    .dateTime(dateTime)
                    .rain(this.rain.get(i))
                    .temperature(this.temperature2m.get(i))
                    .windSpeed(this.windSpeed10m.get(i))
                    .build();
            detailList.add(hourlyDetails);
        }
        return detailList;
    }
}
