package com.weatherapp.services;

import com.weatherapp.dao.UserFavouriteRepository;
import com.weatherapp.model.User;
import com.weatherapp.model.UserFavourites;
import com.weatherapp.detail.LocationInformation;
import org.springframework.stereotype.Service;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
@Service
public class UserFavouritesSerivceImpl implements UserFavouritesSerivce{

    private final UserFavouriteRepository userFavouriteRepository;

    public UserFavouritesSerivceImpl(UserFavouriteRepository userFavouriteRepository) {
        this.userFavouriteRepository = userFavouriteRepository;
    }

    /**
     * This method is used to add a favourite location for a user.
     *
     * @param userId The ID of the user for whom the favourite location is being added.
     * @param locationInformation The information about the location that is being added as a favourite.
     * This includes details like latitude, longitude, city name, and country name.
     *
     * If the userId is 0 or the locationInformation is null, the method will return without doing anything.
     * Otherwise, it creates a new UserFavourites object and sets the latitude, longitude, city, and country
     * from the locationInformation. It also creates a new User object, sets its ID to the provided userId,
     * and associates it with the UserFavourites object.
     *
     * Finally, it saves the UserFavourites object to the repository.
     */
    @Override
    public void addFavourite(int userId, LocationInformation locationInformation) {
        if(userId==0 || locationInformation == null) {
            return;
        }
        UserFavourites userFavourites = new UserFavourites();
        User user = new User();
        user.setId(userId);

        userFavourites.setLatitude(locationInformation.getLatitude());
        userFavourites.setLongitude(locationInformation.getLongitude());
        userFavourites.setCity(locationInformation.getName());
        userFavourites.setCountry(locationInformation.getCountry());
        userFavourites.setUser(user);
        userFavouriteRepository.save(userFavourites);
    }

    /**
     * This method is used to remove a favourite location for a user.
     *
     * @param locationInformation The information about the location that is being removed from favourites.
     * This includes details like latitude, longitude, city name, and country name.
     * @param userId The ID of the user for whom the favourite location is being removed.
     *
     * If the userId is 0 or the locationInformation is null, the method will return without doing anything.
     * Otherwise, it retrieves the UserFavourites object associated with the provided location and user ID.
     * If such a UserFavourites object exists, it is deleted from the repository.
     */
    @Override
    public void removeFavourite(LocationInformation locationInformation,int userId) {
        if(userId==0 || locationInformation == null) {
            return;
        }
        UserFavourites userFavourites = userFavouriteRepository.get(locationInformation.getLatitude(), locationInformation.getLongitude(), userId);
        if(userFavourites != null) {
            userFavouriteRepository.delete(userFavourites);
        }
    }
    @Override
    public List<UserFavourites> getFavourites(int userId) {
        return userFavouriteRepository.findAllByUserId(userId);
    }
}
