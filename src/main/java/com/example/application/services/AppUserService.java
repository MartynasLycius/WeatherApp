package com.example.application.services;

import com.example.application.data.entity.AppUser;
import com.example.application.repositories.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser findUserByEmail(String email) {
        return this.appUserRepository.findByEmail(email);
    }
}
