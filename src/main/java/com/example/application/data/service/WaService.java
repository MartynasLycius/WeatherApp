package com.example.application.data.service;

import com.example.application.dto.GeoCode;
import com.example.application.http.client.HttpGeoDataRequest;
import org.springframework.stereotype.Service;

@Service
public class WaService {

    private HttpGeoDataRequest httpGeoDataRequest;

    public WaService(HttpGeoDataRequest httpGeoDataRequest) {
        this.httpGeoDataRequest = httpGeoDataRequest;
    }

    public GeoCode[] getGeoCodeResult(String cityName){
        if(cityName == null || cityName.isEmpty()){
            GeoCode[] result = new GeoCode[0];
            return result;
        } else {
            return httpGeoDataRequest.getGeoCodeResult(cityName).getResults();
        }
    }

}
