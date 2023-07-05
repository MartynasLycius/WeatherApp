package com.eastnetic.task.service.impl;

import com.eastnetic.task.model.entity.Users;
import com.eastnetic.task.repository.UsersRepo;
import com.eastnetic.task.service.UsersService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepo userRepository;
    private final AuthenticationContext authenticationContext;
    private final PasswordEncoder passwordEncoder;

    /**
     * Create user and save to table
     * @param user
     * @return
     * @throws
     */
    public void createUser(Users user) {
        if (!userRepository.existsByUsername(user.getUsername())){
            userRepository.save(user);
        }
    }

    /**
     * Gets username from authentication principal
     * @param
     * @return String
     * @throws
     */
    public String getUsernameFromPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return authentication.getPrincipal().toString();
        }

        return null;
    }

    /**
     * Add user to table
     * @param username, password, roles, name
     * @return
     * @throws
     */
    public void addUser(String username, String password, String roles, String name) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roles);
        user.setName(name);
        user.setStatus(1);

        createUser(user);
    }

    /**
     * Check if user is logged in or not
     * @param
     * @return boolean
     * @throws
     */
    public boolean isUserLoggedIn() {
        if(getCurrentUser() != null)
            return true;
        return false;
    }

    /**
     * Get current logged in user details
     * @param
     * @return Users
     * @throws
     */
    public Users getCurrentUser() {
        String username = getUsernameFromPrincipal();
        Users user = userRepository.findByUsername(username).orElse(null);
        return user;
    }

    /**
     * Logout from current context
     * @param
     * @return
     * @throws
     */
    public void logout() {
        authenticationContext.logout();
    }
}
