package com.proit.weather.proit.weather.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proit.weather.proit.weather.model.FavoriteLocation;
import com.proit.weather.proit.weather.service.FavoriteLocationsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/favlocations")
public class FavoriteLocationController {

 @Autowired
 private FavoriteLocationsService favoriteLocationsService;

 @GetMapping
 public List<FavoriteLocation> getAllLocations() {
     return favoriteLocationsService.getFavoriteAllLocations();
 }

 @GetMapping("/{id}")
 public ResponseEntity<FavoriteLocation> getLocationById(@PathVariable Long id) {
	 FavoriteLocation location = favoriteLocationsService.getFavoriteLocationById(id);
     return ResponseEntity.ok(location);
 }

 @PostMapping
 public FavoriteLocation createLocation(@RequestBody FavoriteLocation favoriteLocation) {
     return favoriteLocationsService.createFavoriteLocation(favoriteLocation);
 }

 @PutMapping("/{id}")
 public ResponseEntity<FavoriteLocation> updateLocation(@PathVariable Long id, @RequestBody FavoriteLocation favoriteLocation) {
	 FavoriteLocation updatedLocation = favoriteLocationsService.updateFavoriteLocation(id, favoriteLocation);
     return ResponseEntity.ok(updatedLocation);
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
     favoriteLocationsService.deleteFavoriteLocation(id);
     return ResponseEntity.noContent().build();
 }
}
