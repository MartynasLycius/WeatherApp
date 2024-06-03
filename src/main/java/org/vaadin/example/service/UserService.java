package org.vaadin.example.service;

import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import org.vaadin.example.model.Users;
import org.vaadin.example.repository.UserRepository;

import java.util.Optional;

public class UserService {

    @Inject
    UserRepository userRepository;

    public boolean hasSession(){
        String username = (String) VaadinSession.getCurrent().getAttribute("username");
        return username != null;
    }

    public Long getLoggedUserId(){
        return Long.parseLong(VaadinSession.getCurrent().getAttribute("id").toString());
    }

    public String getLoggedUserName(){
        return (String) VaadinSession.getCurrent().getAttribute("fullName");
    }

    public Optional<Users> findByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
