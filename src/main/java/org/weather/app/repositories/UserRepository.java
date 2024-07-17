package org.weather.app.repositories;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.weather.app.entities.User;

import java.util.Optional;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "my-unit")
    private EntityManager em;

    public Optional<User> findByUsername(String username) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> authenticate(String username, String password) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getResultStream().findFirst();
    }

    public void save(User user) {
        em.merge(user);
    }
}