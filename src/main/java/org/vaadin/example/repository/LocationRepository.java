package org.vaadin.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.vaadin.example.entity.Location;

@ApplicationScoped
public class LocationRepository {

    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    @Transactional
    public Location save(Location location) {
        entityManager.persist(location);
        return location;
    }

    public Location findByCoordinates(double latitude, double longitude) {
        return entityManager.createQuery("SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude", Location.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    public Location findById(Integer locationId) {
        return entityManager.createQuery("SELECT l FROM Location l WHERE l.id = :locationId", Location.class)
                .setParameter("locationId", locationId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
}
