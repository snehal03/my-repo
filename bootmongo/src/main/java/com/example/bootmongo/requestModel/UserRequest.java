package com.example.bootmongo.requestModel;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.example.bootmongo.model.Address;
import com.example.bootmongo.model.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal= false)
public class UserRequest {

	String _id;
	
	@NotNull(message = "First Name can not be null")
	String firstname;
	
	@NotNull(message = "Last Name can not be null")
	String lastname;
	
	@NotNull(message = "Password can not be null")
	String password;
	
	@NotNull(message = "Email can not be null")
	String email;
	
	@NotNull(message = "Address can not be null")
	ArrayList<AddressRequest> address;
	
	@NotNull(message = "Roles can not be null")
	ArrayList<RoleRequest> roles;
}
