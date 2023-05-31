package com.proit.application.service;

import com.proit.application.data.entity.User;
import com.proit.application.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails;
        }

        return null;
    }

    public boolean isUserLoggedIn() {
        log.debug("isUserLoggedIn called");

        return getUserDetails() != null;
    }

    public Long getCurrentUserId() {
        log.debug("getCurrentUserId called");

        UserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }

        String username = userDetails.getUsername();

        User user = repository.findByUsername(username);
        if (user != null) {
            log.debug("Found user with username: {}", username);
            return user.getId();
        }

        return null;
    }

}
