package com.example.application.services;

import com.example.application.data.FavouritesRepository;
import com.example.application.data.Favourites;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FavouritesService {

    private static String userId;
    private FavouritesRepository favouriteRep;

    public FavouritesService(FavouritesRepository rep) {
        this.favouriteRep = rep;
    }
    public static void setUserId(String userIdSelected) {
        userId = userIdSelected;
    }
    public List<Favourites> getAll() {
        return favouriteRep.find(userId);
    }
    public Favourites findOne(String locId) {
        return favouriteRep.findOne(userId, locId);
    }
    public void save(Integer locId, String name, String lat, String longt) {
        favouriteRep.insert(userId, locId, name, lat, longt);
    }
    public void delete(Integer id) {
        favouriteRep.delete(id);
    }
}
