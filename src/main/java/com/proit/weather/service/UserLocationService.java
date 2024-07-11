package com.proit.weather.service;

import com.proit.weather.dto.LocationInfoDto;
import com.proit.weather.model.LocationInfoModel;
import com.proit.weather.model.User;
import com.proit.weather.model.UserLocationMapModel;
import com.proit.weather.repository.LocationRepository;
import com.proit.weather.repository.UserLocationMapRepository;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class UserLocationService {
    private final LocationRepository locationRepository = new LocationRepository();
    private final UserLocationMapRepository userLocationMapRepository = new UserLocationMapRepository();
    private final User user = (User) VaadinSession.getCurrent().getAttribute(SecurityService.USER_ATTRIBUTE);

    public void saveFavoriteLocation(LocationInfoDto locationInfo) {
        LocationInfoModel locationInfoModel = locationRepository.findLocation(locationInfo.getId());
        if (locationInfoModel == null) {
            locationInfoModel = new LocationInfoModel();
            try {
                BeanUtils.copyProperties(locationInfoModel, locationInfo);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        UserLocationMapModel userLocationMapModel = new UserLocationMapModel();
        userLocationMapModel.setUserInfo(user);
        userLocationMapModel.setLocationInfoModel(locationInfoModel);
        userLocationMapRepository.createUserLocation(userLocationMapModel);
    }

    public List<LocationInfoModel> getFavouriteLocations() {
        List<UserLocationMapModel> userLocations = userLocationMapRepository.findUserLocationMaps(user.getId());
        return userLocations.stream().map(UserLocationMapModel::getLocationInfoModel).collect(Collectors.toList());
    }

    public void removeFavourite(Long locationId) {
        UserLocationMapModel userLocationMapModel = userLocationMapRepository.findUserLocationMap(user.getId(), locationId);
        userLocationMapRepository.deleteLocation(userLocationMapModel.getId());
    }
}
