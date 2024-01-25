package com.example.application.security;

import com.example.application.data.UsersRepository;
import com.example.application.data.Users;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            Users user = userRepository.find(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, this.getGrantedAuthorities());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    private List<GrantedAuthority> getGrantedAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
        
        return authorities;
    }
}
