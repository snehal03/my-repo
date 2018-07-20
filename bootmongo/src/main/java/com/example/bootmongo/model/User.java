package com.example.bootmongo.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Document(collection="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames=true)
@FieldDefaults(level=AccessLevel.PRIVATE , makeFinal=false)
public class User {

	String _id;
	
	String firstname;
	
	String lastname;
	
	String password;
	
	String email;
	
	ArrayList<Role> roles;

}
