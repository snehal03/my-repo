package com.example.bootmongo.requestModel;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(makeFinal=false, level=AccessLevel.PRIVATE)
@ToString
public class UpdateUserRquest {

	String _id;
	
	String firstName;

	String lastName;

	ArrayList<AddressRequest> address;

	ArrayList<RoleRequest> roles;
}
