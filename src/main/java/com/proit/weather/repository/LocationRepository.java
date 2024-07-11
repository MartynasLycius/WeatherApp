package com.proit.weather.repository;


import com.proit.weather.model.LocationInfoModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.proit.weather.utils.APP_CONSTANTS.DB_UNIT;

public class LocationRepository {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DB_UNIT);

    public void createLocation(LocationInfoModel locationInfo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(locationInfo);
        em.getTransaction().commit();
        em.close();
    }

    public LocationInfoModel findLocation(Long id) {
        EntityManager em = emf.createEntityManager();
        LocationInfoModel locationInfo = em.find(LocationInfoModel.class, id);
        em.close();
        return locationInfo;
    }
}
