package com.example.application.services;

import com.example.application.data.FavoriteLocationRepository;
import com.example.application.data.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService
{
    private final FavoriteLocationRepository favoriteLocationRepository;

    public CrmService(FavoriteLocationRepository favoriteLocationRepository)
    {
        this.favoriteLocationRepository = favoriteLocationRepository;
    }

    public List<Location> findAllFavoriteLocations()
    {
        return favoriteLocationRepository.findAll();
    }

    public void deleteFavoriteLocation(Location location)
    {
        favoriteLocationRepository.delete(location);
    }

    public void saveFavoriteLocation(Location location)
    {
        if (location == null)
        {
            System.out.println("Location is null, can't save as favorite");
            return;
        }
        favoriteLocationRepository.save(location);
    }
}
