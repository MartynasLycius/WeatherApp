package org.weather.app.service.dto;

import org.apache.commons.lang3.StringUtils;
import org.weather.app.config.Constants;

public class SearchFilter {
  private String searchTerm = Constants.EMPTY_STRING;
  private String filterTerm = Constants.EMPTY_STRING;

  public void setFilterTerm(String filterTerm) {
    this.filterTerm = filterTerm;
  }

  public String getFilterTerm() {
    return filterTerm;
  }

  public String getSearchTerm() {
    return this.searchTerm;
  }

  public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  public boolean check(Location location) {
    boolean nameMatched = match(location.getName(), filterTerm);
    boolean countryNameMatched = match(location.getCountry(), filterTerm);
    boolean countryCodeMatched = match(location.getCountryCode(), filterTerm);
    return nameMatched || countryNameMatched || countryCodeMatched;
  }

  private boolean match(String propertyValue, String filterTermMatch) {
    return StringUtils.isEmpty(propertyValue)
        || StringUtils.isEmpty(filterTermMatch)
        || propertyValue.toLowerCase().contains(filterTermMatch.toLowerCase());
  }
}
