package org.vaadin.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.vaadin.example.entity.FavoriteLocation;
import org.vaadin.example.entity.Location;
import org.vaadin.example.entity.User;

import java.util.List;

@ApplicationScoped
public class FavoriteLocationRepository {

    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    @Transactional
    public void addFavoriteLocation(User user, Location location) {
        // Check if the favorite location already exists for the user
        if (!isFavoriteLocationExists(user, location)) {
            // Favorite location does not exist, so add it
            FavoriteLocation favoriteLocation = new FavoriteLocation();
            favoriteLocation.setUser(user);
            favoriteLocation.setLocation(location);
            entityManager.persist(favoriteLocation);
        }
    }

    private boolean isFavoriteLocationExists(User user, Location location) {
        return entityManager.createQuery(
                        "SELECT COUNT(fl) FROM FavoriteLocation fl WHERE fl.user = :user AND fl.location = :location", Long.class)
                .setParameter("user", user)
                .setParameter("location", location)
                .getSingleResult() > 0;
    }

    public List<Location> findFavoriteLocationsByUser(User user) {

        return entityManager.createQuery("SELECT fl.location FROM FavoriteLocation fl WHERE fl.user = :user", Location.class)
                .setParameter("user", user)
                .getResultList();
    }
}
