package com.example.bootmongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bootmongo.model.UserToken;

@Repository
public interface UserTokenRepo extends MongoRepository <UserToken,String >{
	
	@Query("{ $and : [{'email' : ?0} , {'token' : ?1}] }")
	UserToken findUserToken(String userEmailId,String token);

	@Query("{$and: [{'email' : ?0} , {'tokenType' : 'FORGOT_PASS'}] }")
	UserToken getForgotPasswordToken(String emailId);

/*	@Query("{$and: [{'email' : ?0} , {'tokenType' : 'FORGOT_PASS'}] },  delete = true")
	void removeToken(String email);*/
}
