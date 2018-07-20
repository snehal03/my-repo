package com.example.bootmongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bootmongo.model.User;

@Repository
public interface UserRepo  extends MongoRepository<User, String>{

	@Query("{'email': ?0}")
	User findUserByEmail(String email);
	
}
