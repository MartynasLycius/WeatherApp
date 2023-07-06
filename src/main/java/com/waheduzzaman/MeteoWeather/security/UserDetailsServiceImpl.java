package com.waheduzzaman.MeteoWeather.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import com.waheduzzaman.MeteoWeather.data.entity.User;
import com.waheduzzaman.MeteoWeather.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private User user;
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        user = userRepository.findByName(name);
        if (user == null)
            throw new UsernameNotFoundException("Invalid Username/Password !");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }

    public User getLoggedInUser() {
        user = userRepository.findByName(authenticationContext.getAuthenticatedUser(UserDetails.class).get().getUsername());
        return user;
    }

    public boolean isUserLoggedIn() {
        return authenticationContext.isAuthenticated();
    }

    public void logout() {
        authenticationContext.logout();
    }
}