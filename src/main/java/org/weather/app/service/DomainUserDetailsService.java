package org.weather.app.service;

import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.weather.app.domain.User;
import org.weather.app.repository.UserRepository;

@Service
public class DomainUserDetailsService implements UserDetailsService {

  private User user;
  private final UserRepository userRepository;
  private final AuthenticationContext authenticationContext;

  public DomainUserDetailsService(
      UserRepository userRepository, AuthenticationContext authenticationContext) {
    this.userRepository = userRepository;
    this.authenticationContext = authenticationContext;
  }

  @Override
  public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findFirstByLogin(name);
    if (optionalUser.isEmpty()) {
      throw new UsernameNotFoundException("Invalid Username/Password !");
    }
    user = optionalUser.get();
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));

    return new org.springframework.security.core.userdetails.User(
        user.getLogin(), user.getPassword(), grantedAuthorities);
  }

  public User getLoggedInUser() {
    user =
        userRepository
            .findFirstByLogin(
                authenticationContext.getAuthenticatedUser(UserDetails.class).get().getUsername())
            .get();
    return user;
  }

  public boolean isUserLoggedIn() {
    return authenticationContext.isAuthenticated();
  }

  public void logout() {
    authenticationContext.logout();
  }
}
