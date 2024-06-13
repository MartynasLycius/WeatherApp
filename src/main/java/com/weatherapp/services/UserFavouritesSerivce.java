package com.weatherapp.services;

import com.weatherapp.model.UserFavourites;
import com.weatherapp.detail.LocationInformation;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 13 Jun, 2024
 */
public interface UserFavouritesSerivce {
    public void addFavourite(int userId, LocationInformation locationInformation);
    public void removeFavourite(LocationInformation locationInformation,int userId);
    public List<UserFavourites> getFavourites(int userId);
}
