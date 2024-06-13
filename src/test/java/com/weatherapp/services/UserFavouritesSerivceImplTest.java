/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.weatherapp.services;

import com.weatherapp.dao.UserFavouriteRepository;
import com.weatherapp.detail.LocationInformation;
import com.weatherapp.model.User;
import com.weatherapp.model.UserFavourites;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author rasel
 */
public class UserFavouritesSerivceImplTest {


    @Mock
    private UserFavouriteRepository userFavouritesRepository;

    private UserFavouritesSerivce userFavouritesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userFavouritesService = new UserFavouritesSerivceImpl(userFavouritesRepository);
    }

    @org.junit.jupiter.api.Test
    public void testAddFavourite() {
        LocationInformation locationInformation = new LocationInformation();
        UserFavourites favourite = new UserFavourites();
        User user = new User();
        user.setId(1);
        favourite.setId(1);
        favourite.setUser(user);
        favourite.setCity("Dhaka");
        favourite.setCountry("Bangladesh");
        favourite.setLatitude(23.8103);
        favourite.setLongitude(90.4125);

        locationInformation.setName("Dhaka");
        locationInformation.setCountry("Bangladesh");
        locationInformation.setLatitude(23.8103);
        locationInformation.setLongitude(90.4125);


        when(userFavouritesRepository.save(any(UserFavourites.class))).thenReturn(favourite);

        userFavouritesService.addFavourite(user.getId(), locationInformation);

        verify(userFavouritesRepository, times(1)).save(favourite);
    }

    @Test
    public void testGetFavourites() {
        User user = new User();
        user.setId(1);
        UserFavourites favourite1 = new UserFavourites();

        favourite1.setUser(user);
        favourite1.setCity("Dhaka");
        favourite1.setCountry("Bangladesh");
        favourite1.setLatitude(23.8103);
        favourite1.setLongitude(90.4125);

        UserFavourites favourite2 = new UserFavourites();

        favourite2.setUser(user);
        favourite2.setCity("Khulna");
        favourite2.setCountry("Bangladesh");
        favourite2.setLatitude(23.8103);
        favourite2.setLongitude(90.4125);

        List<UserFavourites> favourites = Arrays.asList(favourite1, favourite2);

        when(userFavouritesRepository.findAllByUserId(1)).thenReturn(favourites);

        List<UserFavourites> returnedFavourites = userFavouritesService.getFavourites(1);

        assertEquals(favourites, returnedFavourites);
        verify(userFavouritesRepository, times(1)).findAllByUserId(1);
    }
    
}
