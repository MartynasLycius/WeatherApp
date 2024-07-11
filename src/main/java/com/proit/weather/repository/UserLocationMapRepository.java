package com.proit.weather.repository;

import com.proit.weather.model.UserLocationMapModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static com.proit.weather.utils.APP_CONSTANTS.DB_UNIT;

public class UserLocationMapRepository {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DB_UNIT);

    public void createUserLocation(UserLocationMapModel userLocationMapModel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(userLocationMapModel);
        em.getTransaction().commit();
        em.close();
    }

    public UserLocationMapModel findUserLocationMap(Long userId, Long locationId) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ulm FROM UserLocationMapModel ulm WHERE ulm.userInfo.id = :user_id AND ulm.locationInfoModel.id = :location_id";
        UserLocationMapModel locations = em.createQuery(query, UserLocationMapModel.class)
                .setParameter("user_id", userId)
                .setParameter("location_id", locationId)
                .getSingleResult();
        em.close();
        return locations;
    }

    public List<UserLocationMapModel> findUserLocationMaps(Long userId) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT ulm FROM UserLocationMapModel ulm WHERE ulm.userInfo.id = :user_id";
        List<UserLocationMapModel> locations = em.createQuery(query, UserLocationMapModel.class)
                .setParameter("user_id", userId)
                .getResultList();
        em.close();
        return locations;
    }

    public void deleteLocation(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        UserLocationMapModel userLocationMapModel = em.find(UserLocationMapModel.class, id);
        if (userLocationMapModel != null) {
            em.remove(userLocationMapModel);
        }
        em.getTransaction().commit();
        em.close();
    }
}
