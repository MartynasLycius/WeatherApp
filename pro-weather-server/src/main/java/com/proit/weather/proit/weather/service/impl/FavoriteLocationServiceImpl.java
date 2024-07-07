package com.proit.weather.proit.weather.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proit.weather.proit.weather.exception.FavoriteLocationAlreadyExistsException;
import com.proit.weather.proit.weather.exception.FavoriteLocationNotFoundException;
import com.proit.weather.proit.weather.model.FavoriteLocation;
import com.proit.weather.proit.weather.repo.FavoriteLocationRepository;
import com.proit.weather.proit.weather.service.FavoriteLocationsService;

@Service
public class FavoriteLocationServiceImpl implements FavoriteLocationsService{
	   
		@Autowired
	    private FavoriteLocationRepository favoriteLocationRepository;

	    @Override
	    public List<FavoriteLocation> getFavoriteAllLocations() {
	        return favoriteLocationRepository.findAll();
	    }

	    @Override
	    public FavoriteLocation getFavoriteLocationById(Long id) {
	        Optional<FavoriteLocation> location = favoriteLocationRepository.findById(id);
	        if (location.isPresent()) {
	            return location.get();
	        } else {
	            throw new FavoriteLocationNotFoundException("Favorite Location with id " + id + " not found");
	        }
	    }

	    @Override
	    public FavoriteLocation createFavoriteLocation(FavoriteLocation location) {
	    	
	    	 if (favoriteLocationRepository.existsById(location.getId())) {
	             throw new FavoriteLocationAlreadyExistsException("Favorite location already exists with id " + location.getId());
	         }
	         return favoriteLocationRepository.save(location);
	    }

	    @Override
	    public FavoriteLocation updateFavoriteLocation(Long id, FavoriteLocation location) {
	    	  if (!favoriteLocationRepository.existsById(id)) {
	              throw new FavoriteLocationNotFoundException("Favorite location not found with id " + id);
	          }
	          location.setId(id);
	          return favoriteLocationRepository.save(location);
	    }

	    @Override
	    public void deleteFavoriteLocation(Long id) {
	    	 if (!favoriteLocationRepository.existsById(id)) {
	             throw new FavoriteLocationNotFoundException("Favorite location not found with id " + id);
	         }
	         favoriteLocationRepository.deleteById(id);
	    }
}
