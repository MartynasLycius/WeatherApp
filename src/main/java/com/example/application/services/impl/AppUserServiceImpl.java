package com.example.application.services.impl;

import com.example.application.data.entity.AppUser;
import com.example.application.repositories.AppUserRepository;
import com.example.application.services.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return this.appUserRepository.findByEmail(email);
    }
}
