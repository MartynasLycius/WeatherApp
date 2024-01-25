package com.example.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query("select c from Users c " +
            "where lower(c.username) = :username")
    Users find(@Param("username") String username);
}
