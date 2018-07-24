package com.example.bootmongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bootmongo.model.Role;

@Repository
public interface RoleRepo extends MongoRepository<Role, String> {

	@Query("{'name' : ?0}")
	public Role findByRoleName(String roleName);
}
