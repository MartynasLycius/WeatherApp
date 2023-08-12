package com.eastnetic.application.users.repository;

import com.eastnetic.application.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

    User findByUsername(String username);
}
