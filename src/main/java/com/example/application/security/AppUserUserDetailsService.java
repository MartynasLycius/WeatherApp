package com.example.application.security;

import com.example.application.data.entity.AppUser;
import com.example.application.services.AppUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserUserDetailsService implements UserDetailsManager, UserDetailsPasswordService {

    private final AppUserService appUserService;

    public AppUserUserDetailsService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserService.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority("USER"));
        return new User(user.getEmail(), user.getPassword(), true, true, true, true, authorityList);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}
