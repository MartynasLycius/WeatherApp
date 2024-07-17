package org.weather.app.repositories;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.weather.app.entities.FavoriteCity;
import org.weather.app.entities.User;

import java.util.List;
import java.util.UUID;

@Stateless
public class FavoriteCityRepository {
    @PersistenceContext(unitName = "my-unit")
    private EntityManager em;

    public List<FavoriteCity> findFavoriteCitiesByUser(User user) {
        TypedQuery<FavoriteCity> query = em.createQuery(
                "SELECT f FROM FavoriteCity f WHERE f.user = :user", FavoriteCity.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<FavoriteCity> findFavoriteCitiesByUserId(UUID userId) {
        TypedQuery<FavoriteCity> query = em.createQuery(
                "SELECT f FROM FavoriteCity f WHERE f.user.id = :userId", FavoriteCity.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public FavoriteCity findCityByLatLong(String latitude, String longitude) {
        TypedQuery<FavoriteCity> query = em.createQuery(
                "SELECT f FROM FavoriteCity f WHERE f.latitude = :latitude AND f.longitude = :longitude", FavoriteCity.class);
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        return query.getSingleResult();
    }

    public FavoriteCity findCityByUserIdAndCityName(User user, String name) {
        TypedQuery<FavoriteCity> query = em.createQuery(
                "SELECT f FROM FavoriteCity f WHERE f.user = :user AND f.name = :name", FavoriteCity.class);
        query.setParameter("user", user);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public void save(FavoriteCity favoriteCity) {
        em.merge(favoriteCity);
    }

    public void delete(FavoriteCity favoriteCity) {
        em.remove(em.merge(favoriteCity));
    }
}
