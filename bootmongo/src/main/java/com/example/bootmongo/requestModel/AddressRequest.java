package com.example.bootmongo.requestModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(makeFinal= false, level=AccessLevel.PRIVATE)
public class AddressRequest {

	String route;
	
	String landmark;
	
	String city;
	
	String state;
	
	String zip;
	
	String country;
}
