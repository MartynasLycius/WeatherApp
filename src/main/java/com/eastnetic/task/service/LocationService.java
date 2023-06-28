package com.eastnetic.task.service;

import com.eastnetic.task.model.dto.LocationDTO;

public interface LocationService {

    LocationDTO getLocations(String cityName);
}
