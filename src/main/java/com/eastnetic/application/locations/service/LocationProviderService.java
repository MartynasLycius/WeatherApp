package com.eastnetic.application.locations.service;


import com.eastnetic.application.locations.entity.LocationDetails;

import java.util.List;

public interface LocationProviderService {

    List<LocationDetails> getLocationDetails(String cityName, int count);

}
