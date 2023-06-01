package com.example.application.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.application.backend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {


	public User findById(long id);
	
    public User findByName(String  name);


}

