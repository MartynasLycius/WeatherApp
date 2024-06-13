package com.weatherapp.detail;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
@lombok.Data
public class FavouriteList {
    public  FavouriteList(String name) {
        this.name = name;
    }
    public FavouriteList() {
    }
    String name;
    LocationInformation locationInformation;
}
