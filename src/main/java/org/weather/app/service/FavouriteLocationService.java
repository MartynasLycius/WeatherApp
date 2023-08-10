package org.weather.app.service;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.weather.app.domain.FavouriteLocation;
import org.weather.app.domain.User;
import org.weather.app.repository.FavouriteLocationRepository;
import org.weather.app.service.dto.Location;

@Service
public class FavouriteLocationService {

  @Value("${notification.message.item-added}")
  private String ITEM_ADDED;

  @Value("${notification.message.item-deleted}")
  private String ITEM_DELETED;

  @Value("${notification.message.item-not-matched}")
  private String ITEM_NOT_MATCHED;

  @Value("${notification.message.item-null}")
  private String ITEM_NULL;

  private final FavouriteLocationRepository favouriteLocationRepository;
  private final DomainUserDetailsService domainUserDetailsService;

  public FavouriteLocationService(
      FavouriteLocationRepository favouriteLocationRepository,
      DomainUserDetailsService domainUserDetailsService) {
    this.favouriteLocationRepository = favouriteLocationRepository;
    this.domainUserDetailsService = domainUserDetailsService;
  }

  public void applyFavouriteLocationToLoggedInUser(Location location) {
    if (Objects.isNull(location)) {
      Notification.show(ITEM_NULL, 2000, Notification.Position.TOP_END)
          .addThemeVariants(NotificationVariant.LUMO_ERROR);
      return;
    }
    FavouriteLocation favouriteLocation = new FavouriteLocation();
    favouriteLocation.setName(location.getName());
    favouriteLocation.setCountry(location.getCountry());
    favouriteLocation.setLongitude(location.getLongitude());
    favouriteLocation.setLatitude(location.getLatitude());
    favouriteLocation.setUser(domainUserDetailsService.getLoggedInUser());
    favouriteLocationRepository.save(favouriteLocation);
    Notification.show(ITEM_ADDED, 2000, Notification.Position.TOP_END)
        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
  }

  public void removeFavouriteLocationOfLoggedInUser(Location location) {
    if (alreadyFavouriteLocationOfLoggedInUser(location)) {
      FavouriteLocation favouriteLocation = findFavouriteLocationOfLoggedInUser(location);
      favouriteLocationRepository.delete(favouriteLocation);
      Notification.show(ITEM_DELETED, 2000, Notification.Position.TOP_END)
          .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
      return;
    }
    Notification.show(ITEM_NOT_MATCHED, 2000, Notification.Position.TOP_END)
        .addThemeVariants(NotificationVariant.LUMO_ERROR);
  }

  public void removeFavouriteLocationById(Long id) {
    FavouriteLocation favouriteLocation = favouriteLocationRepository.findById(id).orElse(null);
    if (Objects.isNull(favouriteLocation)) {
      Notification.show(ITEM_NOT_MATCHED, 2000, Notification.Position.TOP_END)
          .addThemeVariants(NotificationVariant.LUMO_ERROR);
      return;
    }
    favouriteLocationRepository.delete(favouriteLocation);
    Notification.show(ITEM_DELETED, 2000, Notification.Position.TOP_END)
        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
  }

  public boolean alreadyFavouriteLocationOfLoggedInUser(Location location) {
    if (Objects.isNull(location)) {
      return false;
    }
    User loggedInUser = domainUserDetailsService.getLoggedInUser();
    FavouriteLocation favouriteLocation =
        favouriteLocationRepository.findByLatitudeAndLongitudeAndUser(
            location.getLatitude(), location.getLongitude(), loggedInUser);
    return Objects.nonNull(favouriteLocation);
  }

  public FavouriteLocation findFavouriteLocationOfLoggedInUser(Location location) {
    User loggedInUser = domainUserDetailsService.getLoggedInUser();
    return favouriteLocationRepository.findByLatitudeAndLongitudeAndUser(
        location.getLatitude(), location.getLongitude(), loggedInUser);
  }

  public List<FavouriteLocation> findAllFavouriteLocationsForLoggedInUser() {
    User loggedInUser = domainUserDetailsService.getLoggedInUser();
    List<FavouriteLocation> favouriteLocationList =
        favouriteLocationRepository.findAllByUser(loggedInUser);
    return favouriteLocationList;
  }
}
