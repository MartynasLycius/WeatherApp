package org.weather.app.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.weather.app.domain.User;
import org.weather.app.repository.UserRepository;

@Component
public class InitDefaultDBInsert {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public InitDefaultDBInsert(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void runAfterStartup() {
    createDefaultUsers();
  }

  private void createDefaultUsers() {
    if (userRepository.count() < 1) {
      System.out.println("CREATING_DEFAULT_USERS");
      userRepository.save(new User("user", "ROLE_USER", passwordEncoder.encode("1234")));
      userRepository.save(new User("admin", "ROLE_ADMIN", passwordEncoder.encode("1234")));
      userRepository.save(new User("salman", "ROLE_ADMIN", passwordEncoder.encode("1234")));
    } else {
      System.out.println("DEFAULT_USERS_ALREADY_INITIALIZED");
    }
  }
}
