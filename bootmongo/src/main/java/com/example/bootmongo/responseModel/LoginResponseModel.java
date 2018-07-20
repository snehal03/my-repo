package com.example.bootmongo.responseModel;

import java.util.List;

import com.example.bootmongo.model.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE , makeFinal=false)
public class LoginResponseModel {

	String id;
	
	String email;
	
	String token;
	
	String firstname;
	
	String lastname;
	
	List<Role> roles;
	
	
	
}
