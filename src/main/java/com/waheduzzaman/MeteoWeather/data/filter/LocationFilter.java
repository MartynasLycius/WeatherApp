package com.waheduzzaman.MeteoWeather.data.filter;

import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import org.apache.commons.lang3.StringUtils;

public class LocationFilter {
    private String searchTerm = "", filterTerm = "";

    public void setFilterTerm(String filterTerm) {
        this.filterTerm = filterTerm;
    }

    public String getSearchTerm() {
        return this.searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public boolean test(Location location) {
        boolean matchesName = matches(location.getName(), filterTerm);
        boolean matchesCountryName = matches(location.getCountry(), filterTerm);
        boolean matchesCountryCode = matches(location.getCountryCode(), filterTerm);
        return matchesName || matchesCountryName || matchesCountryCode;
    }

    private boolean matches(String value, String searchTerm) {
        return StringUtils.isEmpty(value) || StringUtils.isEmpty(value)
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}
