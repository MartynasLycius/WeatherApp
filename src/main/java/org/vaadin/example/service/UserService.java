package org.vaadin.example.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.vaadin.example.entity.User;
import org.vaadin.example.util.PasswordUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    @Transactional
    public void register(User user) {

        String plainPassword = user.getPassword();
        String hashedPassword = PasswordUtils.hashPassword(plainPassword);
        user.setPassword(hashedPassword); // Store the hashed password

        entityManager.persist(user);
    }


    public Optional<User> login(String email, String password) {

        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (PasswordUtils.checkPassword(password, user.getPassword())) {
                return Optional.of(user);
            }
            else {
                return Optional.empty();
            }
        } catch (NoResultException e) {
            // Username not found
            return Optional.empty();
        }
    }
}
