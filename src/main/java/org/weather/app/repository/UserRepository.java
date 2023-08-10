package org.weather.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weather.app.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findFirstByLogin(String login);
}
