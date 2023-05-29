package com.example.application.services;

import com.example.application.data.entity.AppUser;

public interface AppUserService {
    AppUser findUserByEmail(String email);
}
