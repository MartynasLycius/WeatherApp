package org.weather.app.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "name",
  "latitude",
  "longitude",
  "country_code",
  "timezone",
  "country_id",
  "country",
})
public class Location {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("country_code")
  private String countryCode;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("country_id")
  private Integer countryId;

  @JsonProperty("country")
  private String country;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public Integer getCountryId() {
    return countryId;
  }

  public void setCountryId(Integer countryId) {
    this.countryId = countryId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "Location{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", latitude="
        + latitude
        + ", longitude="
        + longitude
        + ", countryCode='"
        + countryCode
        + '\''
        + ", timezone='"
        + timezone
        + '\''
        + ", countryId="
        + countryId
        + ", country='"
        + country
        + '\''
        + '}';
  }
}
